package comprehensive.project.nasaapi.services;

import comprehensive.project.nasaapi.App;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;

// THIS SERVICE USES SHOW MESSAGE SEEVICE!!!!

public class InputVerifierPassword implements IinputVerifierPassword{

    @Override
    public void addListener(PasswordField node) {
        node.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (!newValue) {
                    checkInput(node);
                }
            }

        });
    }

    @Override
    public boolean checkInput(PasswordField node) {
        if (!node.getText().matches("^(?=.*[a-zA-Z])(?=.*[0-9]).*$")) {
            App.showMessage.alert(Alert.AlertType.WARNING, "Error in password input",
                    "Change or reenter your password input", "Please use both numbers and letters");
            return false;
        }
        if (node.getText().length() < 4) {
            App.showMessage.alert(Alert.AlertType.WARNING, "Error in password input",
                    "Change or reenter your password input", "Please set a password at least 4 characters long");
            return false;
        }
        return true;
    }
}
