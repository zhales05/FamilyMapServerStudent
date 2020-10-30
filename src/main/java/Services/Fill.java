package Services;

import Services.familycreation.EventGenerator;
import Services.familycreation.GenerationGenerator;
import Services.jsonhandler.InfoGeneration;
import dao.*;
import model.Event;
import model.Person;
import model.User;

import java.sql.Connection;

/**
 * Populates the server's database with generated data for the specified user name
 */
public class Fill {
    Database db;
    Connection conn;
    /**
     * constructor creates a connection through database, might need to add deconstructor that ends connection.
     */
    public Fill() {
        db = new Database();
        try {
            conn = db.getConnection();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * populate the server database with data from the request class
     * @param request;
     * @return
     */
    public FillResult fill(FillRequest request) {
        UserDao uDao = new UserDao(conn);
        PersonDao pDao = new PersonDao(conn);
        EventDao eventDao = new EventDao(conn);
        EventGenerator makeEvents = new EventGenerator();
        FillResult fillResult = new FillResult(false,"Error");

        try {
            User user = uDao.find(request.username);
            if (user == null) {
                fillResult = new FillResult(false, "error invalid user");
                db.closeConnection(false);
                return fillResult;
            }

            pDao.deleteWithUserName(request.username);
            eventDao.deleteWithUserName(request.username);

            Person person = userToPersonConversion(user);
            Event birth = makeEvents.generateBirth(person, null);

            pDao.insert(person);
            eventDao.insert(birth);

            GenerationGenerator create = new GenerationGenerator(request.generations);
            create.startGenerateParents(person, 0);

            //int totalPersons = (int) (Math.pow(2, request.generations) + 1);
           // int totalEvents = ((totalPersons-1) * 3) + 1;

            int i = (int)Math.pow(2.0D, (request.generations + 1)) - 1;
            int j = i * 2;

            fillResult = new FillResult(true, "Successfully added " + i +
                     " persons and " + j + " events to the database.");
            db.closeConnection(true);
            return fillResult;

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        try {
            db.closeConnection(false);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return fillResult;
    }

    public Person userToPersonConversion(User user) {
        Person person = new Person(user.getPersonID(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getGender());
        return person;
    }
}
