package Services;
/**
 * Class of LoginResult object. Return Success or Failure
 */
public class LoginResult {
    String authToken;
    String userName;
    String personID;
    boolean success;
    String message;

    public LoginResult(String authToken, String userName, String personId) {
        this.authToken = authToken;
        this.userName = userName;
        this.personID = personId;
        success = true;
    }

    public LoginResult(String message) {
        this.message = message;
        success = false;
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

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
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


}
