package comprehensive.project.nasaapi.controllers;

import com.google.gson.Gson;
import comprehensive.project.nasaapi.App;
import comprehensive.project.nasaapi.apiconnection.APIConnectionIVL;
import comprehensive.project.nasaapi.models.ivl.*;
import javafx.concurrent.Task;
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
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.List;

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
        String query = queryField.getText();
        try {
            APIConnectionIVL.setSearch(query);//Actualiza la busqueda en la pantalla

            Ivl ivl = APIConnectionIVL.getIVL();
            Collectionn collection = ivl.getCollection();
            List<Item> items = collection.getItems();


            for (int i = 0; i < 5 && i < items.size(); i++) {
                Item item = items.get(i);
                List<Datum> data = item.getData();

                if (!data.isEmpty()) {
                    Datum datum = data.get(0); // Asumiendo que solo hay un Datum por Item

                    // Obtener la URL de la imagen o video
                    List<Link> links = item.getLinks();
                    if (!links.isEmpty()) {
                        String url = links.get(0).getHref();

                        // Crear WebView y Label para este resultado
                        WebView webView = new WebView();
                        webView.getEngine().load(url);
                        webView.setMaxWidth(400);
                        webView.setMaxHeight(250);

                        Label titleLabel = new Label(item.getData().get(0).getTitle());

                        // Crear VBox para contener WebView y Label
                        VBox resultBox = new VBox();
                        resultBox.getChildren().addAll(webView, titleLabel);
                        tilePane.getChildren().add(resultBox);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}




