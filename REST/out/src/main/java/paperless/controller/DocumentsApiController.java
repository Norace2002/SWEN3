package paperless.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import org.springframework.web.multipart.MultipartFile;
import paperless.models.Document;
import paperless.models.DocumentsIdPreviewGet200Response;
import paperless.models.Metadata;

import java.util.List;
import java.util.Optional;

import jakarta.annotation.Generated;
import paperless.services.DocumentService;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-09-22T12:43:13.854462136Z[Etc/UTC]", comments = "Generator version: 7.9.0-SNAPSHOT")
@CrossOrigin(origins = "http://localhost:8080")
@Controller
@RequestMapping("${openapi.documentManagerSystemServer.base-path:}")
public class DocumentsApiController implements DocumentsApi {

    private final NativeWebRequest request;

    @Autowired
    public DocumentsApiController(NativeWebRequest request) {
        this.request = request;
    }

    @Autowired
    private DocumentService documentService;

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return Optional.ofNullable(request);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    @GetMapping("/documents")
    public ResponseEntity<List<Document>> documentsGet(){
        return documentService.getAllDocumentsResponse();
    }

    @Override
    @GetMapping("/documents/{id}")
    public ResponseEntity<Document> documentsIdGet(@PathVariable String id){
        return documentService.getDocumentByIdResponse(id);
    }

    @Override
    @GetMapping("/documents/{id}/download")
    public ResponseEntity<Resource> documentsIdDownloadGet(@PathVariable String id){
        return documentService.downloadDocumentResponse(id);
    }

    @GetMapping("/documents/{id}/preview")
    public ResponseEntity<DocumentsIdPreviewGet200Response> documentsIdPreviewGet(@PathVariable String id){
        return documentService.getDocumentPreviewResponse(id);
    }

    @Override
    @GetMapping("/documents/{id}/metadata")
    public ResponseEntity<Metadata> documentsIdMetadataGet(@PathVariable String id){
        return documentService.getDocumentMetadataResponse(id);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    @PostMapping("/documents")
    public ResponseEntity<Void> documentsPost(
            @RequestPart("document") String document,
            @RequestPart("metadata") String metadata,
            @RequestPart("file") MultipartFile pdfFile
    ){
        return documentService.createNewDocumentResponse(document, metadata, pdfFile);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    @PutMapping("/documents/{id}")
    public ResponseEntity<Void> documentsIdPut(@PathVariable String id, @RequestBody Document document) {
        return documentService.editExistingDocumentResponse(id, document);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @Override
    @DeleteMapping("/documents/{id}")
    public ResponseEntity<Void> documentsIdDelete(@PathVariable String id){
        return documentService.deleteExistingDocumentResponse(id);
    }

}
