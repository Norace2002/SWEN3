package OCR.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class RabbitMqSender {
    @Autowired
    private RabbitTemplate template;

    @Qualifier("messageQueue")
    @Autowired
    private Queue messageQueue;

    @Qualifier("returnQueue")
    @Autowired
    private Queue returnQueue;

    /*
    public void send() {
        String message = "Hello World2!";
        this.template.convertAndSend(messageQueue.getName(), message);
        System.out.println(" [x] Sent in messageQueue: '" + message + "'");
    }
    */

    public void returnFileContent(String text) throws IOException{
        Message message = MessageBuilder.withBody(text.getBytes()).build();
        this.template.convertAndSend(returnQueue.getName(), message);
        System.out.println(" [x] Sent in returnQueue: '" + message + "'");
    }
}
