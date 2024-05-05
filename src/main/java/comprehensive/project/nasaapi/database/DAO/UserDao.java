package comprehensive.project.nasaapi.database.DAO;

import comprehensive.project.nasaapi.database.Connection;
import comprehensive.project.nasaapi.models.User;
import com.google.gson.Gson;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class UserDao implements Dao<User>
{
    private String baseUrl = "user/";
    private final Gson gson = new Gson();
    public AuxDao register(String name, String password, boolean isAdmin) throws IOException {
        String body = String.format("""
                {"name": "%s",
                "isAdmin": %b,
                "password": "%s"}""", name, isAdmin, password);
        String response = Connection.sendRequest(baseUrl, "POST", body);

        Map<String, Object> responseMap = gson.fromJson(response, Map.class);
        if ((boolean) responseMap.get("success")) {
            return new AuxDao(true, String.valueOf(responseMap.get("message")));
        } else {
            return new AuxDao(false, String.valueOf(responseMap.get("message")));
        }
    }

    public AuxDao logIn(String name, String password) throws IOException {
        String newBaseUrl = baseUrl + "login/";
        String body = String.format("""
                {"name": "%s",
                "password": "%s"}""", name, password);
        String response = Connection.sendRequest(newBaseUrl, "POST", body);

        Map<String, Object> responseMap = gson.fromJson(response, Map.class);
        if ((boolean) responseMap.get("success")) {
            return new AuxDao(true,  String.valueOf(responseMap.get("message")), (String) responseMap.get("token"));
        } else {
            return new AuxDao(false,  String.valueOf(responseMap.get("message")));
        }
    }

    public AuxDao determineIfAdmin() throws IOException {
        String newBaseUrl = baseUrl + "count";
        String response = Connection.sendGETRequest(newBaseUrl);

        Map<String, Object> responseMap = gson.fromJson(response, Map.class);
        if ((boolean) responseMap.get("success")) {
            return new AuxDao(true,  String.valueOf(responseMap.get("message")), (boolean) responseMap.get("isAdmin"));
        } else {
            return new AuxDao(false,  String.valueOf(responseMap.get("message")));
        }
    }

    public AuxDao checkName(String name) throws IOException{
        String newBaseUrl = baseUrl + "checkName?name=" + name;
        String response = Connection.sendGETRequest(newBaseUrl);

        Map<String, Object> responseMap = gson.fromJson(response, Map.class);
        if ((boolean) responseMap.get("success")) {
            return new AuxDao(true, String.valueOf(responseMap.get("message")));
        } else {
            return new AuxDao(false,  String.valueOf(responseMap.get("message")));
        }
    }

    @Override
    public Optional<User> findById(int id, int br) {
        return Optional.empty();
    }

    @Override
    public List<User> findAll(int br) {
        return null;
    }
}
