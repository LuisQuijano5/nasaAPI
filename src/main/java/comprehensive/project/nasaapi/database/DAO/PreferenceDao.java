package comprehensive.project.nasaapi.database.DAO;

import com.google.gson.Gson;
import comprehensive.project.nasaapi.database.Connection;

import java.io.IOException;

public class PreferenceDao
{
    private String baseUrl = "preference/";
    private final Gson gson = new Gson();
    public AuxDao setPreference(int userId, String name, int value) throws IOException {
        String body = String.format("""
                {"userId": %d,
                "name": "%s",
                "value": %d}""", userId, name, value);
        String response = Connection.sendRequest(baseUrl, "POST", body);

        Response responseObj = gson.fromJson(response, Response.class);
        return new AuxDao(responseObj.success, responseObj.message);
    }
    public AuxDao getPreferenceByUserId(int userId) throws IOException {
        String newBaseUrl =  "preference?userId=" + userId;
        String response = Connection.sendGETRequest(newBaseUrl);

        Response responseObj = gson.fromJson(response, Response.class);
        if(responseObj.success){
            PreferenceResponse responseByView = gson.fromJson(responseObj.data.toString(), PreferenceResponse.class);
            return new AuxDao(true, new int[]{responseByView.darkMode, responseByView.menuVisibility});
        } else {
            return new AuxDao(false, responseObj.message);
        }
    }

}
