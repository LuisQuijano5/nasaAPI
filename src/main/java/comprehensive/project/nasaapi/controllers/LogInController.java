package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import comprehensive.project.nasaapi.database.DAO.AuxDao;
import comprehensive.project.nasaapi.database.DAO.UserDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.Objects;

public class LogInController {
    private final UserDao userDao = new UserDao();
    @FXML
    HBox passwordContainer, visiblePasswordContainer;
    @FXML
    PasswordField passwordField;
    @FXML
    TextField usernameField, visiblePasswordField;
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
    private void submit() throws IOException {
        AuxDao auxDao = userDao.logIn(usernameField.getText(), passwordField.getText() );
        if(auxDao.isSuccess()){
            logIn(auxDao.getData());
        }else{
            App.showMessage.alert(Alert.AlertType.ERROR, "ERROR", auxDao.getMessage(), "PLEASE CHECK THE USERNAME AND PASSWORD");
        }
    }

    private void logIn(String token) throws IOException {
        App.showMenu();
        App.changeView("apod");
    }
}
