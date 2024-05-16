package comprehensive.project.nasaapi.database.DAO;

import comprehensive.project.nasaapi.models.*;

import java.util.List;

public class AuxDao {
    private boolean success;
    private String message;
    private String data;
    private boolean condition;
    private int id;
    private int[] values;
    private List<User> users;
    private List<Modification> mods;
    private List<Favorites> favs;
    private List<PicODay> pics;
    private Resource resource;

    public AuxDao() {
    }

    public AuxDao(boolean success) {
        this.success = success;
    }

    public AuxDao(boolean success, Resource resource) {
        this.success = success;
        this.resource = resource;
    }

    public AuxDao(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public AuxDao(boolean success, int[] values) {
        this.success = success;
        this.values = values;
    }

    public AuxDao(boolean success, String message, String data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }

    public AuxDao(boolean success, String message, boolean condition) {
        this.success = success;
        this.message = message;
        this.condition = condition;
    }

    public AuxDao(boolean success, String message, int id) {
        this.success = success;
        this.message = message;
        this.id = id;
    }

    public AuxDao(boolean success, String message, String data, int id) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.id = id;
    }

    public AuxDao(boolean success, String message, String data, int id, boolean condition) {
        this.success = success;
        this.message = message;
        this.data = data;
        this.id = id;
        this.condition = condition;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public boolean isCondition() {
        return condition;
    }

    public void setCondition(boolean condition) {
        this.condition = condition;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int[] getValues() {
        StringBuilder string = new StringBuilder();
        for(int value : values){
            string.append(value);
        }
        return values;
    }

    public void setValues(int[] values) {
        this.values = values;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    public List<Modification> getMods() {
        return mods;
    }

    public void setMods(List<Modification> mods) {
        this.mods = mods;
    }

    public Resource getResource() {
        return resource;
    }

    public void setResource(Resource resource) {
        this.resource = resource;
    }

    public List<Favorites> getFavs() {
        return favs;
    }

    public void setFavs(List<Favorites> favs) {
        this.favs = favs;
    }

    public List<PicODay> getPics() {
        return pics;
    }

    public void setPics(List<PicODay> pics) {
        this.pics = pics;
    }
}
