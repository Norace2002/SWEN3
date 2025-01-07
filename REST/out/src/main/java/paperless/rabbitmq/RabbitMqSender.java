package paperless.rabbitmq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private final Logger logger = LogManager.getLogger();

    public void sendIdentifier(String id) throws IOException{
        this.template.convertAndSend(messageQueue.getName(), id);
        logger.info(" [x] Sent in messageQueue: '" + id + "'");
    }
}
