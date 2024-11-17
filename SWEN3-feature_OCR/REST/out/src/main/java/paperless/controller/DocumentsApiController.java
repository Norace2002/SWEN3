package paperless.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import org.springframework.web.multipart.MultipartFile;
import paperless.mapper.DocumentDTO;
import paperless.models.Document;
import paperless.models.DocumentsIdPreviewGet200Response;

import java.util.List;
import java.util.Optional;

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

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @GetMapping("/documents")
    public ResponseEntity<List<DocumentDTO>> getDocumentDtos(){
        return documentService.getAllDocumentsResponse();
    }

    @GetMapping("/documents/{id}")
    public ResponseEntity<DocumentDTO> getDocumentDtoById(@PathVariable String id){
        return documentService.getDocumentByIdResponse(id);
    }

    @GetMapping("/documents/{id}/download")
    public ResponseEntity<Resource> getDocumentDownload(@PathVariable String id){
        return documentService.downloadDocumentResponse(id);
    }

    @GetMapping("/documents/{id}/preview")
    public ResponseEntity<DocumentsIdPreviewGet200Response> getDocumentPreview(@PathVariable String id){
        return documentService.getDocumentPreviewResponse(id);
    }

    @GetMapping("/documents/{id}/metadata")
    public ResponseEntity<Document> getDocumentMetadata(@PathVariable String id){
        return documentService.getDocumentMetadataResponse(id);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/documents")
    public ResponseEntity<Void> postDocument(
            @RequestPart("document") String document,
            @RequestPart("file") MultipartFile pdfFile
    ){
        return documentService.createNewDocumentResponse(document, pdfFile);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PutMapping("/documents/{id}")
    public ResponseEntity<Void> editDocumentById(@PathVariable String id, @RequestBody Document document) {
        return documentService.editExistingDocumentResponse(id, document);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @DeleteMapping("/documents/{id}")
    public ResponseEntity<Void> deleteDocumentById(@PathVariable String id){
        return documentService.deleteExistingDocumentResponse(id);
    }

}
