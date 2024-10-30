package paperless.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import paperless.models.Document;
import paperless.models.DocumentsIdPreviewGet200Response;
import paperless.models.Metadata;
import paperless.repositories.DocumentRepository;
import paperless.repositories.MetadataRepository;

import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private MetadataRepository metadataRepository;

    public DocumentService(){

    }

    public ResponseEntity<List<Document>> getAllDocumentsResponse(){
        return new ResponseEntity<>(documentRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity<Document> getDocumentByIdResponse(String id){
        Optional<Document> optionalDocument = documentRepository.findById(id);

        if(optionalDocument.isPresent()){
            Document returnDocument = optionalDocument.get();
            return new ResponseEntity<>(returnDocument, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Resource> downloadDocumentResponse(String id){
        // how to manage download? json object? pdf? java object?

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<DocumentsIdPreviewGet200Response> getDocumentPreviewResponse(String id){
        // how to manage preview?

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Metadata> getDocumentMetadataResponse(String id){
        Optional<Metadata> optionalData = metadataRepository.findById(id);

        if(optionalData.isPresent()){
            Metadata returnData = optionalData.get();
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Void> createNewDocumentResponse(Resource requestBody){
        // how turn Resource into db entity?

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> editExistingDocumentResponse(String id, Document requestBody){
        // how turn Resource into db entity?

        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
    }

    public ResponseEntity<Void> deleteExistingDocumentResponse(String id){
        try{
            documentRepository.deleteById(id);
        } catch(RuntimeException e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
