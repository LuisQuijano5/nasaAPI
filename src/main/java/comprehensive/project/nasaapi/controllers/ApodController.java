package comprehensive.project.nasaapi.controllers;

import comprehensive.project.nasaapi.App;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import org.kordamp.ikonli.javafx.FontIcon;


public class ApodController {
    @FXML
    FontIcon closedEye;
    @FXML
    FontIcon openEye;
    private boolean menuVisibility = true;

    @FXML
    private void changeMenuVisibility() {
        menuVisibility = App.menuSwitch.switchMenu(menuVisibility, openEye, closedEye);
    }
}
