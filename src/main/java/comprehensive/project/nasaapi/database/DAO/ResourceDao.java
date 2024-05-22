package comprehensive.project.nasaapi.database.DAO;

import com.google.gson.Gson;
import comprehensive.project.nasaapi.database.Connection;
import comprehensive.project.nasaapi.models.Resource;
import comprehensive.project.nasaapi.models.User;

import java.io.IOException;

public class ResourceDao {
    private String baseUrl = "resource/";
    private final Gson gson = new Gson();

    public AuxDao addResource(User user, String nasaId, String title, String type, String url, String description) throws IOException {
        String body = String.format("""
                {"nasaId": "%s",
                "title": "%s",
                "type": "%s",
                "url": "%s",
                "description": "%s"}""", nasaId, title, type, url, description);
        String response = Connection.sendRequest(baseUrl, "POST", body, user.getToken());

        Response responseObj = gson.fromJson(response, Response.class);
        return new AuxDao(responseObj.success, responseObj.message);
    }

    public AuxDao getResource(User user, String nasaId) throws IOException {
        String newBaseUrl = "resource?nasaId=" + nasaId;
        String response = Connection.sendGETRequest(newBaseUrl, user.getToken());

        Response responseObj = gson.fromJson(response, Response.class);
        if(responseObj.success){
            Resource resource = gson.fromJson(responseObj.data.toString(), Resource.class);
            return new AuxDao(responseObj.success, resource);
        } else {
            return new AuxDao(responseObj.success, "error getting the resource");
        }
    }
}
