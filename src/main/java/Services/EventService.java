package Services;

import dao.*;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;

import java.sql.Connection;

public class EventService {

    Database db;
    Connection conn;

    /**
     * constructor creates a connection through database, might need to add deconstructor that ends connection.
     */
    public EventService() {
        db = new Database();
        try {
            conn = db.getConnection();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }

    /**
     * get the person with the ID
     * @param eventID
     * @return
     */
    public EventResult getEvent(String eventID, String token) {
        AuthTokenDao aDao = new AuthTokenDao(conn);
        EventDao eDao = new EventDao(conn);
        UserDao uDao = new UserDao(conn);
        AuthToken authToken;
        Event event;
        EventResult  eventResult = new EventResult("error");
        try {

            authToken = aDao.find(token);
            if (authToken == null) {
                eventResult = new EventResult("error invalid authToken");
                db.closeConnection(false);
                return eventResult;
            }

            event = eDao.findWithEventID(eventID);
            if (event == null){
                eventResult = new EventResult("error invalid eventID");
                return eventResult;
            }
            User user = uDao.findWithPersonID(authToken.getPersonID());
            if(!event.getPersonID().equals(user.getPersonID())) {//does event match user
                eventResult = new EventResult("error invalid eventID for this user");
                db.closeConnection(false);
                return eventResult;
            }

            event = eDao.findWithEventID(eventID);
            if(event != null) {
                eventResult = new EventResult(event.getEventID(), event.getUsername(), event.getPersonID(),
                        event.getLatitude(), event.getLongitude(), event.getCountry(), event.getCity(), event.getEventType(), event.getYear());
                db.closeConnection(false);
                return eventResult;
            }
            db.closeConnection(false);

        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        return eventResult;
    }

    /**
     * gets person with ID help
     * @return
     */
    EventResult getEvent(AuthToken verify) {
        return null;
    }
}
