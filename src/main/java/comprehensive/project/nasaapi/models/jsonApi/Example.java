
package comprehensive.project.nasaapi.models.jsonApi;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//("jsonschema2pojo")
public class Example {

    @SerializedName("success")
    @Expose
    private Boolean success;
    @SerializedName("data")
    @Expose
    private List<Modi> data;

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public List<Modi> getData() {
        return data;
    }

    public void setData(List<Modi> data) {
        this.data = data;
    }

}
