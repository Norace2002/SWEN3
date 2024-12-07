package paperless.rabbitmq;

import lombok.AllArgsConstructor;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

@Service
@AllArgsConstructor
public class RabbitMqReceiver {
    /*
    @RabbitListener(queues = "messageQueue")
    public void receive(String in) throws InterruptedException {
        System.out.println(" [x] Received  in queue 1'" + in + "q1'");
        Thread.sleep(3000);
        System.out.println("Done queue 1");
    }
    */

    @RabbitListener(queues = "returnQueue")
    public void receiveFileContent(String message) {

        // Process the received file bytes and contentType as needed
        // You can save the file, perform further processing, etc.

        System.out.println("Received file with content: " + message);
    }
}
