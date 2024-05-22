package comprehensive.project.nasaapi.database.DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import comprehensive.project.nasaapi.database.Connection;
import comprehensive.project.nasaapi.models.PicODay;
import comprehensive.project.nasaapi.models.User;
import comprehensive.project.nasaapi.models.jsonApi.DatumApi;
import comprehensive.project.nasaapi.models.jsonApi.JsonApi;

import java.io.FilterOutputStream;
import java.io.IOException;
import java.util.List;

public class PicODayDao {
    private String baseUrl = "picoday/";
    private final Gson gson = new Gson();

    public AuxDao addPic(User user, PicODay pic) throws IOException {
        String body = String.format("""
                {"day": "%s",
                "title": "%s",
                "url": "%s",
                "credits": "%s"}""", pic.getDay(), pic.getTitle(), pic.getUrl(), pic.getCredits());

        String response = Connection.sendRequest( baseUrl, "POST", body, user.getToken());

        Response responseObj = gson.fromJson(response, Response.class);
        return new AuxDao(responseObj.success, responseObj.message);
    }

    public List<DatumApi> getWeeksPics(User user) throws IOException {
        String response = Connection.sendGETRequest(baseUrl, user.getToken());

        JsonApi responseObj = gson.fromJson(response, JsonApi.class);

        if (responseObj.getSuccess()) {
            List<DatumApi> pics = responseObj.getData();
            return pics;
        } else {
            return null;
        }
    }
}
