package paperless.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import paperless.models.Document;

@Mapper
public interface DocumentMapper {
    DocumentMapper INSTANCE = Mappers.getMapper(DocumentMapper.class);


    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "fileUrl", target = "fileUrl")
    @Mapping(source = "ocrReadable", target = "ocrReadable")


    DocumentDTO documentToDocumentDTO(Document document);

    @Mapping(source = "id", target = "id")
    @Mapping(source = "title", target = "title")
    @Mapping(source = "description", target = "description")
    @Mapping(source = "fileUrl", target = "fileUrl")
    @Mapping(source = "ocrReadable", target = "ocrReadable")
    Document documentDTOToDocument(DocumentDTO documentDTO);
}
