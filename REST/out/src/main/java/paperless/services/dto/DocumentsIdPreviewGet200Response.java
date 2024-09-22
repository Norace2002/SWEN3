package paperless.services.dto;

import java.net.URI;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonTypeName;
import org.openapitools.jackson.nullable.JsonNullable;
import java.time.OffsetDateTime;
import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import io.swagger.v3.oas.annotations.media.Schema;


import java.util.*;
import jakarta.annotation.Generated;

/**
 * DocumentsIdPreviewGet200Response
 */

@JsonTypeName("_documents__id__preview_get_200_response")
@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2024-09-22T12:43:13.854462136Z[Etc/UTC]", comments = "Generator version: 7.9.0-SNAPSHOT")
public class DocumentsIdPreviewGet200Response {

  private String previewUrl;

  public DocumentsIdPreviewGet200Response previewUrl(String previewUrl) {
    this.previewUrl = previewUrl;
    return this;
  }

  /**
   * Get previewUrl
   * @return previewUrl
   */
  
  @Schema(name = "previewUrl", requiredMode = Schema.RequiredMode.NOT_REQUIRED)
  @JsonProperty("previewUrl")
  public String getPreviewUrl() {
    return previewUrl;
  }

  public void setPreviewUrl(String previewUrl) {
    this.previewUrl = previewUrl;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DocumentsIdPreviewGet200Response documentsIdPreviewGet200Response = (DocumentsIdPreviewGet200Response) o;
    return Objects.equals(this.previewUrl, documentsIdPreviewGet200Response.previewUrl);
  }

  @Override
  public int hashCode() {
    return Objects.hash(previewUrl);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DocumentsIdPreviewGet200Response {\n");
    sb.append("    previewUrl: ").append(toIndentedString(previewUrl)).append("\n");
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

