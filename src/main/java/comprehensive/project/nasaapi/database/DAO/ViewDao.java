package comprehensive.project.nasaapi.database.DAO;

import comprehensive.project.nasaapi.database.Connection;
import comprehensive.project.nasaapi.models.View;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import com.google.gson.Gson;

public class ViewDao{
    private String baseUrl = "view/";
    private final Gson gson = new Gson();
    public AuxDao setViewPermits(int userId, String name, int value) throws IOException {
        String body = String.format("""
                {"userId": %d,
                "name": "%s",
                "value": %d}""", userId, name, value);
        String response = Connection.sendRequest(baseUrl, "POST", body);

        Response responseObj = gson.fromJson(response, Response.class);
        return new AuxDao(responseObj.success, responseObj.message);
    }

    public AuxDao getViewsByUserId(int userId) throws IOException {
        String newBaseUrl =  "view?userId=" + userId;
        String response = Connection.sendGETRequest(newBaseUrl);

        Response responseObj = gson.fromJson(response, Response.class);
        if(responseObj.success){
            ViewResponse responseByView = gson.fromJson(responseObj.data.toString(), ViewResponse.class);
            return new AuxDao(true, new int[]{responseByView.apod, responseByView.gallery, responseByView.epic, responseByView.account});
        } else {
            return new AuxDao(false, responseObj.message);
        }
    }

}
