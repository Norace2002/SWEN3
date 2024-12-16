package OCR.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMqConfig {
    @Bean(name = "messageQueue") // Ensure the name matches your qualifier
    public Queue messageQueue() {
        return new Queue("messageQueue", true);
    }

    @Bean(name = "fileQueue") // Ensure the name matches your qualifier
    public Queue fileQueue() {
        return new Queue("fileQueue", true);
    }

    @Bean(name = "returnQueue")
    public Queue returnQueue() {
        return new Queue("returnQueue", true);
    }
}
