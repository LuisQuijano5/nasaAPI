package comprehensive.project.nasaapi.database;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

public class Connection
{
    private static String baseUrl = "http://localhost:3000/api/";

    public static String sendRequest(String restOfUrl, String requestMethod, String body) throws IOException{
        String requestUrl = baseUrl + restOfUrl;

        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setDoOutput(true); // Allow writing to the output stream
        conn.setRequestProperty("Content-Type", "application/json");
        conn.setRequestMethod(requestMethod);
        OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream());
        writer.write(body);
        writer.flush();
        writer.close();
        conn.connect();

        int responseCode = conn.getResponseCode();

        return getString(conn, responseCode);
    }

    public static String sendGETRequest(String restOfUrl) throws IOException {
        String requestUrl = baseUrl + restOfUrl;

        URL url = new URL(requestUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();
        return getString(conn, responseCode);
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
