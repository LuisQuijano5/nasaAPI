package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;

public class SignUpController {
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
        signIn();
    }

    private void signIn() {
        App.showMenu();
        App.changeView("apod");
    }
}
