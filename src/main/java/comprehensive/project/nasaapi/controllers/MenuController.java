package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class MenuController {

    @FXML
    private void goAPOD() {
        App.changeView("apod");
    }

    @FXML
    private void goGallery() {
        App.changeView("gallery");
    }

    @FXML
    private void goEPIC() {
        App.changeView("epic");
    }

    @FXML
    private void goAccount() {
        App.changeView("account");
    }

    @FXML
    private void exit() {
        App.setWelcomeView();
    }

    @FXML
    private void goToGithub() {
    }

    @FXML
    private void goToDocumentation() {
    }

    @FXML
    private void showHelp() {
    }
}
