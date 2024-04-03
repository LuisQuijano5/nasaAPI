package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;

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
        openLink("https://github.com/LuisQuijano5/nasaAPI");
    }

    @FXML
    private void goToDocumentation() {
        openLink("https://docs.google.com/document/d/1vy7F3T8JRDCqxHmj6Ssty3-GInQbDQj2WppAFcwRVoY/edit?usp=sharing");
    }

    @FXML
    private void showHelp() {
        openLink("https://docs.google.com/document/d/1Eeq8dVdvIUuXa0zO8T7CnI82fqFy9_weE_496hZ6kBc/edit?usp=sharing");
    }

    private void openLink(String url) {
        try {
            java.awt.Desktop.getDesktop().browse(java.net.URI.create(url));
        } catch (Exception e) {
            // Handle potential exceptions
            App.showMessage.alert(Alert.AlertType.ERROR, "Couldnt open link", "Error opening link: " + e.getMessage(), "Please check the method or browse requirements");
        }
    }
}
