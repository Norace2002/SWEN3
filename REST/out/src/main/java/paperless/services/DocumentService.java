package paperless.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import paperless.models.Document;
import paperless.models.DocumentsIdPreviewGet200Response;
import paperless.models.Metadata;
import paperless.repositories.DocumentRepository;
import paperless.repositories.MetadataRepository;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private MetadataRepository metadataRepository;

    @Autowired
    private ResourceLoader resourceLoader;

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

    //ToDo:
    // * find out if this actually works
    // * work with filepath/classpath/?
    public ResponseEntity<Resource> downloadDocumentResponse(String id){
        Optional<Document> optionalDocument = documentRepository.findById(id);

        if(optionalDocument.isPresent()) {
            Document foundDocument = optionalDocument.get();
            Resource downloadResorce;
            try {
                downloadResorce = resourceLoader.getResource("filesystem:" + foundDocument.getFileUrl());
                return new ResponseEntity<>(downloadResorce, HttpStatus.OK);
            } catch (RuntimeException e) {
                System.out.println(e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //ToDo:
    // * implement some way of sending an image of the first page as a preview
    public ResponseEntity<DocumentsIdPreviewGet200Response> getDocumentPreviewResponse(String id){
        // how to manage preview?
        Optional<Document> optionalDocument = documentRepository.findById(id);

        if(optionalDocument.isPresent()){
            Document returnDocument = optionalDocument.get();
            DocumentsIdPreviewGet200Response previewObject = new DocumentsIdPreviewGet200Response();
            previewObject.setPreviewUrl(returnDocument.getFileUrl());
            return new ResponseEntity<>(previewObject, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
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

    //ToDo:
    // * find out how a file is sent from frontend and how it will be stored
    public ResponseEntity<Void> createNewDocumentResponse(Document document){
        try{
            documentRepository.save(document);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(RuntimeException e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //ToDo:
    // * follow-up problem of not knowing how files are created/stored
    public ResponseEntity<Void> editExistingDocumentResponse(String id, Document document){
        try{
            documentRepository.deleteById(document.getId());
            documentRepository.save(document);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(RuntimeException e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Void> deleteExistingDocumentResponse(String id){
        try{
            documentRepository.deleteById(id);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(RuntimeException e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
