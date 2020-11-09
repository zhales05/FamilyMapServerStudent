package Services;

import dao.*;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class ClearTest {
    private Database db;
    PersonDao pDao;
    Person bestPerson;
    Person leastPerson;
    User user;
    AuthToken authToken;
    AuthTokenDao aDao;
    UserDao uDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        bestPerson = new Person("qoitub", "test", "Craig",
                "Laron", "f", "asfsfm", "23qg4", "asfhib4");
        leastPerson = new Person("dsdsv", "test", "notCraig",
                "Laron", "m", "asfsfm", "23qg4", "asfhib4");
        user = new User("test", "parker", "sheila@parker.com",
                "Sheila", "Parkers", "f", "Sheila_Parkers");
        authToken = new AuthToken("Sheila_Parkers", "AuthToken");
        Connection conn = db.getConnection();
        db.clearTables();
        pDao = new PersonDao(conn);
        aDao = new AuthTokenDao(conn);
        uDao = new UserDao(conn);
    }

    @Test
    void clearData() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.insert(leastPerson);
        aDao.insert(authToken);
        uDao.insert(user);
        db.closeConnection(true);
        Clear clear = new Clear();
        ClearResult cr = new ClearResult(true, "Clear succeeded.");
        ClearResult test = clear.clearData();
        assertEquals(cr.getMessage(), test.getMessage());

    }

    @Test
    void clearDataFail() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.insert(leastPerson);
        aDao.insert(authToken);
        uDao.insert(user);

        Clear clear = new Clear();
        ClearResult cr = new ClearResult(false, "database error");
        ClearResult test = clear.clearData();
        assertEquals(cr.getMessage(), test.getMessage());
    }
}