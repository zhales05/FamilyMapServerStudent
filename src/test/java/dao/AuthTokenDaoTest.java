package dao;

import model.AuthToken;
import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class AuthTokenDaoTest {
    private Database db;
    private AuthToken bestAuthToken;
    private AuthToken leastAuthToken;
    private AuthTokenDao aDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        db = new Database();
        bestAuthToken = new AuthToken("32g", "afser");
        leastAuthToken = new AuthToken("f43ouyn4whoir", "wger");
        Connection conn = db.getConnection();
        db.clearTables();
        aDao = new AuthTokenDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        aDao.insert(bestAuthToken);
        AuthToken compareTest = aDao.find(bestAuthToken.getAuthToken());
        assertNotNull(compareTest);
        assertEquals(bestAuthToken, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        aDao.insert(bestAuthToken);
        assertThrows(DataAccessException.class, ()-> aDao.insert(bestAuthToken));
    }

    @Test
    void findPass() throws DataAccessException {
        aDao.insert(bestAuthToken);
        aDao.insert(leastAuthToken);
        AuthToken compareTest = aDao.find(bestAuthToken.getAuthToken());
        assertEquals(bestAuthToken, compareTest);
    }

    @Test
    void findFail() throws DataAccessException {
        assertNull(aDao.find(bestAuthToken.getAuthToken()));
    }

    @Test
    void delete() {
    }

    @Test
    void clear() {
    }
}