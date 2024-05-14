package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import comprehensive.project.nasaapi.models.User;

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
        App.currentUser = new User(-1, "Guest", false, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);

        App.showMenu();
        App.changeView("apod");
    }

}