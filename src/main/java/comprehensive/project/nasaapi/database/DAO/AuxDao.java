package comprehensive.project.nasaapi.database.DAO;

public class AuxDao {
    private boolean success;
    private String message;
    private String data;
    private boolean condition;
    private int id;
    private int[] values;

    public AuxDao() {
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
}
