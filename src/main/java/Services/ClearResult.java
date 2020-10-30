package Services;

public class ClearResult {

    String message;
    boolean success;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public ClearResult(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

}
