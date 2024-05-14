package comprehensive.project.nasaapi.models.ivl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class Metadata {

    @SerializedName("total_hits")
    @Expose
    private Integer totalHits;

    public Integer getTotalHits() {
        return totalHits;
    }

    public void setTotalHits(Integer totalHits) {
        this.totalHits = totalHits;
    }

}
