package Services;

import dao.*;
import model.AuthToken;
import model.Event;
import model.User;

import java.sql.Connection;
import java.util.List;

public class EventsService {

    Database db;
    Connection conn;

    /**
     * constructor creates a connection through database, might need to add deconstructor that ends connection.
     */
    public EventsService() {
        db = new Database();
        try {
            conn = db.getConnection();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }
    public EventResult getEvents(String token) {
        AuthTokenDao aDao = new AuthTokenDao(conn);
        EventDao eDao = new EventDao(conn);
        UserDao uDao = new UserDao(conn);
        Event event;
        List<Event> eventList;
        AuthToken authToken;
        EventResult  eventResult = new EventResult("error");

        try {
            authToken = aDao.find(token);

            if (authToken == null) {
                eventResult = new EventResult("error invalid authToken");
                db.closeConnection(false);
                return eventResult;
            }

            User user = uDao.findWithPersonID(authToken.getPersonID());
            //eDao.testEntries();
           // eDao.findWithEventID("Davis_Birth");

            eventList = eDao.findAllWithUserName(user.getUserName());
             eventResult = new EventResult(eventList);
             db.closeConnection(false);
             return eventResult;

        } catch (DataAccessException e) {
            e.printStackTrace();
        }

        try {
            db.closeConnection(false);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return eventResult;
    }
}
