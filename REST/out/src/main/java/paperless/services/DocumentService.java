package paperless.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import org.mapstruct.factory.Mappers;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import paperless.mapper.DocumentDTO;
import paperless.mapper.DocumentMapper;

import paperless.models.Document;
import paperless.models.DocumentsIdPreviewGet200Response;

import paperless.rabbitmq.RabbitMqSender;
import paperless.repositories.DocumentRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    private final DocumentMapper documentMapper = Mappers.getMapper(DocumentMapper.class);

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private RabbitMqSender rabbitMqSender;

    @Autowired
    private MinIOService minIOStorage;

    // @Getter
    // for whatever reason the getter from lombok here fails (02.11.24) -> manually created below
    private final ObjectMapper objectMapper;

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

    public String documentToString(Document model){
        try{
            return this.getObjectMapper().writeValueAsString(model);
        }
        catch(JsonProcessingException e){
            throw new RuntimeException(e);
        }
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ResponseEntity<List<DocumentDTO>> getAllDocumentsResponse(){
        List<Document> documents = documentRepository.findAll();
        List<DocumentDTO> documentDTOs = new ArrayList<>();

        for(Document doc : documents){
            documentDTOs.add(documentMapper.documentToDocumentDTO(doc));
        }
        return new ResponseEntity<>(documentDTOs, HttpStatus.OK);
    }

    public ResponseEntity<DocumentDTO> getDocumentByIdResponse(String id){
        Optional<Document> optionalDocument = documentRepository.findById(id);

        if(optionalDocument.isPresent()){
            Document returnDocument = optionalDocument.get();
            DocumentDTO dto = documentMapper.documentToDocumentDTO(returnDocument);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //ToDo:
    // * find out if this actually works
    // * work with filepath/classpath/?
    public ResponseEntity<Resource> downloadDocumentResponse(String id){
        Optional<Document> optionalDocument = documentRepository.findById(id);

        //ToDo:Test minIO Download
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

    public ResponseEntity<Document> getDocumentMetadataResponse(String id){
        Optional<Document> optionalData = documentRepository.findById(id);

        if(optionalData.isPresent()){
            Document returnData = optionalData.get();
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    //ToDo:
    // * find out how a file is sent from frontend and how it will be stored
    public ResponseEntity<Void> createNewDocumentResponse(String documentString, MultipartFile pdfFile){
        Document documentModel = stringToDocument(documentString);

        try{
            // rabbitmq message / handle actual file
            this.rabbitMqSender.send();
            byte[] byteArray = pdfFile.getBytes();

            // minIO store File
            minIOStorage.upload(documentModel.getTitle(),byteArray);

            // save document data
            documentRepository.save(documentModel);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(RuntimeException | IOException e){
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
