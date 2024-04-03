package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class WelcomeController {

    @FXML
    private Label titleLbl;

    // Need change to allow DI

    public void initialize() {

    }

    @FXML
    private void LogIn() {
        App.hideMenu();
        App.changeView("logIn");
    }

    @FXML
    private void SignUp() {
        App.hideMenu();
        App.changeView("signUp");
    }

    @FXML
    private void continueAsGuest() {
        App.showMenu();
        App.changeView("apod");
    }

}