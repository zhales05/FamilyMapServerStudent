package dao;

import dao.DataAccessException;
import dao.Database;
import dao.EventDao;
import model.Event;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class EventDaoTest {
    private Database db;
    private Event bestEvent;
    private Event leastEvent;
    private EventDao eDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        db = new Database();
        bestEvent = new Event("Biking_123A", "Gale", "Gale123A",
                35.9f, 140.1f, "Japan", "Ushiku",
                "Biking_Around", 2016);
        leastEvent = new Event("23rq3ger", "Gore", "goree",
                34f, 14f, "USA", "Driggs",
                "birth", 2022);
        Connection conn = db.getConnection();
        db.clearTables();
        eDao = new EventDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        eDao.insert(bestEvent);
        Event compareTest = eDao.find(bestEvent.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestEvent, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {
        eDao.insert(bestEvent);
        assertThrows(DataAccessException.class, ()-> eDao.insert(bestEvent));
    }

    @Test
    void findPass() throws DataAccessException {
        eDao.insert(bestEvent);
        eDao.insert(leastEvent);
        Event compareTest = eDao.find(bestEvent.getPersonID());
        assertEquals(bestEvent, compareTest);
    }

    @Test
    void findFail() throws DataAccessException {
        assertNull(eDao.find(leastEvent.getPersonID()));
    }

    @Test
    void findBirthPass() throws DataAccessException {
        eDao.insert(leastEvent);
        Event compareTest = eDao.find(leastEvent.getPersonID());
        assertEquals("birth", compareTest.getEventType());
    }

    @Test
    void findBirthFail() throws DataAccessException {
        eDao.insert(bestEvent);
        Event compareTest = eDao.find(bestEvent.getPersonID());
        assertNull(eDao.find(leastEvent.getPersonID()));
    }

    @Test
    void findUNPass() throws DataAccessException {
        List<Event> eventList = new ArrayList<>();
        eventList.add(bestEvent);
        eventList.add(leastEvent);
        bestEvent.setAssociatedUsername(leastEvent.getUsername());
        eDao.insert(bestEvent);
        eDao.insert(leastEvent);
        List<Event> compareTest = eDao.findAllWithUserName(leastEvent.getUsername());
        assertEquals(eventList, compareTest);
    }

    @Test
    void findUNFail() throws DataAccessException {
        List<Event> compareTest = eDao.findAllWithUserName(leastEvent.getUsername());
        List <Event> empty = new ArrayList<>();
        assertEquals(compareTest, empty);
    }

    @Test
    void findEIPass() throws DataAccessException {
        eDao.insert(bestEvent);
        eDao.insert(leastEvent);
        Event compareTest = eDao.findWithEventID(bestEvent.getEventID());
        assertEquals(bestEvent, compareTest);
    }

    @Test
    void findEIFail() throws DataAccessException {
        assertNull(eDao.findWithEventID(leastEvent.getEventID()));
    }

    @Test
    void clear() throws DataAccessException {
        eDao.insert(bestEvent);
        eDao.insert(leastEvent);
        eDao.clear();
        assertNull(eDao.find(leastEvent.getPersonID()));
        assertNull(eDao.find(bestEvent.getPersonID()));
    }

    @Test
    void deleteWithUserName() throws DataAccessException {
        eDao.insert(leastEvent);
        eDao.deleteWithUserName(leastEvent.getUsername());
        assertNull(eDao.find(leastEvent.getPersonID()));
    }

    @Test
    void deleteWithUserNameFail() throws DataAccessException {
        eDao.insert(leastEvent);
        eDao.deleteWithUserName(bestEvent.getUsername());
        assertNotNull(eDao.find(leastEvent.getPersonID()));
    }

}
