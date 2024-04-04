package comprehensive.project.nasaapi.services;

import javafx.scene.Node;
import javafx.scene.control.PasswordField;

public interface IinputVerifierPassword {
    public void addListener(PasswordField node);

    public boolean checkInput(PasswordField node);
}
