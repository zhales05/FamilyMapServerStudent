package model;

/**
 * AuthToken class. Handles the authorization of Users
 */
public class AuthToken {

    String personID;
    String authToken;

    public AuthToken(String personID, String authToken) {
        this.personID = personID;
        this.authToken = authToken;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof AuthToken) {
            AuthToken authToken = (AuthToken) o;
            return authToken.getPersonID().equals(getPersonID()) &&
                    authToken.getAuthToken().equals(getAuthToken());
        } else {
            return false;
        }
    }
    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public String getAuthToken() {
        return authToken;
    }

    public void setAuthToken(String authToken) {
        this.authToken = authToken;
    }

}
