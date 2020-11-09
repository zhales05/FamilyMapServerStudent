package Services;

import dao.*;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class FillTest {
    private Database db;
    PersonDao pDao;
    Person bestPerson;
    Person leastPerson;
    User user;
    Event event;
    AuthToken authToken;
    AuthTokenDao aDao;
    UserDao uDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();
        db.clearTables();
        user = new User("sheila", "parker", "sheila@parker.com",
                "Sheila","Parkers","f", "Sheila_Parkers");
        uDao = new UserDao(conn);
    }
    @Test
    void fill() throws DataAccessException {
        uDao.insert(user);
        db.closeConnection(true);
        FillRequest request = new FillRequest("/fill/sheila/4");
        Fill fill = new Fill();
        FillResult result = fill.fill(request);
        FillResult testResult = new FillResult(true, "Successfully added 31 persons and 62 events to the database.");
        assertEquals(testResult.getMessage(), result.getMessage());
    }

}