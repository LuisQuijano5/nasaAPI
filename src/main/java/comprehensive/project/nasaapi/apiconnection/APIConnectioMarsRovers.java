package comprehensive.project.nasaapi.apiconnection;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class APIConnectioMarsRovers {
    private static final String API_KEY = "UZEaWudlMUdLbdtgJOr3inBq5HiTDh0q1MFvmsNy";
    private static final String API_URL = "https://api.nasa.gov/mars-photos/api/v1/rovers/";

    public static JsonArray getPhotosByEarthDate(String rover, String earthDate) throws IOException {
        String url = API_URL + rover + "/photos?earth_date=" + earthDate + "&api_key=" + API_KEY;
        System.out.println(url);
        return getJsonArray(url);
    }

    private static JsonArray getJsonArray(String url) throws IOException {
        URL apiUrl = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) apiUrl.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        if (responseCode != 200) {
            throw new RuntimeException("Error al conectar con la API: " + responseCode);
        } else {
            StringBuilder informationString = new StringBuilder();
            Scanner scanner = new Scanner(apiUrl.openStream());

            while (scanner.hasNext()) {
                informationString.append(scanner.nextLine());
            }

            scanner.close();

            String jsonString = informationString.toString();
            JsonObject jsonObject = new Gson().fromJson(jsonString, JsonObject.class);
            return jsonObject.getAsJsonArray("photos");
        }
    }
}
