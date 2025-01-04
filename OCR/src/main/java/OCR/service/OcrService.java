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

    private String performOCR(List<File> files) throws Exception{
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
        System.out.println("ghost4j pdfdocument created from bytes");

        // Create a renderer and set the resolution
        SimpleRenderer renderer = new SimpleRenderer();
        renderer.setResolution(70); // Set desired DPI
        System.out.println("ghost4j renderer created and resolution set");

        // Render the document as a list of images
        List<Image> images = renderer.render(document);
        System.out.println("ghost4j list of images rendered");

        List<File> outputImages = new ArrayList<>();

        for(int i = 0; i < images.size(); i++){
            // Convert the images to RenderedImages
            RenderedImage renderedImage = (RenderedImage) images.get(i);

            // Save the images to a temporary file
            File outputImage = File.createTempFile("page_" + (i+1), ".png");
            ImageIO.write(renderedImage, "png", outputImage);

            outputImages.add(outputImage);
        }

        System.out.println("written the renderedimages to outputimages");

        return outputImages;
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
            List<File> files = convertPdfToImage(byteStream);
            System.out.println("ByteStream for id " + fileIdentifier + " converted to images with ghostScript");

            // perform OCR
            String fileText = performOCR(files);
            System.out.println("OCR performed on image " + fileIdentifier + " with Tesseract");

            // send text to elasticSearch
            elasticSearchService.indexDocument(fileIdentifier, fileText);
            System.out.println("Indexing Document performed on " + fileIdentifier + " with elastic Search");

            // clean up after file
            if(!files.isEmpty()){
                boolean deleted = false;

                for(File file : files){
                    deleted = file.delete();
                }

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
