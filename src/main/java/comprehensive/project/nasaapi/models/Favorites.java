package comprehensive.project.nasaapi.models;

public class Favorites {
    private int userId;
    private String resourcesId;
    private String note;

    public Favorites(int userId, String resourcesId, String note) {
        this.userId = userId;
        this.resourcesId = resourcesId;
        this.note = note;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getResourcesId() {
        return resourcesId;
    }

    public void setResourcesId(String resourcesId) {
        this.resourcesId = resourcesId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }
}
