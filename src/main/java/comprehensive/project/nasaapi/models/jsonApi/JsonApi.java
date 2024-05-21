
package comprehensive.project.nasaapi.models.jsonApi;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//Generated("jsonschema2pojo")
public class JsonApi {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("message")
    @Expose
    private String message;
    @SerializedName("data")
    @Expose
    private List<DatumApi> data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DatumApi> getData() {
        return data;
    }

    public void setData(List<DatumApi> data) {
        this.data = data;
    }

}
