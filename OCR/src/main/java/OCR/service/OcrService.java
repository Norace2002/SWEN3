package OCR.service;

import OCR.rabbitmq.RabbitMqSender;
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
        System.out.println("ghost4j pdfdocument created from bytes");

        // Create a renderer and set the resolution
        SimpleRenderer renderer = new SimpleRenderer();
        renderer.setResolution(300); // Set desired DPI
        System.out.println("ghost4j renderer created and resolution set");

        // Render the document as a list of images
        List<Image> images = renderer.render(document);
        System.out.println("ghost4j list of images rendered");

        // Convert the first image to RenderedImage
        RenderedImage renderedImage = (RenderedImage) images.get(0);
        System.out.println("first elmeent in list of images converted to RenderedImage");

        // Save the first image to a temporary file
        File outputImage = File.createTempFile("output", ".png");
        ImageIO.write(renderedImage, "png", outputImage);
        System.out.println("written the renderedimage to outputimage File");

        return outputImage;
    }

    public void returnFileContent(String fileIdentifier) throws Exception {
        try{
            // download file bytes from minio
            byte[] byteStream = minIOService.download(fileIdentifier);

            if(byteStream != null){
                System.out.println("ByteStream for id " + fileIdentifier + " retrieved from MinIO");
            } else{
                throw new Exception("Bytestream received is empty");
            }

            // render image from bytestream
            File file = convertPdfToImage(byteStream);
            System.out.println("ByteStream for id " + fileIdentifier + " converted to image with ghostScript");

            // perform OCR
            String fileText = performOCR(file);
            System.out.println("OCR performed on image " + fileIdentifier + " with Tesseract");

            // TODO:
            // send text to elasticSearch

            // TODO:
            // clean up after file
            if(file.exists()){
                boolean deleted = file.delete();

                if(deleted){
                    System.out.println("file cleanup successful");
                } else{
                    System.out.println("failed to delete file");
                }
            }

            // return message that OCR was successful
            if(fileText != null){
                rabbitMqSender.returnFileContent(fileIdentifier);
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }


}
