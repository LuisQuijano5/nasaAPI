package comprehensive.project.nasaapi.services;

import javafx.scene.control.Alert;

public interface IshowMessage {
    public void alert(Alert.AlertType type, String title, String header, String context);
}
