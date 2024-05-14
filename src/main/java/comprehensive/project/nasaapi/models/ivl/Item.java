package comprehensive.project.nasaapi.models.ivl;

import comprehensive.project.nasaapi.models.ivl.Link;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//@Generated("jsonschema2pojo")
public class Item {

    @SerializedName("href")
    @Expose
    private String href;
    @SerializedName("data")
    @Expose
    private List<Datum> data;
    @SerializedName("links")
    @Expose
    private List<Link> links;

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public List<Datum> getData() {
        return data;
    }

    public void setData(List<Datum> data) {
        this.data = data;
    }

    public List<Link> getLinks() {
        return links;
    }

    public void setLinks(List<Link> links) {
        this.links = links;
    }

}
