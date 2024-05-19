package comprehensive.project.nasaapi.apiconnection;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import comprehensive.project.nasaapi.App;
import comprehensive.project.nasaapi.database.DAO.AuxDao;
import comprehensive.project.nasaapi.database.DAO.NasaKeyDao;
import comprehensive.project.nasaapi.models.APOD;
import comprehensive.project.nasaapi.models.User;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;


public class APIConnectionAPOD {

    private static String customDate = "";

    private static NasaKeyDao nasaKeyDao = new NasaKeyDao();
    private static AuxDao auxDao;


    //private static String API_KEY = "DEMO_KEY";//DEMO_KEY /DOrdIrK4IrT10Nce6AeUQjvWafJlmCa87cOWxnyB
    private static String API_URL;

    private static String prueba;


    private static String getApiData() throws IOException{

        API_URL = "https://api.nasa.gov/planetary/apod?api_key=" + getApiKey() + "&date=" + customDate;

        System.out.println("prueba: " + auxDao.getData());

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
    private static String getApiData(String startDate, String endDate) throws IOException{

        API_URL = "https://api.nasa.gov/planetary/apod?api_key=" + getApiKey() + "&start_date=" + startDate + "&end_date=" + endDate;

        System.out.println("prueba: " + auxDao.getData());

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

    public static List<APOD> getApodsInRange(String startDate, String endDate) throws IOException {
        String jsonData = getApiData(startDate, endDate);
        Type listType = new TypeToken<List<APOD>>() {}.getType();
        return new Gson().fromJson(jsonData, listType);
    }

    public static String getApiKey() {
            try {
                auxDao = nasaKeyDao.getWeekPics();
            } catch (IOException e) {
                auxDao.getMessage();
            }
        return auxDao.getData();
    }

    public static void setConApiKey(User user, String apiKey) {
        nasaKeyDao.updateKey(user, apiKey);
    }


    public static String getCustomDate() {
        return customDate;
    }

    public static void setCustomDate(String customDate) {
        APIConnectionAPOD.customDate = customDate;
    }
}