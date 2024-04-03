package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import javafx.fxml.FXML;

public class LogInController {

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
