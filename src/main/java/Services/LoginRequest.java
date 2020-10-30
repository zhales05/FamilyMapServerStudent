package Services;

public class LoginRequest {

    String userName;
    String password;

    /**
     * constructor that sets variables for Login Request
     * @param userName
     * @param password
     */

    public LoginRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUsername() {
        return userName;
    }

    public void setUsername(String username) {
        this.userName = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
