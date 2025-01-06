package OCR.rabbitmq;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class RabbitMqSender {
    @Autowired
    private RabbitTemplate template;

    @Qualifier("returnQueue")
    @Autowired
    private Queue returnQueue;

    public void returnFileContent(String text) {
        Message message = MessageBuilder.withBody(text.getBytes()).build();
        this.template.convertAndSend(returnQueue.getName(), message);
        System.out.println(" [x] Sent in returnQueue: '" + message + "'");
    }
}
