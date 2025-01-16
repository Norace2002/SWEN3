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
import java.util.ArrayList;
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

    public String performOCR(List<File> files) throws Exception{

        Tesseract tesseract = new Tesseract();
        tesseract.setDatapath("/app/tessdata");
        tesseract.setTessVariable("user_defined_dpi", "70");

        StringBuilder ocrResults = new StringBuilder();

        for (File file : files) {
            try {
                String result = tesseract.doOCR(file);
                ocrResults.append(result).append("\n");
            } catch (TesseractException e) {
                throw new Exception("Tesseract couldn't process the pdf correctly");
            }
        }

        return ocrResults.toString();
    }

    public List<File> convertPdfToImage(byte[] pdfBytes) throws Exception {
        // Load the PDF document
        PDFDocument document = new PDFDocument();
        document.load(new ByteArrayInputStream(pdfBytes));
        logger.info("ghost4j PDF document created from bytes");

        // Create a renderer and set the resolution
        SimpleRenderer renderer = new SimpleRenderer();
        renderer.setResolution(70); // Set desired DPI
        logger.info("ghost4j renderer created and resolution set");
      
        // Render the document as a list of images
        List<Image> images = renderer.render(document);
        logger.info("ghost4j list of images rendered");

        List<File> outputImages = new ArrayList<>();

        for(int i = 0; i < images.size(); i++){
            // Convert the images to RenderedImages
            RenderedImage renderedImage = (RenderedImage) images.get(i);

            // Save the images to a temporary file
            File outputImage = File.createTempFile("page_" + (i+1), ".png");
            ImageIO.write(renderedImage, "png", outputImage);

            outputImages.add(outputImage);
        }

        logger.info("rendered images to output image file");

        return outputImages;
    }

    public void returnFileContent(String fileIdentifier){
        try{
            // download file bytes from minio
            byte[] byteStream = minIOService.download(fileIdentifier);

            if(byteStream != null){
                logger.info("ByteStream for id " + fileIdentifier + " retrieved from MinIO");
            } else{
                logger.error("received bytestream for identifier " + fileIdentifier + " is empty");
                throw new Exception();
            }

            // render image from bytestream
            List<File> files = convertPdfToImage(byteStream);
            System.out.println("ByteStream for id " + fileIdentifier + " converted to images with ghostScript");

            // perform OCR
            String fileText = performOCR(files);
            logger.info("OCR performed on image " + fileIdentifier + " with Tesseract");

            // send text to elasticSearch
            elasticSearchService.indexDocument(fileIdentifier, fileText);
            logger.info("Indexing Document performed on " + fileIdentifier + " with elastic Search");

            // clean up after file
            if(!files.isEmpty()){
                boolean deleted = false;

                for(File file : files){
                    deleted = file.delete();
                }

                if(deleted){
                    logger.info("file cleanup successful");
                } else{
                    logger.info("failed to delete file");
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
