package comprehensive.project.nasaapi.services;

import javafx.scene.Scene;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class ScreenSize implements IscreenSize{
    private Scene scene;
    private Stage stage;

    public ScreenSize(Scene scene, Stage stage){
        this.scene = scene;
        this.stage = stage;
    }
    @Override
    public void fullScreen() {
        // Get the screen width and height
        double screenWidth = Screen.getPrimary().getBounds().getWidth();
        double screenHeight = Screen.getPrimary().getBounds().getHeight();

        // Set the initial stage size
        stage.setWidth(screenWidth);
        stage.setHeight(screenHeight);

        // Listen for changes in screen size
        stage.widthProperty().addListener((observable, oldValue, newValue) -> {
            // Update scene width based on new stage width
            scene.getRoot().minWidth(newValue.doubleValue());
            scene.getRoot().prefWidth(newValue.doubleValue());
            scene.getRoot().maxWidth(newValue.doubleValue());
        });

        stage.heightProperty().addListener((observable, oldValue, newValue) -> {
            // Update scene height based on new stage height
            scene.getRoot().minHeight(newValue.doubleValue());
            scene.getRoot().prefHeight(newValue.doubleValue());
            scene.getRoot().maxHeight(newValue.doubleValue());
        });
    }

    @Override
    public void reduceScreen() {

    }
}
