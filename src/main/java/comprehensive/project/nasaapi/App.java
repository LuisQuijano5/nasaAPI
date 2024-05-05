package comprehensive.project.nasaapi;

import comprehensive.project.nasaapi.models.User;
import comprehensive.project.nasaapi.services.*;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicBoolean;

public class App extends Application {
    //user
    public static User currentUser;


    //important components of the app class
    private static Scene scene;
    private static final BorderPane root = new BorderPane(); // app container

    // Creating some of the views
    private static Parent welcomeView; // menu view
    private static Parent menuView;

    //services no DI cuz of nature of the app class
    private final static IloadFXML loader = new LoadFXML();
    public final static ShowMessage showMessage = new ShowMessage();
    public final static ImenuSwitch menuSwitch = new MenuSwitch();
    public final static IthemeHandler themeHandler = new ThemeHandler();
    public final static IinputVerifierPassword inputVerifierPassword = new InputVerifierPassword();
    public final static IpasswordSwitch passwordSwitch = new PasswordSwitch();
    public final static Iencoder encoder = new Encoder();
    public static ScreenSize screenService;

    //user preferences
    public static boolean darkTheme = true;

    public static void main(String[] args) {
        launch(App.class, args);
    }

    // media
    private final double minWidth = 400;
    private final double minHeight = 450;

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
        stage.setMinWidth(250);
        stage.setMinHeight(350);

        //media
        addMedia();

        //screen sizing
        ScreenSize screenService = new ScreenSize(scene, stage);
        screenService.fullScreen();

        stage.show();
    }

    public static void changeView(String parent) {
        Parent view = loader.loadFXML( String.format("views/%s-view.fxml", parent) );
        if (view == null){
            showMessage.alert(Alert.AlertType.ERROR, "ERROR LOADING VIEW",
                    parent + " couldn't be loaded", "Please check the file or path");
        }
        root.setCenter(view);
    }

    //this method allows the change of themes without reloading
    public static void changeCenterTheme() {
        if(!App.darkTheme){ App.themeHandler.applyLightTheme((Parent) root.getCenter()); }
        else { App.themeHandler.removeLightTheme((Parent) root.getCenter()); }
    }

    private void addMedia() {
        // These variables allow concurrent checks of both dimensions
        AtomicBoolean vFlag = new AtomicBoolean(true);
        AtomicBoolean hFlag = new AtomicBoolean(true);

        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() < minWidth) {
                // Scene width is too small, perform actions
                root.setStyle("-fx-font-size: 12px");
                hFlag.set(false);
            }
        });
        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.doubleValue() < minHeight) {
                root.setStyle("-fx-font-size: 12px");
                vFlag.set(false);
            }
        });

        scene.widthProperty().addListener((observable, oldValue, newValue) -> {
            if ((newValue.doubleValue() > minWidth)) {
                if( vFlag.get() ) { root.setStyle("-fx-font-size: 24px"); }
                hFlag.set(true);
            }
        });
        scene.heightProperty().addListener((observable, oldValue, newValue) -> {
            if ((newValue.doubleValue() > minHeight)) {
                if( hFlag.get() ) { root.setStyle("-fx-font-size: 24px"); }
                vFlag.set(true);
            }
        });
    }

    public static void setWelcomeView() {
        hideMenu();
        root.setCenter(welcomeView);
        changeCenterTheme(); //changing the theme or removing it since it's a static var of the app
    }

    public static void showMenu() { root.setLeft(menuView); }

    public static void hideMenu() { root.setLeft(null); }
}