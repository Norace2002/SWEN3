package paperless.mapper;


public class DocumentDTO {

    private String id;
    private String title;
    private String description;

    private String fileUrl;

    public String getId(){
        return id;
    }

    public void setId(String id){
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

    public String getFileUrl(){
        return fileUrl;
    }

    public void setFileUrl(String fileUrl){
        this.fileUrl = fileUrl;
    }
}
