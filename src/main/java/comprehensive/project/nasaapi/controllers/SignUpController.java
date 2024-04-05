package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

public class SignUpController {
    @FXML
    FontIcon returnIcon;
    @FXML
    VBox container;
    @FXML
    PasswordField passwordField, confirmPasswordField;
    @FXML
    TextField visiblePasswordField, visibleConfirmPasswordField;
    @FXML
    HBox passwordContainer, confirmPasswordContainer, visiblePasswordContainer, visibleConfirmPasswordContainer;

    private boolean passwordVisibility;

    public void initialize(){
        if(!App.darkTheme){
            returnIcon.setIconColor(Paint.valueOf("black"));
            App.themeHandler.applyLightTheme(container);
        }

        App.inputVerifierPassword.addListener(passwordField); // setting the listener in password field
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
    private void submit() {
        // check the new password input
        if( !App.inputVerifierPassword.checkInput(passwordField) ){
            passwordField.clear();
            confirmPasswordField.clear();
            return;
        }

        //check that both the confirmation and password are the same
        if(passwordField.getText().compareTo(confirmPasswordField.getText()) != 0){
            App.showMessage.alert(Alert.AlertType.WARNING, "PASSWORD ISSUE",
                    "Your confirmation password differs", "Please reenter your password and confirmation");
            return;
        }

        // if everything is right we sign In
        signIn();
    }

    private void signIn() {
        byte[] password = App.encoder.encode(passwordField.getText());


        App.showMenu();
        App.changeView("apod");
    }

}
