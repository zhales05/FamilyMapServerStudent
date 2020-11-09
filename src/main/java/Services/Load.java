package Services;

import Services.familycreation.EventGenerator;
import Services.jsonhandler.InfoGeneration;
import dao.*;
import model.Event;
import model.Person;
import model.User;

import java.sql.Connection;

/**
 * clears database and then loads data onto database
 */
public class Load {
    Database db;
    Connection conn;

    /**
     * constructor creates a connection through database, might need to add deconstructor that ends connection.
     */
    public Load() {
        db = new Database();
        try {
            conn = db.getConnection();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * wipe database and reload with new info
     *
     * @param lr
     * @return whether operation was successful
     */
    public LoadResult clearAndLoadData(LoadRequest lr) {
        LoadResult loadResult = new LoadResult(false, "Error");

        try {
            db.clearTables();
            populateClasses(lr);
            //Successfully added X users, Y persons, and Z events to the database.”
            loadResult = new LoadResult(true, "Successfully added " + lr.getUsers().size() +
                    " users, " + lr.getPersons().size() + " persons, and " + lr.getEvents().size() + " events to the database.");
            db.closeConnection(true);
            return loadResult;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        try {
            db.closeConnection(false);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return loadResult;
    }

    private void populateClasses(LoadRequest lr) {
        UserDao uDao = new UserDao(conn);
        PersonDao pDao = new PersonDao(conn);
        EventDao eDao = new EventDao(conn);
        try {
            for (User user : lr.getUsers()) {
                uDao.insert(user);
            }
            for (Person person : lr.getPersons()) {
                pDao.insert(person);
            }
            for (Event event : lr.getEvents()) {
                eDao.insert(event);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
}
