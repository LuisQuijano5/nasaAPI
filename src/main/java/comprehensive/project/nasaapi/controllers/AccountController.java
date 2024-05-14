package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import org.kordamp.ikonli.javafx.FontIcon;

public class AccountController {
    @FXML
    VBox toggleBtnsContainer;
    @FXML
    BorderPane container;
    @FXML
    FontIcon closedEye;
    @FXML
    FontIcon openEye;
    private boolean menuVisibility = true;

    public void initialize(){
        if(!App.darkTheme){
            App.themeHandler.applyLightTheme(container);
        }
        if(App.currentUser.getMenuVisibilityPref() == 0){
            menuVisibility = App.menuSwitch.switchMenu(menuVisibility, openEye, closedEye);
        }
        loadToggleButtons();
    }

    @FXML
    private void changeMenuVisibility() {
        menuVisibility = App.menuSwitch.switchMenu(menuVisibility, openEye, closedEye);
    }

    private void loadToggleButtons() {
    }

}
