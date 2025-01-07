package OCR.service;

import OCR.rabbitmq.RabbitMqSender;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ghost4j.document.PDFDocument;
import org.ghost4j.renderer.SimpleRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import net.sourceforge.tess4j.Tesseract;
import net.sourceforge.tess4j.TesseractException;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.List;

@Service
public class OcrService {
    @Autowired
    RabbitMqSender rabbitMqSender;

    @Autowired
    MinIOService minIOService;

    @Autowired
    ElasticSearchService elasticSearchService;

    Logger logger = LogManager.getLogger();

    private String performOCR(File file) {
        Tesseract tesseract = new Tesseract();
        try {
            tesseract.setDatapath("/app/tessdata");
            return tesseract.doOCR(file);
        } catch (TesseractException e) {
            return null;
        }
    }

    public File convertPdfToImage(byte[] pdfBytes) throws Exception {
        // Load the PDF document
        PDFDocument document = new PDFDocument();
        document.load(new ByteArrayInputStream(pdfBytes));
        logger.debug("ghost4j PDF document created from bytes");

        // Create a renderer and set the resolution
        SimpleRenderer renderer = new SimpleRenderer();
        renderer.setResolution(300); // Set desired DPI
        logger.debug("ghost4j renderer created and resolution set");

        // Render the document as a list of images
        List<Image> images = renderer.render(document);
        logger.debug("ghost4j list of images rendered");

        // Convert the first image to RenderedImage
        RenderedImage renderedImage = (RenderedImage) images.get(0);
        logger.debug("first element in list of images converted to RenderedImage");

        // Save the first image to a temporary file
        File outputImage = File.createTempFile("output", ".png");
        ImageIO.write(renderedImage, "png", outputImage);
        logger.debug("rendered image to output image file");

        return outputImage;
    }

    public void returnFileContent(String fileIdentifier) throws Exception {
        try{
            // download file bytes from minio
            byte[] byteStream = minIOService.download(fileIdentifier);

            if(byteStream != null){
                logger.debug("ByteStream for id " + fileIdentifier + " retrieved from MinIO");
            } else{
                logger.error("received bytestream for identifier " + fileIdentifier + " is empty");
                throw new Exception();
            }

            // render image from bytestream
            File file = convertPdfToImage(byteStream);
            logger.debug("ByteStream for id " + fileIdentifier + " converted to image with ghostScript");

            // perform OCR
            String fileText = performOCR(file);
            logger.debug("OCR performed on image " + fileIdentifier + " with Tesseract");

            // send text to elasticSearch
            elasticSearchService.indexDocument(fileIdentifier, fileText);
            logger.debug("Indexing Document performed on " + fileIdentifier + " with elastic Search");

            // TODO:
            // clean up after file
            if(file.exists()){
                boolean deleted = file.delete();

                if(deleted){
                    logger.debug("file cleanup successful");
                } else{
                    logger.debug("failed to delete file");
                }
            }

            // return message that OCR was successful
            if(fileText != null){
                rabbitMqSender.returnFileContent(fileIdentifier);
            }
        }catch(Exception e){
            logger.error("OCR-Service failed. See detailed stacktrace: " + e);
        }
    }


}
