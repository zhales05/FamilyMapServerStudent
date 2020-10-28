package Services;

import dao.DataAccessException;
import dao.Database;
import dao.EventDao;
import dao.UserDao;
import model.Event;
import model.Person;
import model.User;

import java.sql.Connection;
import java.util.UUID;

/**
 * handles registration requests
 */
public class Register {
    Database db;
    Connection conn;

    /**
     * constructor creates a connection through database, might need to add deconstructor that ends connection.
     */
    public Register(){
        db = new Database();
        try {
            conn = db.getConnection();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    /**
     *
     * @param r
     * @return Register Result depending on whether it is successful or not
     */
    public RegisterResult register(RegisterRequest r) {
        UserDao uDao = new UserDao(conn);
        Person userPerson;
        User user = new User(assignRandomName(),assignRandomName(),r.email,r.firstName,r.lastName,r.gender,assignRandomName());
        User test;
        //check if username has already been taken, maybe change to a function
        try {
            test = uDao.find(user.getUsername());
            if (test == null){
                RegisterResult result = new RegisterResult("Username already taken by another user");
                return result;
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        userPerson = userToPersonConversion(user);


        return null;
    }

    public void generateEvents(Person person) {
        Event event;
        EventDao eDao = new EventDao(conn);


    }

    public Person userToPersonConversion(User user) {
        Person person = new Person(user.getPersonId(),user.getUsername(), user.getFirstName(), user.getLastName(), user.getGender());
        return person;
    }
    public String assignRandomName() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }
}


