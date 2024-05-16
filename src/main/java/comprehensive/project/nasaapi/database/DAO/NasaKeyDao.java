package comprehensive.project.nasaapi.database.DAO;

import com.google.gson.Gson;
import comprehensive.project.nasaapi.database.Connection;

import java.io.IOException;

public class NasaKeyDao {
    private String baseUrl = "nasaapikey/";
    private final Gson gson = new Gson();

    public AuxDao updateKey(String key) {
        String newBaseUrl = "nasaapikey?key=" + key;
        String response = Connection.sendPatch(newBaseUrl, null, null);

        Response responseObj = gson.fromJson(response, Response.class);
        return new AuxDao(responseObj.success, responseObj.message);
    }

    public AuxDao getKey(String key) throws IOException {
        String response = Connection.sendGETRequest(baseUrl, null);

        Response responseObj = gson.fromJson(response, Response.class);
        if(responseObj.success){
            return new AuxDao(responseObj.success, responseObj.data.toString());
        } else {
            return new AuxDao(responseObj.success, "error getting key");
        }
    }
}
