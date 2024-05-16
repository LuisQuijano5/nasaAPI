package comprehensive.project.nasaapi.services;

import javafx.scene.Parent;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

public interface IpasswordSwitch {
    public boolean toogleVisibility(PasswordField passwordField, TextField textField,
                                    Parent passwordContainer, Parent textContainer, boolean isVisible);
}
