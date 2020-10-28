package mytests;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.EventDao;
import model.AuthToken;
import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenDaoTest {
    private Database db;
    private AuthToken bestToken;
    private AuthTokenDao aDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        db = new Database();

        bestToken = new AuthToken("Biking_123A", "Gale");
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        aDao = new AuthTokenDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
        db.closeConnection(false);
    }

    @Test
    void insert() {
    }

    @Test
    void delete() throws DataAccessException {
       // aDao.insert(bestToken);
        aDao.delete(bestToken);
      //  assertNull(aDao.find(bestToken.getPersonID()));
    }

    @Test
    void getToken() {
    }

    @Test
    void clear() {
    }
}