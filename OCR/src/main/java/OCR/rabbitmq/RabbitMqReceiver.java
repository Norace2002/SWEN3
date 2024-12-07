package OCR.rabbitmq;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RabbitMqReceiver {
    @RabbitListener(queues = "messageQueue")
    public void receive(String in) throws InterruptedException {
        System.out.println(" [x] Received  in queue 1'" + in + "q1'");
        Thread.sleep(3000);
        System.out.println("Done queue 1");
    }
    @RabbitListener(queues = "fileQueue")
    public void receiveFile(Message message) {
        byte[] fileBytes = message.getBody();
        String contentType = (String) message.getMessageProperties().getHeaders().get("ContentType");

        // Process the received file bytes and contentType as needed
        // You can save the file, perform further processing, etc.

        System.out.println("Received file with content type: " + contentType);
    }
}
