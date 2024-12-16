package OCR.rabbitmq;

import OCR.service.OcrService;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RabbitMqReceiver {
    @Autowired
    private OcrService ocrService;

    @RabbitListener(queues = "messageQueue")
    public void receiveMessage(String message) throws Exception {
        System.out.println(" [x] Received  in messageQueue: '" + message + "'");

        // call ocr service with id of file
        ocrService.returnFileContent(message);

        System.out.println("Done messageQueue");
    }
    @RabbitListener(queues = "fileQueue")
    public void receiveFile(Message message) {
        byte[] fileBytes = message.getBody();

        // Process the received file bytes and contentType as needed
        // You can save the file, perform further processing, etc.

        System.out.println("Received file in fileQueue");
    }
}
