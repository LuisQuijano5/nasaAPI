package comprehensive.project.nasaapi.services;

import comprehensive.project.nasaapi.App;

public class PermitsSetter
{
    public static void setDefault(int num){
        App.currentUser.setAccountAccess(num);
        App.currentUser.setApodAccess(num);
        App.currentUser.setEpicAccess(num);
        App.currentUser.setGalleryAccess(num);
        App.currentUser.setAccountPrivilege(num);
        App.currentUser.setApodPrivilege(num);
        App.currentUser.setEpicPrivilege(num);
        App.currentUser.setGalleryPrivilege(num);
        App.currentUser.setColorModePref(num);
        App.currentUser.setMenuVisibilityPref(num);
    }
}
