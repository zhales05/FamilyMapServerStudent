package Services;

/**
 * Class of registerResult object. Return Success or Failure
 */
public class RegisterResult {

    String authToken;
    String userName;
    String personID;
    String message;
    boolean success;

    public RegisterResult(String authToken, String userName, String personId) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personId;
        this.success = true;
    }

    public RegisterResult(String message) {
        this.message = message;
        this.success = false;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPersonId() {
        return personID;
    }

    public void setPersonId(String personId) {
        this.personID = personId;
    }

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
}
