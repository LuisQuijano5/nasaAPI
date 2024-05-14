package comprehensive.project.nasaapi.models.ivl;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class Datum {

    @SerializedName("album")
    @Expose
    private List<String> album;
    @SerializedName("center")
    @Expose
    private String center;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("keywords")
    @Expose
    private List<String> keywords;
    @SerializedName("nasa_id")
    @Expose
    private String nasaId;
    @SerializedName("date_created")
    @Expose
    private String dateCreated;
    @SerializedName("media_type")
    @Expose
    private String mediaType;
    @SerializedName("description")
    @Expose
    private String description;

    public List<String> getAlbum() {
        return album;
    }

    public void setAlbum(List<String> album) {
        this.album = album;
    }

    public String getCenter() {
        return center;
    }

    public void setCenter(String center) {
        this.center = center;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<String> keywords) {
        this.keywords = keywords;
    }

    public String getNasaId() {
        return nasaId;
    }

    public void setNasaId(String nasaId) {
        this.nasaId = nasaId;
    }

    public String getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(String dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
