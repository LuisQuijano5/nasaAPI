package comprehensive.project.nasaapi.apiconnection;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import comprehensive.project.nasaapi.models.APOD;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;


public class APIConnectionAPOD {

    private static String customDate = "";
    private static String API_KEY = "DOrdIrK4IrT10Nce6AeUQjvWafJlmCa87cOWxnyB";//DEMO_KEY
    private static String API_URL;

    private static String getApiData() throws IOException{
        API_URL = "https://api.nasa.gov/planetary/apod?api_key=" + API_KEY + "&date=" + customDate;

        URL url = new URL(API_URL);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();

        if (responseCode != 200){
            throw new RuntimeException("Error al conectar con la API: " + responseCode);
        }else {
            StringBuilder informationString = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());//escanea flujo de datos

            while (scanner.hasNext()){
                informationString.append(scanner.nextLine());
            }

            scanner.close();

            return informationString.toString();
        }
    }

    public static APOD getApod() throws IOException{
        String jsonData = getApiData();

        APOD apodObject = new Gson().fromJson(jsonData, APOD.class);

        return apodObject;
    }

    public static String getApiKey() {
        return API_KEY;
    }

    public static void setApiKey(String apiKey) {
        API_KEY = apiKey;
    }

    public static String getCustomDate() {
        return customDate;
    }

    public static void setCustomDate(String customDate) {
        APIConnectionAPOD.customDate = customDate;
    }
}