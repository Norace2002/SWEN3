package paperless.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * Document
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-09-22T12:43:13.854462136Z[Etc/UTC]", comments = "Generator version: 7.9.0-SNAPSHOT")
public class Document {

  private String id;

  private String title;

  private String description;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime uploadDate;

  private String fileUrl;

  private String fileType;

  private Integer size;

  public Document id(String id) {
    this.id = id;
    return this;
  }

  /**
   * Unique identifier for the document
   * @return id
   */
  
  @Schema(name = "id", description = "Unique identifier for the document", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public Document title(String title) {
    this.title = title;
    return this;
  }

  /**
   * Title of the document
   * @return title
   */
  
  @Schema(name = "title", description = "Title of the document", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("title")
  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Document description(String description) {
    this.description = description;
    return this;
  }

  /**
   * Description of the document
   * @return description
   */
  
  @Schema(name = "description", description = "Description of the document", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Document uploadDate(OffsetDateTime uploadDate) {
    this.uploadDate = uploadDate;
    return this;
  }

  /**
   * Date when the document was uploaded
   * @return uploadDate
   */
  @Valid 
  @Schema(name = "uploadDate", description = "Date when the document was uploaded", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uploadDate")
  public OffsetDateTime getUploadDate() {
    return uploadDate;
  }

  public void setUploadDate(OffsetDateTime uploadDate) {
    this.uploadDate = uploadDate;
  }

  public Document fileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
    return this;
  }

  /**
   * URL to access the document
   * @return fileUrl
   */
  
  @Schema(name = "fileUrl", description = "URL to access the document", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fileUrl")
  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }

  public Document fileType(String fileType) {
    this.fileType = fileType;
    return this;
  }

  /**
   * The file type of the document (e.g., PDF)
   * @return fileType
   */
  
  @Schema(name = "fileType", description = "The file type of the document (e.g., PDF)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fileType")
  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public Document size(Integer size) {
    this.size = size;
    return this;
  }

  /**
   * Size of the document in bytes
   * @return size
   */
  
  @Schema(name = "size", description = "Size of the document in bytes", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("size")
  public Integer getSize() {
    return size;
  }

  public void setSize(Integer size) {
    this.size = size;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Document document = (Document) o;
    return Objects.equals(this.id, document.id) &&
        Objects.equals(this.title, document.title) &&
        Objects.equals(this.description, document.description) &&
        Objects.equals(this.uploadDate, document.uploadDate) &&
        Objects.equals(this.fileUrl, document.fileUrl) &&
        Objects.equals(this.fileType, document.fileType) &&
        Objects.equals(this.size, document.size);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, description, uploadDate, fileUrl, fileType, size);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Document {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    description: ").append(toIndentedString(description)).append("\n");
    sb.append("    uploadDate: ").append(toIndentedString(uploadDate)).append("\n");
    sb.append("    fileUrl: ").append(toIndentedString(fileUrl)).append("\n");
    sb.append("    fileType: ").append(toIndentedString(fileType)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }
}

