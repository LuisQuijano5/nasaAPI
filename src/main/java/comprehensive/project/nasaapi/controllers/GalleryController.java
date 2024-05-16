package comprehensive.project.nasaapi.controllers;

import com.google.gson.Gson;
import comprehensive.project.nasaapi.App;
import comprehensive.project.nasaapi.apiconnection.APIConnectionIVL;
import comprehensive.project.nasaapi.models.ivl.*;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;


public class GalleryController {
    @FXML
    BorderPane container;
    @FXML
    FontIcon closedEye;
    @FXML
    FontIcon openEye;
    private boolean menuVisibility = true;
    @FXML
    private TextField queryField;
    @FXML
    private ComboBox<String> mediaTypeComboBox;

    @FXML
    private TilePane tilePane;

    public void initialize(){
        if(!App.darkTheme){ App.themeHandler.applyLightTheme(container); }
        if(App.currentUser.getMenuVisibilityPref() == 0){
            menuVisibility = App.menuSwitch.switchMenu(menuVisibility, openEye, closedEye);
        }
    }

    @FXML
    private void changeMenuVisibility() {
        menuVisibility = App.menuSwitch.switchMenu(menuVisibility, openEye, closedEye);
    }

    @FXML
    private void onPerformSearchButtonClick(){
        // Limpiar el TilePane antes de agregar nuevos elementos
        tilePane.getChildren().clear();
        //For each space in query add %20
        String query = queryField.getText().replace(" ", "%20");

        Thread loadSearch = new Thread(() ->{
            Platform.runLater(() -> {
                try {
                    APIConnectionIVL.setSearch(query);//Actualiza la busqueda en la pantalla

                    Ivl ivl = APIConnectionIVL.getIVL();
                    Collectionn collection = ivl.getCollection();
                    List<Item> items = collection.getItems();


                    for (int i = 0; i < 30 && i < items.size(); i++) {
                        Item item = items.get(i);
                        List<Datum> data = item.getData();

                        if (!data.isEmpty()) {
                            Datum datum = data.get(0); // Asumiendo que solo hay un Datum por Item

                            List<Link> links = item.getLinks();
                            if (true) { //Load MultiMedia resouces
                                //Can be used as inspiration for the filter

                                if (datum.getMediaType().equals("video")){

                                    loadVideo(collection, i, item, tilePane);

                                } else if (datum.getMediaType().equals("audio")){

                                    loadAudio(collection, i, item, tilePane);

                                } else if (datum.getMediaType().equals("image")) {

                                    loadImage(links, item, tilePane);
                                }

                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            });
        });
        loadSearch.start();
    }

    //Read the link.json for each item to load MultiMedia
    public static String[] linksExtractor(String href) throws IOException {
        URL url = new URL(href);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");
        conn.connect();

        int responseCode = conn.getResponseCode();

        if (responseCode != 200){
            throw new RuntimeException("Error connecting to link.son: " + responseCode);

        }else {
            StringBuilder informationString = new StringBuilder();
            Scanner scanner = new Scanner(url.openStream());

            while (scanner.hasNext()){
                informationString.append(scanner.nextLine());
            }

            scanner.close();

            String jsonData = informationString.toString();

            Gson gson = new Gson();

            String[] jsonArray = gson.fromJson(jsonData, String[].class);
            System.out.println("Holaa: "+jsonArray[0]);
            return jsonArray;
        }
    }

    //Load Image resources
    public static void loadImage(List<Link> links, Item item, TilePane tilePane){
        try {
            WebView webView = new WebView();
            webView.getEngine().load(links.get(0).getHref());
            webView.setMaxWidth(400);
            webView.setMaxHeight(250);

            Label titleLabel = new Label(item.getData().get(0).getTitle());
            titleLabel.maxWidth(400);

            // Crear VBox para contener WebView y Label
            VBox resultBox = new VBox();
            resultBox.getChildren().addAll(webView, titleLabel);
            tilePane.getChildren().add(resultBox);
        }catch (Exception e){
            Label titleLabel = new Label("It was impossible to load this resourse..."+
                    "\n\ne");

            VBox resultBox = new VBox();
            resultBox.getChildren().addAll(titleLabel);
            tilePane.getChildren().add(resultBox);
        }
    }

    //Load Audio resources
    private static void loadAudio(Collectionn collection, int i,
                                Item item, TilePane tilePane) throws IOException {

        try {
            String[] jsonLinks = linksExtractor(collection.getItems().get(i).getHref());
            //System.out.println("Holaa: "+jsonLinks[0]);

            WebView webView = new WebView();

            for (String link : jsonLinks){
                if (link.endsWith(".mp3")){
                    webView.getEngine().load(link);

                    break;
                } else if (link.endsWith(".jpg")) {
                    webView.getEngine().load(link);

                    break;
                }
            }

            webView.setPrefSize(400, 250);
            webView.setMaxSize(400, 50);
            webView.setMinSize(400, 50);

            Label titleLabel = new Label(item.getData().get(0).getTitle());
            titleLabel.maxWidth(400);

            VBox resultBox = new VBox();
            resultBox.getChildren().addAll(webView, titleLabel);
            tilePane.getChildren().add(resultBox);
        }catch (Exception e){
            Label titleLabel = new Label("It was impossible to load this resourse..."+
                    "\n\ne");

            VBox resultBox = new VBox();
            resultBox.getChildren().addAll(titleLabel);
            tilePane.getChildren().add(resultBox);
        }
    }

    //Load Video Resources
    private static void loadVideo(Collectionn collection, int i,
                                  Item item, TilePane tilePane) throws IOException {
        try {
            String[] jsonLinks = linksExtractor(collection.getItems().get(i).getHref());

            WebView webView = new WebView();

            for (String link : jsonLinks){
                if (link.endsWith(".mp4")){
                    webView.getEngine().load(link);

                    //System.out.println(link);
                    break;
                }
            }


            webView.setPrefSize(400, 250);
            webView.setMaxSize(400, 250);
            webView.setMinSize(400, 250);

            Label titleLabel = new Label(item.getData().get(0).getTitle());
            titleLabel.maxWidth(400);

            VBox resultBox = new VBox();
            resultBox.getChildren().addAll(webView, titleLabel);
            tilePane.getChildren().add(resultBox);
        }catch (Exception e){
            Label titleLabel = new Label("It was impossible to load this resourse..."+
                    "\n\n"+e);

            VBox resultBox = new VBox();
            resultBox.getChildren().addAll(titleLabel);
            tilePane.getChildren().add(resultBox);
        }

    }
}
