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
        tesseract.setDatapath("/app/tessdata_best");
        try {
            return tesseract.doOCR(file);
        } catch (TesseractException e) {
            return null;
        }
    }

    public File convertPdfToImage(byte[] pdfBytes) throws Exception {
        // load PDF-Document
        PDFDocument document = new PDFDocument();
        document.load(new ByteArrayInputStream(pdfBytes));

        // konfigure renderer
        SimpleRenderer renderer = new SimpleRenderer();
        renderer.setResolution(300); // 300 DPI

        // render
        List<Image> images = renderer.render(document);

        // save image
        File outputImage = File.createTempFile("output", ".png");
        ImageIO.write((RenderedImage) images.get(0), "png", outputImage);

        return outputImage;
    }

    public void returnFileContent(String fileIdentifier) throws Exception {
        // download file bytes from minio
        byte[] byteStream = minIOService.download(fileIdentifier);

        // render image from bytestream
        File file = convertPdfToImage(byteStream);

        // perform OCR
        String fileText = performOCR(file);

        if(fileText != null){
            rabbitMqSender.returnFileContent(fileText);
        } else{
            System.out.println("problem with OCRService");
        }
    }


}
