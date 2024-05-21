package comprehensive.project.nasaapi.database.DAO;

import com.google.gson.Gson;
import comprehensive.project.nasaapi.database.Connection;
import comprehensive.project.nasaapi.models.User;

import java.io.IOException;

public class NasaKeyDao {
    private String baseUrl = "nasaapikey/";
    private final Gson gson = new Gson();

    public AuxDao updateKey(User user, String key) {

        String body = String.format("""
                {"key": "%s"}""", key);
        String response = Connection.sendPatch(baseUrl, body, user.getToken());

        Response responseObj = gson.fromJson(response, Response.class);
        return new AuxDao(responseObj.success, responseObj.message);
    }


    public AuxDao getKey() throws IOException {
        String response = Connection.sendGETRequest(baseUrl, null);

        Response responseObj = gson.fromJson(response, Response.class);
        if(responseObj.success){
            return new AuxDao(true, "SUCCESS", responseObj.data.toString());
        } else {
            return new AuxDao(false, "error getting key");
        }
    }
}
