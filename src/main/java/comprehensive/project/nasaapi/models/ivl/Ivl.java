package comprehensive.project.nasaapi.models.ivl;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

//@Generated("jsonschema2pojo")
public class Ivl {

    @SerializedName("collection")
    @Expose
    public Collectionn collection;

    public Collectionn getCollection() {
        return collection;
    }

    public void setCollection(Collectionn collection) {
        this.collection = collection;
    }

}
