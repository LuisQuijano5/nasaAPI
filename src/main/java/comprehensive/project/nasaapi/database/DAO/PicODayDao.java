package comprehensive.project.nasaapi.database.DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import comprehensive.project.nasaapi.database.Connection;
import comprehensive.project.nasaapi.models.PicODay;
import comprehensive.project.nasaapi.models.User;

import java.io.IOException;
import java.util.List;

public class PicODayDao {
    private String baseUrl = "picoday/";
    private final Gson gson = new Gson();

    public AuxDao addPic(User user, PicODay pic) throws IOException {
        String body = String.format("""
                {"day": %s,
                "title": "%s",
                "url": "%s",
                "credits": "%s"}""", pic.getDay(), pic.getTitle(), pic.getUrl(), pic.getCredits());
        String response = Connection.sendRequest( baseUrl, "POST", body, user.getToken());

        Response responseObj = gson.fromJson(response, Response.class);
        return new AuxDao(responseObj.success, responseObj.message);
    }

    public AuxDao getWeekPics(User user) throws IOException {
        String response = Connection.sendGETRequest(baseUrl, user.getToken());

        Response responseObj = gson.fromJson(response, Response.class);
        if (responseObj.success) {
            List<PicODay> pics = gson.fromJson(responseObj.data.toString(), new TypeToken<List<PicODay>>(){}.getType());
            AuxDao auxDao = new AuxDao(true);
            auxDao.setPics(pics);
            return auxDao;
        } else {
            return new AuxDao(false, "Error getting the week's images");
        }
    }
}
