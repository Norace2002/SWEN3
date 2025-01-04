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
import java.util.UUID;

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

    @Autowired
    private ElasticSearchService elasticSearchService;

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

    public ResponseEntity<DocumentDTO> getDocumentByIdResponse(UUID id){
        Optional<Document> optionalDocument = documentRepository.findById(id);

        if(optionalDocument.isPresent()){
            Document returnDocument = optionalDocument.get();
            DocumentDTO dto = documentMapper.documentToDocumentDTO(returnDocument);
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<byte[]> downloadDocumentResponse(UUID id){
        Optional<Document> optionalDocument = documentRepository.findById(id);

        if(optionalDocument.isPresent()) {
            Document foundDocument = optionalDocument.get();
            byte[] filecontent = minIOStorage.download(String.valueOf(id));
            try {
                return new ResponseEntity<>(filecontent, HttpStatus.OK);
            } catch (RuntimeException e) {
                System.out.println(e);
                return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
            }
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<DocumentsIdPreviewGet200Response> getDocumentPreviewResponse(UUID id){
        Optional<Document> optionalDocument = documentRepository.findById(id);

        if(optionalDocument.isPresent()){
            return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Document> getDocumentMetadataResponse(UUID id){
        Optional<Document> optionalData = documentRepository.findById(id);

        if(optionalData.isPresent()){
            Document returnData = optionalData.get();
            return new ResponseEntity<>(returnData, HttpStatus.OK);
        } else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<List<DocumentDTO>> getDocumentSearchKeyword(String key){
        List<String> documentIDs = elasticSearchService.searchDocumentsByKeyword(key);

        System.out.println("Ids: " + documentIDs);

        if(!documentIDs.isEmpty()){
            List<Optional<Document>> optionalDocuments = new ArrayList<>();
            List<Document> documents = new ArrayList<>();
            List<DocumentDTO> documentsDTO = new ArrayList<>();

            for(String id : documentIDs){
                optionalDocuments.add(documentRepository.findById(UUID.fromString(id)));
            }

            for(Optional<Document> opDoc : optionalDocuments){
                documents.add(opDoc.get());
            }

            for(Document document : documents){
                documentsDTO.add(documentMapper.documentToDocumentDTO(document));
            }

            return new ResponseEntity<>(documentsDTO, HttpStatus.OK);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    //ToDo:
    // * find out how a file is sent from frontend and how it will be stored
    public ResponseEntity<Void> createNewDocumentResponse(String documentString, MultipartFile pdfFile){
        Document documentModel = stringToDocument(documentString);

        try{
            byte[] byteArray = pdfFile.getBytes();

            // save document data
            documentRepository.save(documentModel);

            // minIO store File
            minIOStorage.upload(String.valueOf(documentModel.getId()), byteArray);

            // rabbitmq message
            this.rabbitMqSender.sendIdentifier(documentModel.getId().toString());

            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch(RuntimeException | IOException e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    //ToDo:
    // * follow-up problem of not knowing how files are created/stored
    public ResponseEntity<Void> editExistingDocumentResponse(UUID id, Document document){
        try{
            documentRepository.deleteById(id);
            documentRepository.save(document);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(RuntimeException e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<Void> deleteExistingDocumentResponse(UUID id){
        try{
            //delete from DB, minIO and elasticSearch
            documentRepository.deleteById(id);
            minIOStorage.delete(id.toString());
            elasticSearchService.deleteDocumentById(id.toString());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch(RuntimeException e){
            System.out.println(e);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
