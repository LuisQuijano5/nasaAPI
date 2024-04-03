package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class SignUpController {
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
