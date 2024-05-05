package comprehensive.project.nasaapi.models;

public class Privilege
{
    private int userId;
    private String name;
    private int value;

    public Privilege() {
    }

    public Privilege(int userId, String name, int value) {
        this.userId = userId;
        this.name = name;
        this.value = value;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
