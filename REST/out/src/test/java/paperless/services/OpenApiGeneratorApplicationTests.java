package paperless.services;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import paperless.mapper.DocumentDTO;
import paperless.mapper.DocumentMapper;
import paperless.models.Document;
import paperless.repositories.DocumentRepository;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class DocumentServiceTest {

    @InjectMocks
    private DocumentService documentService;

    @Mock
    private DocumentRepository documentRepository;

    @Mock
    private DocumentMapper documentMapper;

    @Mock
    private ElasticSearchService elasticSearchService;

    @Mock
    private MinIOService minIOStorage;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetDocumentByIdResponseNotFound() {
        UUID documentId = UUID.randomUUID();

        when(documentRepository.findById(documentId)).thenReturn(Optional.empty());

        ResponseEntity<DocumentDTO> response = documentService.getDocumentByIdResponse(documentId);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testGetDocumentMetadataResponseNotFound() {
        UUID documentId = UUID.randomUUID();

        when(documentRepository.findById(documentId)).thenReturn(Optional.empty());

        ResponseEntity<Document> response = documentService.getDocumentMetadataResponse(documentId);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testDownloadDocumentResponseNotFound() {
        UUID documentId = UUID.randomUUID();

        when(documentRepository.findById(documentId)).thenReturn(Optional.empty());

        ResponseEntity<byte[]> response = documentService.downloadDocumentResponse(documentId);

        assertNotNull(response);
        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    void testStringToDocumentHandlesInvalidJson() {
        String invalidJson = "invalid json";

        assertThrows(RuntimeException.class, () -> documentService.stringToDocument(invalidJson));
    }

    @Test
    void testStringToDocumentHandlesEmptyString() {
        String emptyJson = "";

        assertThrows(RuntimeException.class, () -> documentService.stringToDocument(emptyJson));
    }

    @Test
    void testElasticSearchServiceMockIntegration() {
        String keyword = "test";
        when(elasticSearchService.searchDocumentsByKeyword(keyword)).thenReturn(List.of());

        List<String> result = elasticSearchService.searchDocumentsByKeyword(keyword);

        assertNotNull(result);
        assertTrue(result.isEmpty());
        verify(elasticSearchService, times(1)).searchDocumentsByKeyword(keyword);
    }

    @Test
    void testMinIOStorageMockIntegration() {
        String fileIdentifier = "test-file-id";
        byte[] fileContent = "Test Content".getBytes();

        doNothing().when(minIOStorage).upload(fileIdentifier, fileContent);

        minIOStorage.upload(fileIdentifier, fileContent);

        verify(minIOStorage, times(1)).upload(fileIdentifier, fileContent);
    }

    @Test
    void testGetDocumentMetadataResponseSuccess() {
        UUID documentId = UUID.randomUUID();
        Document mockDocument = mock(Document.class);

        when(documentRepository.findById(documentId)).thenReturn(Optional.of(mockDocument));

        ResponseEntity<Document> response = documentService.getDocumentMetadataResponse(documentId);

        assertNotNull(response);
        assertEquals(200, response.getStatusCodeValue());
        assertEquals(mockDocument, response.getBody());
    }

    @Test
    void testDocumentMapperIntegration() {
        Document mockDocument = mock(Document.class);
        DocumentDTO mockDocumentDTO = mock(DocumentDTO.class);

        when(documentMapper.documentToDocumentDTO(mockDocument)).thenReturn(mockDocumentDTO);

        DocumentDTO result = documentMapper.documentToDocumentDTO(mockDocument);

        assertNotNull(result);
        assertSame(mockDocumentDTO, result);
        verify(documentMapper, times(1)).documentToDocumentDTO(mockDocument);
    }

    @Test
    void testElasticSearchDeleteIntegration() {
        String documentId = UUID.randomUUID().toString();

        doNothing().when(elasticSearchService).deleteDocumentById(documentId);

        elasticSearchService.deleteDocumentById(documentId);

        verify(elasticSearchService, times(1)).deleteDocumentById(documentId);
    }
}


