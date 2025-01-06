package paperless.rabbitmq;

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

    public void sendIdentifier(String id) throws IOException{
        this.template.convertAndSend(messageQueue.getName(), id);
        System.out.println(" [x] Sent in messageQueue: '" + id + "'");
    }
}
