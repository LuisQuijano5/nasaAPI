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

        AuxDao auxDao = userDao.logIn(name, passwordField.getText() );
        if(auxDao.isSuccess()){
            logIn(auxDao, name);
        }else{
            App.showMessage.alert(Alert.AlertType.ERROR, "ERROR", auxDao.getMessage(), "PLEASE CHECK THE USERNAME AND PASSWORD");
        }
        //enableFields();
    }

    private boolean setPermits(int id) throws IOException {
        AuxDao auxDao = viewDao.getViewsByUserId(id);
        if(!auxDao.isSuccess()){
            App.showMessage.alert(Alert.AlertType.ERROR,"ERROR", auxDao.getMessage(), "Setting defaul permits");
            return false;
        }
        int[] viewsValues = auxDao.getValues();
        App.currentUser.setApodAccess(viewsValues[0]);
        App.currentUser.setGalleryAccess(viewsValues[1]);
        App.currentUser.setEpicAccess(viewsValues[2]);
        App.currentUser.setAccountAccess(viewsValues[3]);

        auxDao = privilegeDao.getPrivilegeByUserId(id);
        if(!auxDao.isSuccess()){
            App.showMessage.alert(Alert.AlertType.ERROR,"ERROR", auxDao.getMessage(), "Setting defaul permits");
            return false;
        }
        viewsValues = auxDao.getValues();
        App.currentUser.setApodPrivilege(viewsValues[0]);
        App.currentUser.setGalleryPrivilege(viewsValues[1]);
        App.currentUser.setEpicPrivilege(viewsValues[2]);
        App.currentUser.setAccountPrivilege(viewsValues[3]);

        auxDao = preferenceDao.getPreferenceByUserId(id);
        if(!auxDao.isSuccess()){
            App.showMessage.alert(Alert.AlertType.ERROR,"ERROR", auxDao.getMessage(), "Setting defaul preferences");
            return false;
        }
        viewsValues = auxDao.getValues();
        App.currentUser.setColorModePref(viewsValues[0]);
        App.currentUser.setMenuVisibilityPref(viewsValues[1]);

        return true;
    }

    private void logIn(AuxDao auxDao, String name) throws IOException {
        App.currentUser = new User(auxDao.getId(), name, auxDao.isCondition(), auxDao.getData());
        if(!setPermits(App.currentUser.getId())){
            defaultPermits(1);
        }

        App.showMenu();
        App.changeView("apod");
    }

    private void defaultPermits(int num){
        App.currentUser.setAccountAccess(num);
        App.currentUser.setApodAccess(num);
        App.currentUser.setEpicAccess(num);
        App.currentUser.setGalleryAccess(num);
        App.currentUser.setAccountPrivilege(num);
        App.currentUser.setApodPrivilege(num);
        App.currentUser.setEpicPrivilege(num);
        App.currentUser.setGalleryPrivilege(num);
        App.currentUser.setColorModePref(num);
        App.currentUser.setMenuVisibilityPref(num);

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
