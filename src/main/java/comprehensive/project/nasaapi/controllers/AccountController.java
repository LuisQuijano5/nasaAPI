package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import comprehensive.project.nasaapi.database.DAO.AuxDao;
import comprehensive.project.nasaapi.database.DAO.ModificationDao;
import comprehensive.project.nasaapi.database.DAO.PreferenceDao;
import comprehensive.project.nasaapi.database.DAO.UserDao;
import comprehensive.project.nasaapi.models.Modification;
import comprehensive.project.nasaapi.models.User;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

import java.io.IOException;
import java.util.List;

public class AccountController {
    @FXML
    HBox toggleBtnsContainer;
    @FXML
    BorderPane container;
    @FXML
    FontIcon closedEye;
    @FXML
    FontIcon openEye;
    @FXML
    Button myAccountBtn, usersBtn, modificationsBtn;
    @FXML
    VBox guestsSection, usersSection, modificationsSection, myAccountSection;
    @FXML
    VBox passwordContainer, visiblePasswordContainer;
    @FXML
    PasswordField passwordField;
    @FXML
    TextField usernameField, visiblePasswordField;
    private boolean menuVisibility = true;
    private boolean passwordIsVisible = false;
    private UserDao userDao = new UserDao();
    private PreferenceDao prefDao = new PreferenceDao();
    private ModificationDao modDao = new ModificationDao();

    public void initialize(){
        if(!App.darkTheme){
            App.themeHandler.applyLightTheme(container);
        }
        if(App.currentUser.getMenuVisibilityPref() == 0){
            menuVisibility = App.menuSwitch.switchMenu(menuVisibility, openEye, closedEye);
        }

        //Access visibility handling
        if(App.currentUser.getAccountAccess() > 0 || App.currentUser.isAdmin()){
            setMyAccountSection();
            myAccountBtn.setVisible(true);
        }

        if(App.currentUser.getAccountAccess() == 2){
            modificationsBtn.setVisible(true);
        } else if(App.currentUser.getAccountAccess() == 3 || App.currentUser.isAdmin()){
            modificationsBtn.setVisible(true);
            usersBtn.setVisible(true);
        }

        // delete this when testing is done
        myAccountBtn.setVisible(true); // for testing
        modificationsBtn.setVisible(true); // for testing
        usersBtn.setVisible(true); // for testing

        StringProperty sharedText = new SimpleStringProperty();
        passwordField.textProperty().bindBidirectional(sharedText);
        visiblePasswordField.textProperty().bindBidirectional(sharedText);
    }

    @FXML
    private void changeMenuVisibility() {
        menuVisibility = App.menuSwitch.switchMenu(menuVisibility, openEye, closedEye);
    }

    @FXML
    private void setMyAccountSection(){
        loadToggleButtons(1);
    }

    @FXML
    private void setModificationsSection(){
        loadToggleButtons(2);
    }

    @FXML
    private void setUsersSection(){
        loadToggleButtons(3);
    }

    @FXML
    private void changePasswordVisibility() {
        passwordIsVisible = App.passwordSwitch.toogleVisibility(passwordField, visiblePasswordField, passwordContainer,
                visiblePasswordContainer, passwordIsVisible);
    }

    @FXML
    private void updateUserInfo() throws IOException {
        String pastName = App.currentUser.getName();
        String pastPass = App.currentUser.getPassword();
        if( !App.inputVerifierPassword.checkInput(passwordField)){
            return;
        }
        App.currentUser.setName(usernameField.getText());
        App.currentUser.setPassword(passwordField.getText());
        AuxDao auxDao = userDao.updateUser(App.currentUser);
        if(!auxDao.isSuccess()){
            App.currentUser.setName(pastName);
            App.currentUser.setPassword(pastPass);
        }
    }

    @FXML
    private void showUsers() throws IOException {
        //this is obv a demo only to show how to implement the methodto create a table
        AuxDao auxDao = userDao.getUsers(App.currentUser);
        if(!auxDao.isSuccess()){
            return;
        }
        List<User> usersList = auxDao.getUsers();
        for(User user : usersList){
            System.out.println(user.getId() + " " + user.getName());
        }
    }

    @FXML
    private void deleteUser() throws IOException {
        //got an error to solve in the api, please remember me, the operation is going to come as unsuccesful but it wil actually delete the record. Logic issue in the api
        //this is obv a demo only to show how to implement the methodto create a table
        int id = 15; //get the selected user from the table
        AuxDao auxDao = userDao.deleteUser(App.currentUser, id);
        System.out.println(auxDao.getMessage());
    }

    @FXML
    private void updatePref() throws IOException {
        //demo
        int id = 16; //get the selected user from the table
        String name = "menuVisibility";
        AuxDao auxDao = prefDao.updatePreference(App.currentUser, id, 0, name);
        System.out.println(auxDao.getMessage());
    }

    @FXML
    private void showModifications() throws IOException {
        AuxDao auxDao = modDao.getModifications(App.currentUser);
        if(!auxDao.isSuccess()){
            System.out.println("error mods");
            return;
        }
        List<Modification> usersList = auxDao.getMods();
        for(Modification mod : usersList){
            System.out.println(mod.getUserId() + " " + mod.getAction());
        }
    }

    private void loadToggleButtons(int section){
        guestsSection.setVisible(section == 0);
        myAccountSection.setVisible(section == 1);
        modificationsSection.setVisible(section == 2);
        usersSection.setVisible(section == 3);
    }

}
