package comprehensive.project.nasaapi.services;

import comprehensive.project.nasaapi.App;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

public class LoadFXML implements IloadFXML{
    @Override
    public Parent loadFXML(String fxmlPath) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxmlPath));
            return fxmlLoader.load();
        } catch (Exception e) {
            e.printStackTrace();
            return null;  // Indicate loading failure
        }
    }
}
