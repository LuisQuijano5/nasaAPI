package comprehensive.project.nasaapi.apiconnection;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import comprehensive.project.nasaapi.models.ivl.Ivl;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

public class APIConnectionIVL {
    private static String search = "";
    private static String API_URL;
    public static String getApiIVL() throws IOException{
        API_URL = "https://images-api.nasa.gov/search?q=" + search;
        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();

        if (responseCode != 200){
            throw new RuntimeException(" Error connecting to api: " + responseCode);
        }else {
            StringBuilder informationString = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()){
                informationString.append(scanner.nextLine());
            }

            scanner.close();
            return informationString.toString();
        }
    }

    public static Ivl getIVL() throws IOException{
        String jsonData = getApiIVL();

        Gson gson = new Gson();
        Ivl ivl = gson.fromJson(jsonData, new TypeToken<Ivl>(){}.getType());

        return ivl;
    }

    public static String getSearch() {
        return search;
    }

    public static void setSearch(String search) {
        APIConnectionIVL.search = search;
    }
}
