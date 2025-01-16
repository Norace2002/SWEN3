package paperless.mapper;


import java.util.UUID;

public class DocumentDTO {

    private UUID id;
    private String title;
    private String description;
    private String ocrReadable;

    public UUID getId(){
        return id;
    }

    public void setId(UUID id){
        this.id = id;
    }

    public String getTitle(){
        return title;
    }

    public void setTitle(String title){
        this.title = title;
    }

    public String getDescription(){
        return description;
    }

    public void setDescription(String description){
        this.description = description;
    }

    public String getOcrReadable(){
        return ocrReadable;
    }

    public void setOcrReadable(String ocrReadable){
        this.ocrReadable = ocrReadable;
    }
}
