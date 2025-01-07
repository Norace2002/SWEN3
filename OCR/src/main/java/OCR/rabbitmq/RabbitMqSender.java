package OCR.rabbitmq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    Logger logger = LogManager.getLogger();

    public void returnFileContent(String text) {
        Message message = MessageBuilder.withBody(text.getBytes()).build();
        this.template.convertAndSend(returnQueue.getName(), message);
        logger.debug("FileContent sent in returnQueue: '" + message + "'");
    }
}
