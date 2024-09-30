/**
 * NOTE: This class is auto generated by OpenAPI Generator (https://openapi-generator.tech) (7.9.0-SNAPSHOT).
 * https://openapi-generator.tech
 * Do not edit the class manually.
 */
package paperless.controller;

import paperless.models.Document;
import paperless.models.DocumentsIdPreviewGet200Response;
import paperless.models.Metadata;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.NativeWebRequest;

import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;
import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-09-22T12:43:13.854462136Z[Etc/UTC]", comments = "Generator version: 7.9.0-SNAPSHOT")
@Validated
@Tag(name = "documents", description = "the documents API")
public interface DocumentsApi {

    default Optional<NativeWebRequest> getRequest() {
        return Optional.empty();
    }

    /**
     * GET /documents : Get a list of all documents
     *
     * @return OK (status code 200)
     *         or NOT FOUND (status code 404)
     *         or INTERNAL SERVER ERROR (status code 500)
     */
    @Operation(
        operationId = "documentsGet",
        summary = "Get a list of all documents",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", array = @ArraySchema(schema = @Schema(implementation = Document.class)))
            }),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/documents",
        produces = { "application/json" }
    )

    default ResponseEntity<List<Document>> documentsGet(
        
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "[ { \"uploadDate\" : \"2000-01-23T04:56:07.000+00:00\", \"size\" : 0, \"description\" : \"description\", \"fileUrl\" : \"fileUrl\", \"id\" : \"id\", \"title\" : \"title\", \"fileType\" : \"fileType\" }, { \"uploadDate\" : \"2000-01-23T04:56:07.000+00:00\", \"size\" : 0, \"description\" : \"description\", \"fileUrl\" : \"fileUrl\", \"id\" : \"id\", \"title\" : \"title\", \"fileType\" : \"fileType\" } ]";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * DELETE /documents/{id} : Delete a document by ID
     *
     * @param id  (required)
     * @return OK (status code 200)
     *         or NOT FOUND (status code 404)
     *         or INTERNAL SERVER ERROR (status code 500)
     */

    @Operation(
        operationId = "documentsIdDelete",
        summary = "Delete a document by ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
        }
    )
    @RequestMapping(
        method = RequestMethod.DELETE,
        value = "/documents/{id}"
    )

    default ResponseEntity<Void> documentsIdDelete(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") String id
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

    /**
     * GET /documents/{id}/download : Download a document by ID
     *
     * @param id  (required)
     * @return OK (status code 200)
     *         or NOT FOUND (status code 404)
     *         or INTERNAL SERVER ERROR (status code 500)
     */
    @Operation(
        operationId = "documentsIdDownloadGet",
        summary = "Download a document by ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/pdf", schema = @Schema(implementation = org.springframework.core.io.Resource.class))
            }),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/documents/{id}/download",
        produces = { "application/pdf" }
    )
    
    default ResponseEntity<org.springframework.core.io.Resource> documentsIdDownloadGet(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") String id
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /documents/{id} : Get a document by ID
     *
     * @param id  (required)
     * @return OK (status code 200)
     *         or NOT FOUND (status code 404)
     *         or INTERNAL SERVER ERROR (status code 500)
     */
    @Operation(
        operationId = "documentsIdGet",
        summary = "Get a document by ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Document.class))
            }),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/documents/{id}",
        produces = { "application/json" }
    )
    
    default ResponseEntity<Document> documentsIdGet(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") String id
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"uploadDate\" : \"2000-01-23T04:56:07.000+00:00\", \"size\" : 0, \"description\" : \"description\", \"fileUrl\" : \"fileUrl\", \"id\" : \"id\", \"title\" : \"title\", \"fileType\" : \"fileType\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /documents/{id}/metadata : Get metadata of a document by ID
     *
     * @param id  (required)
     * @return OK (status code 200)
     *         or NOT FOUND (status code 404)
     *         or INTERNAL SERVER ERROR (status code 500)
     */
    @Operation(
        operationId = "documentsIdMetadataGet",
        summary = "Get metadata of a document by ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = Metadata.class))
            }),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/documents/{id}/metadata",
        produces = { "application/json" }
    )
    
    default ResponseEntity<Metadata> documentsIdMetadataGet(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") String id
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"uploadDate\" : \"2000-01-23T04:56:07.000+00:00\", \"author\" : \"author\", \"id\" : \"id\", \"title\" : \"title\", \"version\" : \"version\", \"tags\" : [ \"tags\", \"tags\" ] }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * GET /documents/{id}/preview : Preview a document by ID
     *
     * @param id  (required)
     * @return OK (status code 200)
     *         or NOT FOUND (status code 404)
     *         or INTERNAL SERVER ERROR (status code 500)
     */
    @Operation(
        operationId = "documentsIdPreviewGet",
        summary = "Preview a document by ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = DocumentsIdPreviewGet200Response.class))
            }),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
        }
    )
    @RequestMapping(
        method = RequestMethod.GET,
        value = "/documents/{id}/preview",
        produces = { "application/json" }
    )
    
    default ResponseEntity<DocumentsIdPreviewGet200Response> documentsIdPreviewGet(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") String id
    ) {
        getRequest().ifPresent(request -> {
            for (MediaType mediaType: MediaType.parseMediaTypes(request.getHeader("Accept"))) {
                if (mediaType.isCompatibleWith(MediaType.valueOf("application/json"))) {
                    String exampleString = "{ \"previewUrl\" : \"previewUrl\" }";
                    ApiUtil.setExampleResponse(request, "application/json", exampleString);
                    break;
                }
            }
        });
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * PUT /documents/{id} : Update a document by ID
     *
     * @param id  (required)
     * @param document The updated document data (required)
     * @return OK (status code 200)
     *         or BAD REQUEST (status code 400)
     *         or NOT FOUND (status code 404)
     *         or INTERNAL SERVER ERROR (status code 500)
     */
    @Operation(
        operationId = "documentsIdPut",
        summary = "Update a document by ID",
        responses = {
            @ApiResponse(responseCode = "200", description = "OK"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "404", description = "NOT FOUND"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
        }
    )
    @RequestMapping(
        method = RequestMethod.PUT,
        value = "/documents/{id}",
        consumes = { "application/json" }
    )
    
    default ResponseEntity<Void> documentsIdPut(
        @Parameter(name = "id", description = "", required = true, in = ParameterIn.PATH) @PathVariable("id") String id,
        @Parameter(name = "Document", description = "The updated document data", required = true) @Valid @RequestBody Document document
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }


    /**
     * POST /documents : Upload a new document
     *
     * @param body The document to upload (required)
     * @return CREATED (status code 201)
     *         or BAD REQUEST (status code 400)
     *         or INTERNAL SERVER ERROR (status code 500)
     */
    @Operation(
        operationId = "documentsPost",
        summary = "Upload a new document",
        responses = {
            @ApiResponse(responseCode = "201", description = "CREATED"),
            @ApiResponse(responseCode = "400", description = "BAD REQUEST"),
            @ApiResponse(responseCode = "500", description = "INTERNAL SERVER ERROR")
        }
    )
    @RequestMapping(
        method = RequestMethod.POST,
        value = "/documents",
        consumes = { "application/pdf" }
    )
    
    default ResponseEntity<Void> documentsPost(
        @Parameter(name = "body", description = "The document to upload", required = true) @Valid @RequestBody org.springframework.core.io.Resource body
    ) {
        return new ResponseEntity<>(HttpStatus.NOT_IMPLEMENTED);

    }

}
