package comprehensive.project.nasaapi.models;

public class Modification {
    private int userId;
    private String timeOfMod;
    private String viewOfMod;
    private String action;

    public Modification(int userId, String timeOfMod, String viewOfMod, String action) {
        this.userId = userId;
        this.timeOfMod = timeOfMod;
        this.viewOfMod = viewOfMod;
        this.action = action;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTimeOfMod() {
        return timeOfMod;
    }

    public void setTimeOfMod(String timeOfMod) {
        this.timeOfMod = timeOfMod;
    }

    public String getViewOfMod() {
        return viewOfMod;
    }

    public void setViewOfMod(String viewOfMod) {
        this.viewOfMod = viewOfMod;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
