package comprehensive.project.nasaapi.database.DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import comprehensive.project.nasaapi.database.Connection;
import comprehensive.project.nasaapi.models.Modification;
import comprehensive.project.nasaapi.models.User;
import comprehensive.project.nasaapi.models.jsonApi.DatumApi;
import comprehensive.project.nasaapi.models.jsonApi.Example;
import comprehensive.project.nasaapi.models.jsonApi.Modi;

import java.io.IOException;
import java.util.List;

public class ModificationDao {
    private String baseUrl = "modification/";
    private final Gson gson = new Gson();

    public AuxDao registerModification(User user, Modification mod) throws IOException {
        String body = String.format("""
                {"userId": %d,
                "timeOfMod": "%s",
                "viewOfMod": "%s",
                "action": "%s"}""", mod.getUserId(), mod.getTimeOfMod(), mod.getViewOfMod(), mod.getAction());
        String response = Connection.sendRequest(baseUrl, "POST", body, user.getToken());

        Response responseObj = gson.fromJson(response, Response.class);
        return new AuxDao(responseObj.success, responseObj.message);
    }

    public List<Modi> getModifications(User user) throws IOException {
        String response = Connection.sendGETRequest(baseUrl, user.getToken());

        Example responseObj = gson.fromJson(response, Example.class);
        if (responseObj.getSuccess()) {
            List<Modi> modi = responseObj.getData();

            return modi;
        } else {
            return null;
        }
    }

    public AuxDao deleteModifications(User user) throws IOException {
        String body = "";
        String response = Connection.sendRequest(baseUrl, "DELETE", body, user.getToken());

        Response responseObj = gson.fromJson(response, Response.class);
        return new AuxDao(responseObj.success, responseObj.message);
    }

}
