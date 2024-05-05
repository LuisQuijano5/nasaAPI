package comprehensive.project.nasaapi.database.DAO;

import com.google.gson.*;
import comprehensive.project.nasaapi.database.Connection;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

public class UserDao
{
    private String baseUrl = "user/";
    private final Gson gson = new Gson();
    public AuxDao register(String name, String password, boolean isAdmin) throws IOException {
        String body = String.format("""
                {"name": "%s",
                "isAdmin": %b,
                "password": "%s"}""", name, isAdmin, password);
        String response = Connection.sendRequest(baseUrl, "POST", body);

        Response responseObj = gson.fromJson(response, Response.class);
        if (responseObj.success) {
            return new AuxDao(true, responseObj.message, responseObj.userId);
        } else {
            return new AuxDao(false, responseObj.message);
        }
    }

    public AuxDao logIn(String name, String password) throws IOException {
        String newBaseUrl = baseUrl + "login/";
        String body = String.format("""
                {"name": "%s",
                "password": "%s"}""", name, password);
        String response = Connection.sendRequest(newBaseUrl, "POST", body);

        Response responseObj = gson.fromJson(response, Response.class);
        if (responseObj.success) {
            return new AuxDao(true, responseObj.message, responseObj.data.toString(), responseObj.userId, responseObj.isCondition);
        } else {
            return new AuxDao(false, responseObj.message);
        }
    }

    public AuxDao determineIfAdmin() throws IOException {
        String newBaseUrl = baseUrl + "count";
        String response = Connection.sendGETRequest(newBaseUrl);

        Response responseObj = gson.fromJson(response, Response.class);
        if (responseObj.success) {
            return new AuxDao(true,  responseObj.message, responseObj.isCondition);
        } else {
            return new AuxDao(false,  responseObj.message);
        }
    }

    public AuxDao checkName(String name) throws IOException{
        String encodedUserInput = URLEncoder.encode(name, StandardCharsets.UTF_8);
        String newBaseUrl = baseUrl + "checkName?name=" + encodedUserInput;
        String response = Connection.sendGETRequest(newBaseUrl);

        Response responseObj = gson.fromJson(response, Response.class);
        if (responseObj.success) {
            return new AuxDao(true, responseObj.message);
        } else {
            return new AuxDao(false,  responseObj.message);
        }
    }

}

