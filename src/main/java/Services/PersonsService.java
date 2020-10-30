package Services;

import dao.*;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;

import java.sql.Connection;
import java.util.List;

public class PersonsService {
    Database db;
    Connection conn;

    /**
     * constructor creates a connection through database, might need to add deconstructor that ends connection.
     */
    public PersonsService() {
        db = new Database();
        try {
            conn = db.getConnection();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    public PersonResult getPersons(String token) {
        AuthTokenDao aDao = new AuthTokenDao(conn);
        PersonDao pDao = new PersonDao(conn);
        UserDao uDao = new UserDao(conn);
        Person person;
        List<Person> personList;
        AuthToken authToken;
        PersonResult personResult = new PersonResult("error");

        try {
            authToken = aDao.find(token);

            if (authToken == null) {
                personResult = new PersonResult("error invalid authToken");
                db.closeConnection(false);
                return personResult;
            }

            User user = uDao.findWithPersonID(authToken.getPersonID());
            //eDao.testEntries();
           // eDao.findWithEventID("Davis_Birth");

            personList = pDao.findAllWithUserName(user.getUserName());
            personResult = new PersonResult(personList);
            db.closeConnection(false);
            return personResult;

        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        try {
            db.closeConnection(false);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return personResult;
    }
}
