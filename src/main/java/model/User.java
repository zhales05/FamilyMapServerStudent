package model;

/**
 * class in charge of handling users
 */
public class User {
    String userName;
    String password;
    String email;
    String firstName;
    String lastName;
    String gender;
    String personID;

    public User(String username, String password, String email, String firstName, String lastName, String gender, String personID) {
        this.userName = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personID = personID;
    }



    /**
     * getter for username
     * @return userName Unique user name (non-empty string)
     */

    public String getUserName() {
        return userName;
    }
    /**
     * setter for username
     * @param username Unique user name (non-empty string)
     */

    public void setUserName(String username) {
        this.userName = username;
    }

    /**
     * getter for password
     * @return password User’s password (non-empty string)
     */
    public String getPassword() {
        return password;
    }

    /**
     * setter for password
     * @param password User’s password (non-empty string)
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * getter for email
     * @return email User’s email address (non-empty string)
     */
    public String getEmail() {
        return email;
    }

    /**
     * setter for email
     * @param email
     */

    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * getter for firstName
     * @return firstName User’s first name (non-empty string)
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * setter for firstName
     * @param firstName
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * getter for lastName
     * @return lastName User’s last name (non-empty string)
     */
    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * getter for gender
     * @return gender string f or m
     */
    public String getGender() {
        return gender;
    }

    /**
     * setter for gender
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * getter for personId
     * @return personId Unique Person ID assigned to this user’s generated Person object - see Family History Information section for details (non-empty string)
     */
    public String getPersonID() {
        return personID;
    }

    /**
     * setter for personId
     * @param personID
     */
    public void setPersonID(String personID) {
        this.personID = personID;
    }

    /**
     * equals
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof User) {
            User oUser = (User) o;
            return  oUser.getUserName().equals(getUserName()) &&
                    oUser.getPassword().equals(getPassword()) &&
                    oUser.getEmail().equals(getEmail()) &&
                    oUser.getFirstName().equals(getFirstName()) &&
                    oUser.getLastName().equals(getLastName()) &&
                    oUser.getGender().equals(getGender()) &&
                    oUser.getPersonID().equals(getPersonID());
        } else {
            return false;
        }
    }
}
