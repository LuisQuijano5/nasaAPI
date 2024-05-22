
package comprehensive.project.nasaapi.models.jsonApi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class Modi {

    @SerializedName("userId")
    @Expose
    private Integer userId;
    @SerializedName("timeOfMod")
    @Expose
    private String timeOfMod;
    @SerializedName("viewOfMod")
    @Expose
    private String viewOfMod;
    @SerializedName("action")
    @Expose
    private String action;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTimeOfMod() {
        return timeOfMod;
    }

    public void setTimeOfMod(String timeOfMod) {
        this.timeOfMod = timeOfMod;
    }

    public String getViewOfMod() {
        return viewOfMod;
    }

    public void setViewOfMod(String viewOfMod) {
        this.viewOfMod = viewOfMod;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

}
