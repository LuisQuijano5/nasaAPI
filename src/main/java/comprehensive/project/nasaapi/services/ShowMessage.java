package comprehensive.project.nasaapi.services;

import javafx.scene.control.Alert;

public class ShowMessage implements IshowMessage{
    @Override
    public void alert(Alert.AlertType type, String title, String header, String context)
    {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(context);
        alert.showAndWait();
    }
}
