package paperless.models;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;

import io.swagger.v3.oas.annotations.media.Schema;

@Entity
public class Document {
  @Id
  @Column
  @Schema(name = "id", description = "Unique identifier for the document", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("id")
  private UUID id; //Maybe int or long - not decided yet

  @Column
  @Schema(name = "title", description = "Title of the document", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("title")
  private String title;

  @Column
  @Schema(name = "author", description = "Author of the document", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("author")
  private String author;

  @Column
  @Size(max = 500)
  @Schema(name = "description", description = "Description of the document", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("description")
  private String description;

  @Column
  @Schema(name = "uploadDate", description = "Date when the document was uploaded", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uploadDate")
  private String uploadDate;

  @Column
  @Schema(name = "fileType", description = "Type of file (pdf, txt etc.)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fileType")
  private String fileType;

  @Column
  @Schema(name = "fileSize", description = "Size of file in bytes", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fileSize")
  private int fileSize;

  @Column
  @NotNull
  @Schema(name = "fileUrl", description = "URL to access the document", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("fileUrl")
  private String fileUrl;

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // auto-generated methods (keep, maybe useful)

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
        Objects.equals(this.fileUrl, document.fileUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, description, fileUrl);
  }

  @Override
  public String toString() {
      return "class Document {\n" +
            "    id: " + toIndentedString(id) + "\n" +
            "    title: " + toIndentedString(title) + "\n" +
            "    description: " + toIndentedString(description) + "\n" +
            "    fileUrl: " + toIndentedString(fileUrl) + "\n" +
            "}";
  }

  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
  // manual getters/setters since lombok is joking around

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public String getUploadDate() {
    return uploadDate;
  }

  public void setUploadDate(String uploadDate) {
    this.uploadDate = uploadDate;
  }

  public String getFileType() {
    return fileType;
  }

  public void setFileType(String fileType) {
    this.fileType = fileType;
  }

  public int getFileSize() {
    return fileSize;
  }

  public void setFileSize(int fileSize) {
    this.fileSize = fileSize;
  }

  public String getFileUrl() {
    return fileUrl;
  }

  public void setFileUrl(String fileUrl) {
    this.fileUrl = fileUrl;
  }
}

