package paperless.rabbitmq;


import org.apache.logging.log4j.LogManager;
import lombok.AllArgsConstructor;
import org.apache.logging.log4j.Logger;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paperless.models.Document;
import paperless.repositories.DocumentRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class RabbitMqReceiver {
    @Autowired
    DocumentRepository documentRepository;

    private final Logger logger = LogManager.getLogger();

    @RabbitListener(queues = "returnQueue")
    public void receiveFileContent(String message) {

        // Process the received file bytes and contentType as needed
        // You can save the file, perform further processing, etc.

        // message should contain id if successful
        logger.debug("Received file with content: " + message);

        // if message not empty -> contains id -> update existing entry
        if(!message.isEmpty()){
            UUID id = UUID.fromString(message);
            Optional<Document> optionalDocument = documentRepository.findById(id);

            if(optionalDocument.isPresent()){
                Document returnDocument = optionalDocument.get();
                returnDocument.setOcrReadable(true);

                documentRepository.save(returnDocument);
                logger.debug("Set database entry with uuid " + id + " to OCR readable - true");
            }
        }
    }
}
