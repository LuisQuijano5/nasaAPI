package comprehensive.project.nasaapi;

import comprehensive.project.nasaapi.services.*;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    //important components of the app class
    private static Scene scene;
    private static final BorderPane root = new BorderPane(); // app container

    // Creating some of the views
    private static Parent welcomeView; // menu view
    private static Parent menuView;

    //services no DI cuz of nature of the app class

    public final static IshowMessage showMessage = new ShowMessage();
    private final static IloadFXML loader = new LoadFXML();
    public final static ImenuSwitch menuSwitch = new MenuSwitch();
    public static ScreenSize screenService;

    public static void main(String[] args) {
        launch(App.class, args);
    }

    @Override
    public void start(Stage stage) throws IOException {
        // Loading welcome and menu
        welcomeView = loader.loadFXML("views/welcome-view.fxml");
        menuView = loader.loadFXML("views/menu-view.fxml");

        // setting stage
        root.setCenter(welcomeView);
        root.setStyle("-fx-font-size: 24px"); // using rem
        scene = new Scene(root);
        stage.setTitle("FinalProject");
        stage.setScene(scene);

        //screen sizing
        ScreenSize screenService = new ScreenSize(scene, stage);
        screenService.fullScreen();

        stage.show();
    }

    public static void changeView(String parent) {
        System.out.println(String.format("views/%s-view.fxml", parent));
        Parent view = loader.loadFXML( String.format("views/%s-view.fxml", parent) );
        if (view == null){
            showMessage.alert(Alert.AlertType.ERROR, "ERROR LOADING VIEW",
                    parent + " couldn't be loaded", "Please check the file or path");
        }
        root.setCenter(view);
    }

    public static void setWelcomeView() {
        hideMenu();
        root.setCenter(welcomeView);
    }

    public static void showMenu() { root.setLeft(menuView); }

    public static void hideMenu() { root.setLeft(null); }
}