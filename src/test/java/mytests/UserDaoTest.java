package mytests;

import dao.DataAccessException;
import dao.Database;
import dao.UserDao;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class UserDaoTest {
    private Database db;
    private User bestUser;
    private User leastUser;
    private UserDao uDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        bestUser = new User("zhales", "password", "bsu@gmail.com",
                "kyle", "lewis", "m", "23pqtu3igb");
        leastUser = new User("sdfas", "password", "bsu@gmail.com",
                "kyle", "lewis", "m", "asf43f3");
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        uDao = new UserDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        uDao.insert(bestUser);
        User compareTest = uDao.find(bestUser.getPersonId());
        assertNotNull(compareTest);
        assertEquals(bestUser, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        uDao.insert(bestUser);
        assertThrows(DataAccessException.class, ()-> uDao.insert(bestUser));
    }

    @Test
    void findPass() throws DataAccessException {
        uDao.insert(bestUser);
        uDao.insert(leastUser);
        User compareTest = uDao.find(bestUser.getUsername());
        assertEquals(bestUser, compareTest);
    }

    @Test
    void findFail() throws DataAccessException {
        assertNull(uDao.find(leastUser.getUsername()));
    }

    @Test
    void clear() throws DataAccessException {
        uDao.insert(bestUser);
        uDao.insert(leastUser);
        uDao.clear();
        assertNull(uDao.find(leastUser.getPersonId()));
        assertNull(uDao.find(bestUser.getPersonId()));
    }
}
