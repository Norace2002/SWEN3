package paperless.rabbitmq;

import lombok.AllArgsConstructor;
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

        // message should contain id if successful
        System.out.println("Received file with content: " + message);

        // if message not empty -> contains id -> update existing entry
        if(!message.isEmpty()){
            UUID id = UUID.fromString(message);
            Optional<Document> optionalDocument = documentRepository.findById(id);

            if(optionalDocument.isPresent()){
                Document returnDocument = optionalDocument.get();
                returnDocument.setOcrReadable(true);

                documentRepository.save(returnDocument);
            }
        }
    }
}
