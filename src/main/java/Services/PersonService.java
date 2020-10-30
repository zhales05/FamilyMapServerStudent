package Services;

import Services.familycreation.EventGenerator;
import Services.jsonhandler.InfoGeneration;
import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.PersonDao;
import model.AuthToken;
import model.Person;

import java.sql.Connection;

public class PersonService {
    Database db;
    Connection conn;

    /**
     * constructor creates a connection through database, might need to add deconstructor that ends connection.
     */
    public PersonService() {
        db = new Database();
        try {
            conn = db.getConnection();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * get the person with the ID
     * @param personID
     * @return
     */
    public PersonResult getPerson(String personID, String token) {
        AuthTokenDao aDao = new AuthTokenDao(conn);
        PersonDao pDao = new PersonDao(conn);
        AuthToken authToken;
        Person person;
        PersonResult personResult = new PersonResult("error");
        try {

            authToken = aDao.find(token);
            if (authToken == null) {
                personResult = new PersonResult("error invalid authToken");
                db.closeConnection(false);
                return personResult;
            }

            if(!authToken.getPersonID().equals(personID)) {
                    personResult = new PersonResult("error invalid personID");
                    db.closeConnection(false);
                    return personResult;
            }

            person = pDao.find(personID);
            if(person != null) {
                personResult = new PersonResult(person.getAssociatedUsername(), person.getPersonID(),
                        person.getFirstName(), person.getLastName(), person.getGender());
                personResult = checkForIDs(personResult, person);
                db.closeConnection(false);
                return personResult;
            }
            db.closeConnection(false);
            return personResult;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return null;
        }

        PersonResult checkForIDs(PersonResult output, Person input) {
            if(input.getSpouseID() != null) {
                output.setSpouseID(input.getSpouseID());
            }
            if(input.getFatherID() != null) {
                output.setFatherID(input.getFatherID());
            }
            if(input.getMotherID() != null) {
                output.setMotherID(input.getMotherID());
            }
            return output;
        }


    /**
     * gets person with ID help
     * @return
     */
    PersonResult getPerson(String token) {
        return null;
    }
}
