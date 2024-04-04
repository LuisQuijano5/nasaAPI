package comprehensive.project.nasaapi.services;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Paint;
import org.kordamp.ikonli.javafx.FontIcon;

import java.awt.*;

public class ThemeHandler implements IthemeHandler{
    @Override
    public void applyLightTheme(Parent parent) {
        parent.getStyleClass().add("lightTheme");
        ObservableList<Node> children = parent.getChildrenUnmodifiable();
        for (Node child : children) {
            // Add the style class to all nodes
            child.getStyleClass().add("lightTheme");
            if (child instanceof FontIcon) {
                ((FontIcon) child).setIconColor(Paint.valueOf("black"));
            }
            // Recursively call the function on child nodes that are containers
            else if (child instanceof Parent) {
                applyLightTheme((Parent) child);
            }
        }
    }

    @Override
    public void removeLightTheme(Parent parent) {
        parent.getStyleClass().remove("lightTheme");
        ObservableList<Node> children = parent.getChildrenUnmodifiable();
        for (Node child : children) {
            child.getStyleClass().remove("lightTheme");
            if (child instanceof FontIcon) {
                ((FontIcon) child).setIconColor(Paint.valueOf("white"));
            }
            else if (child instanceof Parent) {
                removeLightTheme((Parent) child);
            }
        }
    }

}
