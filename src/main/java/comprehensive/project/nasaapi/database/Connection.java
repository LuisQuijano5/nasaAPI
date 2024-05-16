package comprehensive.project.nasaapi.database;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import okhttp3.*;

public class Connection
{
    private static String baseUrl = "https://our-brand-420000.uc.r.appspot.com/api/";
    //private static String baseUrl = "http://localhost:3000/api/";

    public static String sendRequest(String restOfUrl, String requestMethod, String body, String token) throws IOException{
        String requestUrl = baseUrl + restOfUrl;

        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true); // Allow writing to the output stream
        conn.setRequestProperty("Content-Type", "application/json");
        if( token != null){
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }
        conn.setRequestMethod(requestMethod);
        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
        writer.write(body);
        writer.flush();
        writer.close();
        conn.connect();

        int responseCode = conn.getResponseCode();

        return getString(conn, responseCode);
    }

    public static String sendGETRequest(String restOfUrl, String token) throws IOException {
        String requestUrl = baseUrl + restOfUrl;

        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        if( token != null){
            conn.setRequestProperty("Authorization", "Bearer " + token);
        }
        conn.connect();

        int responseCode = conn.getResponseCode();
        return getString(conn, responseCode);
    }

    public static String sendPatch(String restOfUrl, String bodyString, String token) {
        String requestUrl = baseUrl + restOfUrl;

        OkHttpClient client = new OkHttpClient();

        MediaType mediaType = MediaType.parse("application/json; charset=utf-8"); // Adjust for your content type if needed

        RequestBody body = RequestBody.create(mediaType, bodyString); // Replace with your data

        Request request = new Request.Builder()
                .url(requestUrl)
                .method("PATCH", body)
                .addHeader("Authorization", "Bearer " + token)
                .build();

        try {
            Response response = client.newCall(request).execute();
            String res = response.body().string();
            System.out.println(res); //debugging purposes
            return res;
        } catch (IOException e) {
           return null;
        }
    }

    private static String getString(HttpURLConnection conn, int responseCode) throws IOException {
        if (responseCode != 200){
            throw new RuntimeException("Error in api connection: " + responseCode);
        }else {
            StringBuilder informationString = new StringBuilder();
            Scanner scanner = new Scanner(conn.getInputStream());
            while (scanner.hasNext()){
                informationString.append(scanner.nextLine());
            }
            scanner.close();

            return informationString.toString();
        }
    }
}
