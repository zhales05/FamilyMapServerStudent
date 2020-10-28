package Services;
/**
 * Class of LoginResult object. Return Success or Failure
 */
public class LoginResult {
    String AuthToken;
    String userName;
    String personId;
    boolean success;

    public LoginResult(String authToken, String userName, String personId, boolean success) {
        AuthToken = authToken;
        this.userName = userName;
        this.personId = personId;
        this.success = success;
    }

    /**
     *
     * @return success object
     */
    public LoginResult getSuccess() {
        return null;
    }

    /**
     *
     * @return fail object
     */
    public LoginResult getFail() {
        return null;
    }
}
