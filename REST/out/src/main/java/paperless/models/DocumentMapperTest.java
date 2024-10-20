package paperless.models;

import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;

import static org.junit.jupiter.api.Assertions.*;

class DocumentMapperTest {

    private final DocumentMapper documentMapper = Mappers.getMapper(DocumentMapper.class);

    @Test
    void testDocumentToDocumentDTO() {
        Document document = new Document();
        document.setId("123");
        document.setTitle("Test Document");

        DocumentDTO documentDTO = documentMapper.documentToDocumentDTO(document);

        assertEquals(document.getId(), documentDTO.getId());
        assertEquals(document.getTitle(), documentDTO.getTitle());
    }

}
