package paperless.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import paperless.models.Document;
import paperless.models.DocumentsIdPreviewGet200Response;
import paperless.models.Metadata;

import paperless.rabbitmq.RabbitMqSender;
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

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private RabbitMqSender rabbitMqSender;

    @Autowired
    private MinIOService minIOStorage;

    // @Getter
    // for whatever reason the getter from lombok here fails (02.11.24) -> manually created below
    private ObjectMapper objectMapper;

    public DocumentService(){
        this.objectMapper = new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    private ObjectMapper getObjectMapper(){
        return this.objectMapper;
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public Document stringToDocument(String input){
        try{
            return this.getObjectMapper().readValue(input, new TypeReference<Document>(){});
        }
        catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    public Metadata stringToMetadata(String input){
        try{
            return this.getObjectMapper().readValue(input, new TypeReference<Metadata>(){});
        }
        catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    public String documentToString(Document model){
        try{
            return this.getObjectMapper().writeValueAsString(model);
        }
        catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    public String metadataToString(Metadata model){
        try{
            return this.getObjectMapper().writeValueAsString(model);
        }
        catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

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
    // * Test minIO Download
    public ResponseEntity<Resource> downloadDocumentResponse(String id){
        Optional<Document> optionalDocument = documentRepository.findById(id);


        String filecontent = new String(minIOStorage.download(id));

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
    public ResponseEntity<Void> createNewDocumentResponse(String documentString, String metadataString, MultipartFile pdfFile){
        Document documentModel = stringToDocument(documentString);
        Metadata metadataModel = stringToMetadata(metadataString);

        try{
            // rabbitmq message
            this.rabbitMqSender.send();


            // minIO store File
            minIOStorage.upload(documentModel.getId(), byteArray);

            // save document data

            documentRepository.save(documentModel);
            metadataRepository.save(metadataModel);
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
