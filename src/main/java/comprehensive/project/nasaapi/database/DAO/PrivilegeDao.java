package comprehensive.project.nasaapi.database.DAO;

import com.google.gson.Gson;
import comprehensive.project.nasaapi.database.Connection;
import comprehensive.project.nasaapi.models.User;

import java.io.IOException;

public class PrivilegeDao
{
    private String baseUrl = "privilege/";
    private final Gson gson = new Gson();
    public AuxDao setPrivilege(int userId, String name, int value) throws IOException {
        String body = String.format("""
                {"userId": %d,
                "name": "%s",
                "value": %d}""", userId, name, value);
        String response = Connection.sendRequest(baseUrl, "POST", body, null);

        Response responseObj = gson.fromJson(response, Response.class);
        return new AuxDao(responseObj.success, responseObj.message);
    }

    public AuxDao getPrivilegeByUserId(int userId) throws IOException {
        String newBaseUrl =  "privilege?userId=" + userId;
        String response = Connection.sendGETRequest(newBaseUrl, null);

        Response responseObj = gson.fromJson(response, Response.class);
        if(responseObj.success){
            ViewResponse responseByView = gson.fromJson(responseObj.data.toString(), ViewResponse.class);
            return new AuxDao(true, new int[]{responseByView.apod, responseByView.gallery, responseByView.epic, responseByView.account});
        } else {
            return new AuxDao(false, responseObj.message);
        }
    }

    public AuxDao updatePrivilege(User user, int userId, int value, String name) {
        String body = String.format("""
                {"userId": %d,
                "name": "%s",
                "value": %d}""", userId, name, value);
        String response = Connection.sendPatch(baseUrl, body, user.getToken());

        Response responseObj = gson.fromJson(response, Response.class);
        if(responseObj == null){
            return new AuxDao(false, "Error updating preference");
        } else {
            return new AuxDao(responseObj.success, responseObj.message);
        }
    }
}
