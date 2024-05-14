package comprehensive.project.nasaapi.models.ivl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class Link__1 {

    @SerializedName("rel")
    @Expose
    private String rel;
    @SerializedName("prompt")
    @Expose
    private String prompt;
    @SerializedName("href")
    @Expose
    private String href;

    public String getRel() {
        return rel;
    }

    public void setRel(String rel) {
        this.rel = rel;
    }

    public String getPrompt() {
        return prompt;
    }

    public void setPrompt(String prompt) {
        this.prompt = prompt;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

}
