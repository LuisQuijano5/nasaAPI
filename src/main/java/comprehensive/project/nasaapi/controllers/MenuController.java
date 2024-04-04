package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

public class MenuController {
    @FXML
    StackPane themeContainer;
    @FXML
    FontIcon returnIcon, moonIcon, sunIcon;
    @FXML
    VBox container;

    public void initialize(){
        if(!App.darkTheme){ changeTheme(); }
    }

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

    @FXML
    private void changeTheme() {
        themeContainer.setVisible(false);// to avoid semichanges
        if(App.darkTheme) { // means light theme has to be applied lol :X
            moonIcon.setVisible(true);
            sunIcon.setVisible(false);
            returnIcon.setIconColor(Paint.valueOf("black")); //manually cuz it's the graphic of a button
            App.themeHandler.applyLightTheme(container);
        } else {
            moonIcon.setVisible(false);
            sunIcon.setVisible(true);
            returnIcon.setIconColor(Paint.valueOf("white")); //manually cuz it's the graphic of a button
            App.themeHandler.removeLightTheme(container);
        }
        App.darkTheme = !App.darkTheme;
        App.changeCenterTheme(); //changing the current api theme
        themeContainer.setVisible(true);
    }
}
