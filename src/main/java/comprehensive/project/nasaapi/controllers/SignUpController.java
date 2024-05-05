package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import comprehensive.project.nasaapi.database.DAO.AuxDao;
import comprehensive.project.nasaapi.database.DAO.UserDao;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;

public class SignUpController {
    private final UserDao userDao = new UserDao();
    @FXML
    FontIcon returnIcon;
    @FXML
    VBox container;
    @FXML
    PasswordField passwordField, confirmPasswordField;
    @FXML
    TextField usernameField, visiblePasswordField, visibleConfirmPasswordField;
    @FXML
    HBox passwordContainer, confirmPasswordContainer, visiblePasswordContainer, visibleConfirmPasswordContainer;

    private boolean passwordVisibility = false;

    public void initialize(){
        if(!App.darkTheme){
            returnIcon.setIconColor(Paint.valueOf("black"));
            App.themeHandler.applyLightTheme(container);
        }

        //App.inputVerifierPassword.addListener(passwordField); // setting the listener in password field
    }

    @FXML
    private void changePasswordVisibility() {
        App.passwordSwitch.toogleVisibility(passwordField, visiblePasswordField, passwordContainer,
                visiblePasswordContainer, passwordVisibility);
        passwordVisibility = App.passwordSwitch.toogleVisibility(confirmPasswordField, visibleConfirmPasswordField,
                confirmPasswordContainer, visibleConfirmPasswordContainer, passwordVisibility);
    }

    @FXML
    private void goBack() {
        App.setWelcomeView();
    }

    @FXML
    private void submit() throws IOException {
        if(passwordVisibility){
            passwordField.setText(visiblePasswordField.getText());
            confirmPasswordField.setText(visibleConfirmPasswordField.getText());
        }
        // check the new password input
        if( !App.inputVerifierPassword.checkInput(passwordField)){ return; }

        //check that both the confirmation and password are the same
        if(passwordField.getText().compareTo(confirmPasswordField.getText()) != 0){
            App.showMessage.alert(Alert.AlertType.WARNING, "PASSWORD ISSUE",
                    "Your confirmation password differs", "Please reenter your password and confirmation");
            return;
        }

        if(!checkName(usernameField.getText())){
            return;
        }

        // if everything is alright we sign In
        AuxDao auxDao = userDao.register(usernameField.getText(), passwordField.getText(), determineIfAdmin());
        if(auxDao.isSuccess()){
            signUp();
        } else {
            App.showMessage.alert(Alert.AlertType.ERROR, "ERROR", auxDao.getMessage(), "PLEASE CHECK THE USERNAME AND PASSWORD");
        }

    }

    private boolean determineIfAdmin() throws IOException {
        AuxDao auxDao = userDao.determineIfAdmin();
        if(auxDao.isSuccess()){
            return auxDao.isCondition();
        } else {
            App.showMessage.alert(Alert.AlertType.ERROR, "ERROR", auxDao.getMessage(), "This user wont be stored as ADMIN");
            return false;
        }
    }

    private boolean checkName(String name) throws IOException {
        AuxDao auxDao = userDao.checkName(name);
        if(!auxDao.isSuccess()){
            App.showMessage.alert(Alert.AlertType.ERROR, "ERROR", auxDao.getMessage(), "Please use another name");
            return false;
        }
        return true;
    }

    private void signUp() throws IOException {


        App.showMenu();
        App.changeView("apod");
    }

}
