package comprehensive.project.nasaapi.database.DAO;

import com.google.gson.*;
import comprehensive.project.nasaapi.database.Connection;
import comprehensive.project.nasaapi.models.User;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class UserDao
{
    private String baseUrl = "user/";
    private final Gson gson = new Gson();
    public AuxDao register(String name, String password, boolean isAdmin) throws IOException {
        String body = String.format("""
                {"name": "%s",
                "isAdmin": %b,
                "password": "%s"}""", name, isAdmin, password);
        String response = Connection.sendRequest(baseUrl, "POST", body, null);

        Response responseObj = gson.fromJson(response, Response.class);
        if (responseObj.success) {
            return new AuxDao(true, responseObj.message, responseObj.userId);
        } else {
            return new AuxDao(false, responseObj.message);
        }
    }

    public AuxDao getUsers(User user) throws IOException {
        String response = Connection.sendGETRequest(baseUrl, user.getToken());

        Response responseObj = gson.fromJson(response, Response.class);
        if (responseObj.success) {
            List<User> users = gson.fromJson(responseObj.data.toString(), new TypeToken<List<User>>(){}.getType());
            AuxDao auxDao = new AuxDao(true);
            auxDao.setUsers(users);
            return auxDao;
        } else {
            return new AuxDao(false, "Error getting all users");
        }
    }

    public AuxDao deleteUser(User user, int id) throws IOException {
        String body = String.format("""
                {"id": "%d"}""", id);
        String response = Connection.sendRequest(baseUrl, "DELETE", body, user.getToken());

        Response responseObj = gson.fromJson(response, Response.class);
        return new AuxDao(responseObj.success, responseObj.message);
    }

    public AuxDao logIn(String name, String password) throws IOException {
        String newBaseUrl = baseUrl + "login/";
        String body = String.format("""
                {"name": "%s",
                "password": "%s"}""", name, password);
        String response = Connection.sendRequest(newBaseUrl, "POST", body, null);

        Response responseObj = gson.fromJson(response, Response.class);
        if (responseObj.success) {
            return new AuxDao(true, responseObj.message, responseObj.data.toString(), responseObj.userId, responseObj.isCondition);
        } else {
            return new AuxDao(false, responseObj.message);
        }
    }

    public AuxDao determineIfAdmin() throws IOException {
        String newBaseUrl = baseUrl + "count";
        String response = Connection.sendGETRequest(newBaseUrl, null);

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
        String response = Connection.sendGETRequest(newBaseUrl, null);

        Response responseObj = gson.fromJson(response, Response.class);
        if (responseObj.success) {
            return new AuxDao(true, responseObj.message);
        } else {
            return new AuxDao(false,  responseObj.message);
        }
    }

    public AuxDao updateUser(User user) throws IOException{
        String body = String.format("""
                {"name": "%s",
                "id": %d,
                "password": "%s"}""", user.getName(), user.getId(), user.getPassword());
        String response = Connection.sendPatch(baseUrl, body, user.getToken());
        if(response == null){
            return new AuxDao(false, "");
        }

        Response responseObj = gson.fromJson(response, Response.class);
        if (responseObj.success) {
            return new AuxDao(true, responseObj.message);
        } else {
            return new AuxDao(false, responseObj.message);
        }
    }
}

