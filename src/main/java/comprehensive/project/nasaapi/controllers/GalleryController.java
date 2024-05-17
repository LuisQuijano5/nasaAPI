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
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.text.Text;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Scanner;

//@../images/joke.jpg

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
    @FXML
    private Image imageAudio;

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
        System.out.println("Holaaaaaaaaaaaaaaaaaaaaaaaa");
        // Limpiar el TilePane antes de agregar nuevos elementos
        tilePane.getChildren().clear();
        //For each space in query add %20
        String query = queryField.getText().replace(" ", "%20");

        Thread loadSearch = new Thread(() -> {
            Platform.runLater(() -> {
                try {
                    APIConnectionIVL.setSearch(query);//Actualiza la busqueda en la pantalla

                    Ivl ivl = APIConnectionIVL.getIVL();
                    Collectionn collection = ivl.getCollection();
                    List<Item> items = collection.getItems();

                    String selectedMediaType = mediaTypeComboBox.getValue(); // Get selected filter
                    if (selectedMediaType == null) {
                        selectedMediaType = "All"; // Default to "All" if nothing selected
                    }

                    for (int i = 0; i < 20 && i < items.size(); i++) {
                        Item item = items.get(i);
                        List<Datum> data = item.getData();

                        if (!data.isEmpty()) {
                            Datum datum = data.get(0);
                            List<Link> links = item.getLinks();

                            // Apply the filter
                            if (selectedMediaType.equals("All") || datum.getMediaType().equals(selectedMediaType.toLowerCase())) {
                                if (datum.getMediaType().equals("video")) {
                                    loadVideo(links,collection, i, item, tilePane);
                                } else if (datum.getMediaType().equals("audio")) {
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

            return jsonArray;
        }
    }

    //Load Image resources
    public static void loadImage(List<Link> links, Item item, TilePane tilePane){
        try {
            String imageUrl = links.get(0).getHref();

            String description = item.getData().get(0).getDescription();



            WebView webView = new WebView();
            webView.getEngine().load(imageUrl);
            webView.setPrefSize(300, 221);

            Label mediaLabel = new Label("Image");
            mediaLabel.maxWidth(400);

            Label titleLabel = new Label(item.getData().get(0).getTitle());
            titleLabel.maxWidth(400);

            // Crear VBox para contener WebView y Label
            VBox resultBox = new VBox();
            resultBox.getChildren().addAll(mediaLabel,webView, titleLabel);
            tilePane.getChildren().add(resultBox);

            String finalUrl = imageUrl;
            webView.setOnMouseClicked(event -> {
                if (finalUrl != null){
                    try {
                        openVideoInNewWindow(finalUrl, description);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            Label titleLabel = new Label("It was impossible to load this resourse..."+
                    "\n\ne");

            VBox resultBox = new VBox();
            resultBox.getChildren().addAll(titleLabel);
            tilePane.getChildren().add(resultBox);
        }
    }

    //Load Audio resources
    private void loadAudio(Collectionn collection, int i,
                                Item item, TilePane tilePane) throws IOException {

        String audioUrl = null;
        try {
            String[] jsonLinks = linksExtractor(collection.getItems().get(i).getHref());

            ImageView test = new ImageView(imageAudio);

            for (String link : jsonLinks){
                if (link.endsWith(".mp3")){
                    //webView.getEngine().load(link);

                    audioUrl = link;
                    break;
                }
            }

            String description = item.getData().get(0).getDescription();

            Label mediaLabel = new Label("Audio");
            mediaLabel.maxWidth(400);

            Label titleLabel = new Label(item.getData().get(0).getTitle());
            titleLabel.maxWidth(400);

            VBox resultBox = new VBox();
            resultBox.getChildren().addAll(mediaLabel,test, titleLabel);
            tilePane.getChildren().add(resultBox);

            String finalUrl = audioUrl;
            test.setOnMouseClicked(event -> {
                if (finalUrl != null){
                    try {
                        openVideoInNewWindow(finalUrl, description);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            System.out.println(e);
            Label titleLabel = new Label("It was impossible to load this resourse..."+
                    "\n\ne");

            VBox resultBox = new VBox();
            resultBox.getChildren().addAll(titleLabel);
            tilePane.getChildren().add(resultBox);
        }
    }

    //Load Video Resources
    private static void loadVideo(List<Link> links,Collectionn collection, int i,
                                  Item item, TilePane tilePane) throws IOException {
        try {
            String videoUrl = null;
                    
            String[] jsonLinks = linksExtractor(collection.getItems().get(i).getHref());

            WebView webView = new WebView();
            webView.getEngine().load(links.get(0).getHref());

            for (String link : jsonLinks){
                if (link.endsWith(".mp4")){
                    //webView.getEngine().load(link);

                    videoUrl = link;
                    break;
                }
            }

            String description = item.getData().get(0).getDescription();

            webView.setPrefSize(300, 221);

            Label mediaLabel = new Label("Video");
            mediaLabel.maxWidth(400);

            Label titleLabel = new Label(item.getData().get(0).getTitle());
            titleLabel.maxWidth(400);

            VBox resultBox = new VBox();
            resultBox.getChildren().addAll(mediaLabel,webView, titleLabel);
            tilePane.getChildren().add(resultBox);

            String finalUrl = videoUrl;
            // Load video by clicking on the webView
            webView.setOnMouseClicked(event -> {
                if (finalUrl != null){
                    try {
                        openVideoInNewWindow(finalUrl, description);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }catch (Exception e){
            Label titleLabel = new Label("It was impossible to load this resourse..."+
                    "\n\n"+e);

            VBox resultBox = new VBox();
            resultBox.getChildren().addAll(titleLabel);
            tilePane.getChildren().add(resultBox);
        }

    }

    // Method to open a video in a new modal window
    private static void openVideoInNewWindow(String videoUrl, String description) throws IOException {
        Platform.runLater(() -> {
            try {
                Stage newWindow = new Stage();
                newWindow.initModality(Modality.APPLICATION_MODAL);
                newWindow.setTitle("Video Player");

                WebView newWebView = new WebView();
                newWebView.getEngine().load(videoUrl);
                newWebView.setPrefSize(400, 400);

                Text descriptionText = new Text();
                descriptionText.setWrappingWidth(600);
                descriptionText.setText(description);

                VBox layout = new VBox(newWebView, descriptionText);
                layout.setAlignment(Pos.CENTER);

                ScrollPane scrollPane = new ScrollPane(layout);
                scrollPane.fitToHeightProperty();
                scrollPane.fitToWidthProperty();

                Scene scene = new Scene(scrollPane, 800, 600);
                newWindow.setScene(scene);
                newWindow.show();

                // Stop video when the window is closed
                newWindow.setOnCloseRequest(event -> {
                    newWebView.getEngine().load(null);
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }
}
