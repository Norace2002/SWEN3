package paperless.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import org.springframework.format.annotation.DateTimeFormat;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.media.Schema;


import jakarta.annotation.Generated;

/**
 * Metadata
 */

@Entity
public class Metadata {

  @Id
  @Column
  private String id;

  @Column
  private String author;

  @Column
  private String uploaddate;

  @Column
  private String filetype;

  @Column
  private int filesize;

  @Valid
  @Column
  private List<String> tags = new ArrayList<>();

  @Column
  private String version;

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

  /**
   * Author of the document
   * @return author
   */
  
  @Schema(name = "author", description = "Author of the document", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("author")
  public String getAuthor() {
    return author;
  }

  public void setAuthor(String author) {
    this.author = author;
  }

  /**
   * Date when the document was uploaded
   * @return uploadDate
   */

  @Valid 
  @Schema(name = "uploadDate", description = "Date when the document was uploaded", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("uploadDate")
  public String getUploadDate() {
    return uploaddate;
  }

  public void setUploadDate(String uploadDate) {
    this.uploaddate = uploadDate;
  }

  /**
   * Tags associated with the document
   * @return tags
   */
  
  @Schema(name = "tags", description = "Tags associated with the document", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("tags")
  public List<String> getTags() {
    return tags;
  }

  public void setTags(List<String> tags) {
    this.tags = tags;
  }

  public Metadata addTagsItem(String tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
  }

  /**
   * Version of the document
   * @return version
   */
  
  @Schema(name = "version", description = "Version of the document", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("version")
  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

  @Schema(name = "filetype", description = "Type of file (pdf, txt etc.)", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("filetype")
  public String getFiletype() {
    return filetype;
  }

  public void setFiletype(String filetype) {
    this.filetype = filetype;
  }

  @Schema(name = "filesize", description = "Size of file in bytes", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("filesize")
  public int getFilesize() {
    return filesize;
  }

  public void setFilesize(int filesize) {
    this.filesize = filesize;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Metadata metadata = (Metadata) o;
    return Objects.equals(this.id, metadata.id) &&
        Objects.equals(this.author, metadata.author) &&
        Objects.equals(this.uploaddate, metadata.uploaddate) &&
        Objects.equals(this.filetype, metadata.filetype)&&
        Objects.equals(this.filesize, metadata.filesize)&&
        Objects.equals(this.tags, metadata.tags) &&
        Objects.equals(this.version, metadata.version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Metadata {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    uploadDate: ").append(toIndentedString(uploaddate)).append("\n");
    sb.append("    fileType: ").append(toIndentedString(filetype)).append("\n");
    sb.append("    fileSize: ").append(toIndentedString(filesize)).append("\n");
    sb.append("    tags: ").append(toIndentedString(tags)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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

