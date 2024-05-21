package comprehensive.project.nasaapi.services;

import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.DatePicker;
import javafx.scene.paint.Paint;
import javafx.scene.text.Text;
import org.kordamp.ikonli.javafx.FontIcon;
import comprehensive.project.nasaapi.App;
import java.awt.*;

public class ThemeHandler implements IthemeHandler{
    @Override
    public void applyLightTheme(Parent parent) {
        if(App.welcomeView == parent){
            return;
        }
        parent.getStyleClass().add("lightTheme");
        //parent.getStyleClass().remove("darkTheme");
        ObservableList<Node> children = parent.getChildrenUnmodifiable();
        for (Node child : children) {
            // Add the style class to all nodes
            child.getStyleClass().add("lightTheme");
            //child.getStyleClass().remove("darkTheme");
            if (child instanceof FontIcon) {
                ((FontIcon) child).setIconColor(Paint.valueOf("black"));
            } else if (child instanceof Parent) {
                applyLightTheme((Parent) child);
            }
        }
    }

    @Override
    public void removeLightTheme(Parent parent) {
        parent.getStyleClass().remove("lightTheme");
        //parent.getStyleClass().add("darkTheme");
        ObservableList<Node> children = parent.getChildrenUnmodifiable();
        for (Node child : children) {
            child.getStyleClass().remove("lightTheme");
            //child.getStyleClass().add("darkTheme");
            if (child instanceof FontIcon) {
                ((FontIcon) child).setIconColor(Paint.valueOf("white"));
            }
            else if (child instanceof Parent) {
                removeLightTheme((Parent) child);
            }
        }
    }

}
