package comprehensive.project.nasaapi.database.DAO;

public class AuxDao {
    private boolean success;
    private String message;
    private String data;
    private boolean condition;

    public AuxDao() {
    }

    public AuxDao(boolean success, String message) {
        this.success = success;
        this.message = message;
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
}
