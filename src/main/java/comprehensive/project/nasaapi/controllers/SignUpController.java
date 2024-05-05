package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import comprehensive.project.nasaapi.database.DAO.*;
import comprehensive.project.nasaapi.models.User;
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

public class SignUpController {
    private final UserDao userDao = new UserDao();
    private final ViewDao viewDao = new ViewDao();
    private final PrivilegeDao privilegeDao = new PrivilegeDao();
    private final PreferenceDao preferenceDao = new PreferenceDao();
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
        enableFields();
        if(!App.darkTheme){
            returnIcon.setIconColor(Paint.valueOf("black"));
            App.themeHandler.applyLightTheme(container);
        }
        StringProperty sharedText = new SimpleStringProperty();
        passwordField.textProperty().bindBidirectional(sharedText);
        visiblePasswordField.textProperty().bindBidirectional(sharedText);

        StringProperty sharedTextConfirmation = new SimpleStringProperty();
        confirmPasswordField.textProperty().bindBidirectional(sharedTextConfirmation);
        visibleConfirmPasswordField.textProperty().bindBidirectional(sharedTextConfirmation);

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
        //disableFields();
        // check the new password input
        if( !App.inputVerifierPassword.checkInput(passwordField)){
            enableFields();
            return;
        }

        //check that both the confirmation and password are the same
        if(passwordField.getText().compareTo(confirmPasswordField.getText()) != 0){
            App.showMessage.alert(Alert.AlertType.WARNING, "PASSWORD ISSUE",
                    "Your confirmation password differs", "Please reenter your password and confirmation");
            //enableFields();
            return;
        }

        //to avoid issues if the textfields are suddenly changed
        String name = usernameField.getText();
        String password =  passwordField.getText();
        boolean isAdmin = determineIfAdmin();

        //no repetition of name
        if(!isAdmin){
            if(!checkName(usernameField.getText())){
                //enableFields();
                return;
            }
        }

        // if everything is alright we sign In
        AuxDao auxDao = userDao.register(name, password, isAdmin);
        if(auxDao.isSuccess()){
            if(isAdmin){
                if(setPreferences(auxDao.getId())){ return; }
            } else if(!setUserConfig(auxDao.getId())){ return; }
            auxDao = userDao.logIn(name, password);//login
            if(auxDao.isSuccess()){
                signUp(name, isAdmin, auxDao);
            }else{
                App.showMessage.alert(Alert.AlertType.ERROR, "ERROR", auxDao.getMessage(), "PLEASE CHECK THE USERNAME AND PASSWORD");
            }
        } else {
            App.showMessage.alert(Alert.AlertType.ERROR, "ERROR", auxDao.getMessage(), "PLEASE CHECK THE USERNAME AND PASSWORD");
        }
        //enableFields();
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

    private boolean setPermits(int userId) throws IOException {
        String[] viewNames = {"apod", "gallery", "epic", "account"};
        AuxDao auxDao;
        for(String viewName : viewNames){
            auxDao = viewDao.setViewPermits(userId, viewName, 1);
            if(!auxDao.isSuccess()){
                App.showMessage.alert(Alert.AlertType.ERROR, "ERROR", auxDao.getMessage(), "Please try again");
                return false;
            }
            auxDao = privilegeDao.setPrivilege(userId, viewName, 1);
            if(!auxDao.isSuccess()){
                App.showMessage.alert(Alert.AlertType.ERROR, "ERROR", auxDao.getMessage(), "Please try again");
                return false;
            }
        }
        return true;
    }

    private boolean setPreferences(int userId) throws IOException {
        String[] preferences = {"darkMode", "menuVisibility"};
        AuxDao auxDao;
        for(String preference : preferences){
            auxDao = preferenceDao.setPreference(userId, preference, 1);
            if(!auxDao.isSuccess()){
                App.showMessage.alert(Alert.AlertType.ERROR, "ERROR", auxDao.getMessage(), "Please try again");
                return false;
            }
        }
        return true;
    }

    private boolean setUserConfig(int userId) throws IOException {
        return setPermits(userId) && setPreferences(userId);
    }

    private void signUp(String name, boolean isAdmin, AuxDao auxDao) {
        App.currentUser = new User(auxDao.getId(), name, isAdmin, auxDao.getData(), 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);

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
