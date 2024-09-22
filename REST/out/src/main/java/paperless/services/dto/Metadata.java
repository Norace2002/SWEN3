package paperless.services.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * Metadata
 */

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-09-22T12:43:13.854462136Z[Etc/UTC]", comments = "Generator version: 7.9.0-SNAPSHOT")
public class Metadata {

  private String id;

  private String title;

  private String author;

  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
  private OffsetDateTime uploadDate;

  @Valid
  private List<String> tags = new ArrayList<>();

  private String version;

  public Metadata id(String id) {
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

  public Metadata title(String title) {
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

  public Metadata author(String author) {
    this.author = author;
    return this;
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

  public Metadata uploadDate(OffsetDateTime uploadDate) {
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

  public Metadata tags(List<String> tags) {
    this.tags = tags;
    return this;
  }

  public Metadata addTagsItem(String tagsItem) {
    if (this.tags == null) {
      this.tags = new ArrayList<>();
    }
    this.tags.add(tagsItem);
    return this;
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

  public Metadata version(String version) {
    this.version = version;
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
        Objects.equals(this.title, metadata.title) &&
        Objects.equals(this.author, metadata.author) &&
        Objects.equals(this.uploadDate, metadata.uploadDate) &&
        Objects.equals(this.tags, metadata.tags) &&
        Objects.equals(this.version, metadata.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, title, author, uploadDate, tags, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Metadata {\n");
    sb.append("    id: ").append(toIndentedString(id)).append("\n");
    sb.append("    title: ").append(toIndentedString(title)).append("\n");
    sb.append("    author: ").append(toIndentedString(author)).append("\n");
    sb.append("    uploadDate: ").append(toIndentedString(uploadDate)).append("\n");
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

