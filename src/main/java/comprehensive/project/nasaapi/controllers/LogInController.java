package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

public class LogInController {
    @FXML
    HBox passwordContainer, visiblePasswordContainer;
    @FXML
    PasswordField passwordField;
    @FXML
    TextField visiblePasswordField;
    @FXML
    FontIcon returnIcon;
    @FXML
    VBox container;

    private boolean passwordIsVisible = false;

    @FXML
    public void initialize(){
        if(!App.darkTheme){
            returnIcon.setIconColor(Paint.valueOf("black"));
            App.themeHandler.applyLightTheme(container);
        }
    }

    @FXML
    private void changePasswordVisibility() {
        passwordIsVisible = App.passwordSwitch.toogleVisibility(passwordField, visiblePasswordField, passwordContainer,
                visiblePasswordContainer, passwordIsVisible);
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
