package comprehensive.project.nasaapi.database.DAO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import comprehensive.project.nasaapi.database.Connection;
import comprehensive.project.nasaapi.models.Favorites;
import comprehensive.project.nasaapi.models.Modification;
import comprehensive.project.nasaapi.models.User;

import java.io.IOException;
import java.util.List;

public class FavoritesDao {
    private String baseUrl = "favorites/";
    private final Gson gson = new Gson();

    public AuxDao getFavs(User user) throws IOException {
        String newBaseUrl = "favorites?userId=" + user.getId();
        String response = Connection.sendGETRequest(newBaseUrl, user.getToken());

        Response responseObj = gson.fromJson(response, Response.class);
        if (responseObj.success) {
            List<Favorites> favs = gson.fromJson(responseObj.data.toString(), new TypeToken<List<Favorites>>(){}.getType());
            AuxDao auxDao = new AuxDao(true);
            auxDao.setFavs(favs);
            return auxDao;
        } else {
            return new AuxDao(false, responseObj.message);
        }
    }

    public AuxDao addToFavorite(User user, Favorites favs) throws IOException {
        String body = String.format("""
                {"userId": %d,
                "resourcesId": "%s",
                "note": "%s"}""", favs.getUserId(), favs.getResourcesId(), favs.getNote());
        String response = Connection.sendRequest( baseUrl, "POST", body, user.getToken());

        Response responseObj = gson.fromJson(response, Response.class);
        return new AuxDao(responseObj.success, responseObj.message);
    }

    public AuxDao deleteFromFavorite(User user, Favorites fav) throws IOException {
        String body = String.format("""
                {"userId": %d,
                "resourcesId": "%s"}""", fav.getUserId(), fav.getResourcesId());
        String response = Connection.sendRequest(baseUrl, "DELETE", body, user.getToken());

        Response responseObj = gson.fromJson(response, Response.class);
        return new AuxDao(responseObj.success, responseObj.message);
    }
}
