package paperless.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import org.springframework.web.multipart.MultipartFile;
import paperless.mapper.DocumentDTO;
import paperless.models.Document;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import jakarta.annotation.Generated;
import paperless.services.DocumentService;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-09-22T12:43:13.854462136Z[Etc/UTC]", comments = "Generator version: 7.9.0-SNAPSHOT")
@CrossOrigin(origins = "http://localhost:8080")
@Controller
@RequestMapping("${openapi.documentManagerSystemServer.base-path:}")
public class DocumentsApiController{

    private final NativeWebRequest request;

    @Autowired
    public DocumentsApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Autowired
    private DocumentService documentService;


    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    private final Logger logger = LogManager.getLogger();

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/documents")
    public ResponseEntity<List<DocumentDTO>> getDocumentDtos(){
        logger.debug("Received request GET - /documents");
        return documentService.getAllDocumentsResponse();
    }


    @GetMapping("/documents/{id}")
    public ResponseEntity<DocumentDTO> getDocumentDtoById(@PathVariable String id){
        UUID uuid = UUID.fromString(id);
        logger.debug("Received request GET - /documents/" + uuid);
        return documentService.getDocumentByIdResponse(uuid);
    }

    @GetMapping("/documents/{id}/download")
    public ResponseEntity<byte[]> getDocumentDownload(@PathVariable String id){
        UUID uuid = UUID.fromString(id);
        logger.debug("Received request GET - /documents/" + uuid + "/download");
        return documentService.downloadDocumentResponse(uuid);
    }

    @GetMapping("/documents/{id}/preview")
    public ResponseEntity<Document> getDocumentPreview(@PathVariable String id){
        UUID uuid = UUID.fromString(id);
        logger.debug("Received request GET - /documents/" + uuid + "/preview");
        return documentService.getDocumentPreviewResponse(uuid);
    }

    @GetMapping("/documents/{id}/metadata")
    public ResponseEntity<Document> getDocumentMetadata(@PathVariable String id){
        UUID uuid = UUID.fromString(id);
        logger.debug("Received request GET - /documents/" + uuid + "/metadata");
        return documentService.getDocumentMetadataResponse(uuid);
    }

    @GetMapping("/documents/search/{key}")
    public ResponseEntity<List<DocumentDTO>> getDocumentSearch(@PathVariable String key){
        logger.debug("Received request GET - /documents/search" );
        return documentService.getDocumentSearchKeyword(key);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/documents")
    public ResponseEntity<Void> postDocument(
            @RequestPart("document") String document,
            @RequestPart("file") MultipartFile pdfFile
    ){
        logger.debug("Received request POST - /documents");
        return documentService.createNewDocumentResponse(document, pdfFile);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping("/documents/{id}")
    public ResponseEntity<Void> editDocumentById(@PathVariable String id, @RequestBody Document document) {
        UUID uuid = UUID.fromString(id);
        logger.debug("Received request PUT - /documents/" + uuid);
        return documentService.editExistingDocumentResponse(uuid, document);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping("/documents/{id}")
    public ResponseEntity<Void> deleteDocumentById(@PathVariable String id){
        UUID uuid = UUID.fromString(id);
        logger.debug("Received request DELETE - /documents/" + uuid);
        return documentService.deleteExistingDocumentResponse(uuid);
    }

}
