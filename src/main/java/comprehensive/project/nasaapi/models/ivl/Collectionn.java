package comprehensive.project.nasaapi.models.ivl;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class Collectionn {

    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("items")
    @Expose
    private List<Item> items;
    @SerializedName("metadata")
    @Expose
    private Metadata metadata;
    @SerializedName("links")
    @Expose
    private List<Link__1> links;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    public List<Link__1> getLinks() {
        return links;
    }

    public void setLinks(List<Link__1> links) {
        this.links = links;
    }

}
