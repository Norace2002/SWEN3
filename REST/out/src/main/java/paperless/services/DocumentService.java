package paperless.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import paperless.models.Document;
import paperless.repositories.DocumentRepository;

import java.util.List;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;
    public DocumentService(){

    }

    public List<Document> getAllDocuments(){
        return(documentRepository.findAll());
    }
}
