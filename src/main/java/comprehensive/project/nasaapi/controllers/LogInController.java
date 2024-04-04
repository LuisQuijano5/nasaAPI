package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class LogInController {
    @FXML
    VBox container;

    public void initialize(){
        if(!App.darkTheme){ App.themeHandler.applyLightTheme(container); }
    }

    @FXML
    private void goBack() {
        App.setWelcomeView();
    }

    @FXML
    private void submit() {
        logIn();
    }

    private void logIn() {
        App.showMenu();
        App.changeView("apod");
    }
}
