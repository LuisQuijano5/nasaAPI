package comprehensive.project.nasaapi.models;

import comprehensive.project.nasaapi.App;
import comprehensive.project.nasaapi.database.DAO.AuxDao;
import comprehensive.project.nasaapi.database.DAO.PreferenceDao;
import comprehensive.project.nasaapi.database.DAO.PrivilegeDao;
import comprehensive.project.nasaapi.database.DAO.ViewDao;
import javafx.scene.control.Alert;

import java.io.IOException;

public class User
{
    private int id;
    private String name;
    private boolean isAdmin;
    private String token;
    private Favorites favorites;
    private int apodAccess, apodPrivilege;
    private int galleryAccess, galleryPrivilege;
    private int epicAccess, epicPrivilege;
    private int accountAccess, accountPrivilege;
    private int colorModePref, menuVisibilityPref;

    public void setPreferences(PreferenceDao dao) throws IOException {
        AuxDao auxDao = dao.getPreferenceByUserId(this.id);
        if(!auxDao.isSuccess()){
            App.showMessage.alert(Alert.AlertType.ERROR, "ERROR", auxDao.getMessage(), "Please try again");
            return;
        }
        this.colorModePref = auxDao.getValues()[0];
        this.menuVisibilityPref = auxDao.getValues()[1];
    }

    public void setAccess(ViewDao dao) throws IOException{
        AuxDao auxDao = dao.getViewsByUserId(this.id);
        if(!auxDao.isSuccess()){
            App.showMessage.alert(Alert.AlertType.ERROR, "ERROR", auxDao.getMessage(), "Please try again");
            return;
        }
        this.apodAccess = auxDao.getValues()[0];
        this.galleryAccess = auxDao.getValues()[1];
        this.epicAccess = auxDao.getValues()[2];
        this.accountAccess = auxDao.getValues()[3];
    }

    public void setPrivileges(PrivilegeDao dao) throws IOException{
        AuxDao auxDao = dao.getPrivilegeByUserId(this.id);
        if(!auxDao.isSuccess()){
            App.showMessage.alert(Alert.AlertType.ERROR, "ERROR", auxDao.getMessage(), "Please try again");
            return;
        }
        this.apodPrivilege = auxDao.getValues()[0];
        this.galleryPrivilege = auxDao.getValues()[1];
        this.epicPrivilege = auxDao.getValues()[2];
        this.accountPrivilege = auxDao.getValues()[3];
    }

    public User() {
    }
    public User(int id, String name, boolean isAdmin, String token) {
        this.id = id;
        this.name = name;
        this.isAdmin = isAdmin;
        this.token = token;
    }

    public User(int id, String name, boolean isAdmin, String token, int apodAccess, int apodPrivilege, int galleryAccess, int galleryPrivilege, int epicAccess, int epicPrivilege, int accountAccess, int accountPrivilege, int colorModePref, int menuVisibilityPref) {
        this.id = id;
        this.name = name;
        this.isAdmin = isAdmin;
        this.token = token;
        this.apodAccess = apodAccess;
        this.apodPrivilege = apodPrivilege;
        this.galleryAccess = galleryAccess;
        this.galleryPrivilege = galleryPrivilege;
        this.epicAccess = epicAccess;
        this.epicPrivilege = epicPrivilege;
        this.accountAccess = accountAccess;
        this.accountPrivilege = accountPrivilege;
        this.colorModePref = colorModePref;
        this.menuVisibilityPref = menuVisibilityPref;
    }

    //for guests
    public User(int id, String name, boolean isAdmin, int apodAccess, int apodPrivilege, int galleryAccess, int galleryPrivilege, int epicAccess, int epicPrivilege, int accountAccess, int accountPrivilege, int colorModePref, int menuVisibilityPref) {
        this.id = id;
        this.name = name;
        this.isAdmin = isAdmin;
        this.apodAccess = apodAccess;
        this.apodPrivilege = apodPrivilege;
        this.galleryAccess = galleryAccess;
        this.galleryPrivilege = galleryPrivilege;
        this.epicAccess = epicAccess;
        this.epicPrivilege = epicPrivilege;
        this.accountAccess = accountAccess;
        this.accountPrivilege = accountPrivilege;
        this.colorModePref = colorModePref;
        this.menuVisibilityPref = menuVisibilityPref;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(boolean admin) {
        isAdmin = admin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Favorites getFavorites() {
        return favorites;
    }

    public void setFavorites(Favorites favorites) {
        this.favorites = favorites;
    }

    public int getApodAccess() {
        return apodAccess;
    }

    public void setApodAccess(int apodAccess) {
        this.apodAccess = apodAccess;
    }

    public int getApodPrivilege() {
        return apodPrivilege;
    }

    public void setApodPrivilege(int apodPrivilege) {
        this.apodPrivilege = apodPrivilege;
    }

    public int getGalleryAccess() {
        return galleryAccess;
    }

    public void setGalleryAccess(int galleryAccess) {
        this.galleryAccess = galleryAccess;
    }

    public int getGalleryPrivilege() {
        return galleryPrivilege;
    }

    public void setGalleryPrivilege(int galleryPrivilege) {
        this.galleryPrivilege = galleryPrivilege;
    }

    public int getEpicAccess() {
        return epicAccess;
    }

    public void setEpicAccess(int epicAccess) {
        this.epicAccess = epicAccess;
    }

    public int getEpicPrivilege() {
        return epicPrivilege;
    }

    public void setEpicPrivilege(int epicPrivilege) {
        this.epicPrivilege = epicPrivilege;
    }

    public int getAccountAccess() {
        return accountAccess;
    }

    public void setAccountAccess(int accountAccess) {
        this.accountAccess = accountAccess;
    }

    public int getAccountPrivilege() {
        return accountPrivilege;
    }

    public void setAccountPrivilege(int accountPrivilege) {
        this.accountPrivilege = accountPrivilege;
    }

    public int getColorModePref() {
        return colorModePref;
    }

    public void setColorModePref(int colorModePref) {
        this.colorModePref = colorModePref;
    }

    public int getMenuVisibilityPref() {
        return menuVisibilityPref;
    }

    public void setMenuVisibilityPref(int menuVisibilityPref) {
        this.menuVisibilityPref = menuVisibilityPref;
    }
}
