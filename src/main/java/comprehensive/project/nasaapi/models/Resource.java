package comprehensive.project.nasaapi.models;

public class Resource {
    private String nasaId;
    private String title;
    private String type;
    private String url;
    private String description;

    public Resource(String nasaId, String title, String type, String url, String description) {
        this.nasaId = nasaId;
        this.title = title;
        this.type = type;
        this.url = url;
        this.description = description;
    }

    public String getNasaId() {
        return nasaId;
    }

    public void setNasaId(String nasaId) {
        this.nasaId = nasaId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
