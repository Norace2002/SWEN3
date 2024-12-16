package paperless.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public class RabbitMqSender {
    @Autowired
    private RabbitTemplate template;

    @Qualifier("messageQueue")
    @Autowired
    private Queue messageQueue;

    @Qualifier("fileQueue")
    @Autowired
    private Queue fileQueue;

    public void send() {
        String message = "Hello World!";
        this.template.convertAndSend(messageQueue.getName(), message);
        System.out.println(" [x] Sent in queue1 '" + message + "'");
    }

    public void sendFile(MultipartFile file) throws IOException {
        Message message = MessageBuilder.withBody(file.getBytes()).setHeader("ContentType", file.getContentType()).build();
        this.template.convertAndSend(fileQueue.getName(), message);
        System.out.println(" [x] Sent in fileQueue: '" + message + "'");
    }

    public void sendIdentifier(String id) throws IOException{
        this.template.convertAndSend(messageQueue.getName(), id);
        System.out.println(" [x] Sent in messageQueue: '" + id + "'");
    }
}
