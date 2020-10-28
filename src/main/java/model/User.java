package model;

/**
 * class in charge of handling users
 */
public class User {
    String username;
    String password;
    String email;
    String firstName;
    String lastName;
    String gender;
    String personId;

    public User(String username, String password, String email, String firstName, String lastName, String gender, String personId) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.gender = gender;
        this.personId = personId;
    }



    /**
     * getter for username
     * @return userName Unique user name (non-empty string)
     */

    public String getUsername() {
        return username;
    }
    /**
     * setter for username
     * @param username Unique user name (non-empty string)
     */

    public void setUsername(String username) {
        this.username = username;
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
    public String getPersonId() {
        return personId;
    }

    /**
     * setter for personId
     * @param personId
     */
    public void setPersonId(String personId) {
        this.personId = personId;
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
            return  oUser.getUsername().equals(getUsername()) &&
                    oUser.getPassword().equals(getPassword()) &&
                    oUser.getEmail().equals(getEmail()) &&
                    oUser.getFirstName().equals(getFirstName()) &&
                    oUser.getLastName().equals(getLastName()) &&
                    oUser.getGender().equals(getGender()) &&
                    oUser.getPersonId().equals(getPersonId());
        } else {
            return false;
        }
    }
}
