package comprehensive.project.nasaapi.models;

public class User
{
    private int id;
    private String name;
    private String token;
    private Favorites favorites;
    private int apodAccess, apodPrivilege;
    private int galleryAccess, galleryPrivilege;
    private int epicAccess, epicPrivilege;
    private int accountAccess, accountPrivilege;

    public User() {
    }

    public User(int id, String name, String token, Favorites favorites, int apodAccess, int apodPrivilege, int galleryAccess, int galleryPrivilege, int epicAccess, int epicPrivilege, int accountAccess, int accountPrivilege) {
        this.id = id;
        this.name = name;
        this.token = token;
        this.favorites = favorites;
        this.apodAccess = apodAccess;
        this.apodPrivilege = apodPrivilege;
        this.galleryAccess = galleryAccess;
        this.galleryPrivilege = galleryPrivilege;
        this.epicAccess = epicAccess;
        this.epicPrivilege = epicPrivilege;
        this.accountAccess = accountAccess;
        this.accountPrivilege = accountPrivilege;
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
}
