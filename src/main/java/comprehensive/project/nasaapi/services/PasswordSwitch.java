package comprehensive.project.nasaapi.services;

import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public class PasswordSwitch implements IpasswordSwitch{
    @Override
    public boolean toogleVisibility(PasswordField passwordField, TextField textField,
                                    Parent passwordContainer, Parent textContainer, boolean isVisible)
    {
        if(isVisible) {
            passwordContainer.setVisible(true);
            textContainer.setVisible(false);
            passwordField.setText(textField.getText());
        } else {
            passwordContainer.setVisible(false);
            textContainer.setVisible(true);
            textField.setText(passwordField.getText());
        }
        return !isVisible;
    }
}
