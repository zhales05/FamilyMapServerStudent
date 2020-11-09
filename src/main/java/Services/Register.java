package Services;

import Services.familycreation.EventGenerator;
import Services.familycreation.GenerationGenerator;
import Services.jsonhandler.InfoGeneration;
import dao.*;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;

import java.sql.Connection;

/**
 * handles registration requests
 */
public class Register {
    Database db;
    Connection conn;
    EventGenerator makeEvents = new EventGenerator();
    InfoGeneration ig = new InfoGeneration();

    /**
     * constructor creates a connection through database, might need to add deconstructor that ends connection.
     */
    public Register() {
        db = new Database();
        try {
            conn = db.getConnection();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * @param r
     * @return Register Result depending on whether it is successful or not
     */
    public RegisterResult register(RegisterRequest r) {
        UserDao uDao = new UserDao(conn);
        PersonDao pDao = new PersonDao(conn);
        EventDao eDao = new EventDao(conn);
        AuthTokenDao aDao = new AuthTokenDao(conn);
        RegisterResult rr = new RegisterResult("Error");
        Event birth;
        String firstName;
        String lastName;
        String personID;
        Person userPerson;

        firstName = r.getFirstName();
        lastName = r.getLastName();
        personID = firstName.substring(0, 1).toUpperCase() + firstName.substring(1).toLowerCase() + "_" +
                lastName.substring(0, 1).toUpperCase() + lastName.substring(1).toLowerCase();


        User user = new User(r.getUserName(), r.getPassword(), r.getEmail(), firstName, lastName, r.getGender(), personID);
        User test;
        //check if username has already been taken
        try {
            userPerson = userToPersonConversion(user);
            birth = makeEvents.generateBirth(userPerson, null);
            birth.setAssociatedUsername(userPerson.getUsername());
            AuthToken token = new AuthToken(userPerson.getPersonID(), "rr" + ig.assignRandomID());
            test = uDao.find(user.getUserName());
            if (test != null) {
                RegisterResult result = new RegisterResult("error Username already taken by another user");
                db.closeConnection(false);
                return result;
            }

            uDao.insert(user);
            pDao.insert(userPerson);
            eDao.insert(birth);
            aDao.insert(token);

            db.closeConnection(true);
            GenerationGenerator create = new GenerationGenerator(4);
            create.startGenerateParents(userPerson, 1);

            rr = new RegisterResult(token.getAuthToken(), userPerson.getAssociatedUsername(), userPerson.getPersonID());

            return rr;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        try {
            db.closeConnection(false);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        return rr;
    }

    private Person userToPersonConversion(User user) {
        Person person = new Person(user.getPersonID(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getGender());
        return person;
    }
}


