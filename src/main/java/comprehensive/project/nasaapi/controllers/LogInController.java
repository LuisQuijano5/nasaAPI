package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import comprehensive.project.nasaapi.database.DAO.*;
import comprehensive.project.nasaapi.models.User;
import comprehensive.project.nasaapi.services.PermitsSetter;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class LogInController {
    private final UserDao userDao = new UserDao();
    private final ViewDao viewDao = new ViewDao();
    private final PrivilegeDao privilegeDao = new PrivilegeDao();
    private final PreferenceDao preferenceDao = new PreferenceDao();
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
        enableFields();
        if(!App.darkTheme){
            returnIcon.setIconColor(Paint.valueOf("black"));
            App.themeHandler.applyLightTheme(container);
        }

        StringProperty sharedText = new SimpleStringProperty();
        passwordField.textProperty().bindBidirectional(sharedText);
        visiblePasswordField.textProperty().bindBidirectional(sharedText);
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
        //disableFields();
        //to avoid issues if the textfields are suddenly changed
        String name = usernameField.getText();
        String password = passwordField.getText();

        AuxDao auxDao = userDao.logIn(name, password );
        if(auxDao.isSuccess()){
            logIn(auxDao, name, password);
        }else{
            App.showMessage.alert(Alert.AlertType.ERROR, "ERROR", auxDao.getMessage(), "PLEASE CHECK THE USERNAME AND PASSWORD");
        }
        //enableFields();
    }

    private boolean setPermits()  {
        try {
            App.currentUser.setPreferences(preferenceDao);
            App.currentUser.setPrivileges(privilegeDao);
            App.currentUser.setAccess(viewDao);
        } catch(IOException e) {
            return false;
        }
        return true;
    }

    private void logIn(AuxDao auxDao, String name, String pass) throws IOException {
        App.currentUser = new User(auxDao.getId(), name, auxDao.isCondition(), auxDao.getData());
        App.currentUser.setPassword(pass);
        if(!setPermits()){
            PermitsSetter.setDefault(1);
        }

        App.showMenu();
        App.changeView("apod");
    }

    private void disableFields(){
        passwordField.setDisable(true);
        visiblePasswordField.setDisable(true);
        usernameField.setDisable(true);
    }

    private void enableFields(){
        passwordField.setDisable(false);
        visiblePasswordField.setDisable(false);
        usernameField.setDisable(false);
    }
}
