package Services;

/**
 * Class of registerResult object. Return Success or Failure
 */
public class RegisterResult {

    String AuthToken;
    String userName;
    String personId;
    String message;
    boolean success;

    public RegisterResult(String authToken, String userName, String personId) {
        AuthToken = authToken;
        this.userName = userName;
        this.personId = personId;
        this.success = true;
    }

    public RegisterResult(String message) {
        this.message = message;
        this.success = false;
        //getFail();
    }

    /**
     *
     * @return success object
     */
    public RegisterResult getSuccess() {
        return null;
    }

    /**
     *
     * @return fail object
     */
    public RegisterResult getFail() {
        return null;
    }
}
