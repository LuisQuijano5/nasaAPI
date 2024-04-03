package comprehensive.project.nasaapi.services;

import comprehensive.project.nasaapi.App;
import org.kordamp.ikonli.javafx.FontIcon;

public class MenuSwitch implements ImenuSwitch{
    @Override
    public boolean switchMenu(boolean visibility, FontIcon openEye, FontIcon closedEye) {
        if(visibility){
            App.hideMenu();
            openEye.setVisible(true);
            closedEye.setVisible(false);
        } else {
            App.showMenu();
            closedEye.setVisible(true);
            openEye.setVisible(false);
        }
        return !visibility;
    }
}
