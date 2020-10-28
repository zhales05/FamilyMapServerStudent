package mytests;

import dao.DataAccessException;
import dao.Database;
import dao.PersonDao;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class PersonDaoTest {
    private Database db;
    private Person bestPerson;
    private Person leastPerson;
    private PersonDao pDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        db = new Database();
        bestPerson = new Person("qoitub", "zhadfgs", "Craig",
                "Laron", "f", "asfsfm", "23qg4", "asfhib4");
        leastPerson = new Person("34gfddsf", "test", "notCraig",
                "Laron", "m", "asfsfm", "23qg4", "asfhib4");
        Connection conn = db.getConnection();
        db.clearTables();
        pDao = new PersonDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }
    @Test
    void insertPass() throws DataAccessException {
        pDao.insert(bestPerson);
        Person compareTest = pDao.find(bestPerson.getPersonID());
        assertNotNull(compareTest);
        assertEquals(bestPerson, compareTest);
    }
    @Test
    public void insertFail() throws DataAccessException {
        pDao.insert(bestPerson);
        assertThrows(DataAccessException.class, ()-> pDao.insert(bestPerson));
    }
    @Test
    void findPass() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.insert(leastPerson);
        Person compareTest = pDao.find(bestPerson.getPersonID());
        assertEquals(bestPerson, compareTest);
    }

    @Test
    void findFail() throws DataAccessException {
        assertNull(pDao.find(leastPerson.getPersonID()));
    }

    @Test
    void clear() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.insert(leastPerson);
        pDao.clear();
        assertNull(pDao.find(leastPerson.getPersonID()));
        assertNull(pDao.find(bestPerson.getPersonID()));
    }

    @Test
    void delete() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.delete(bestPerson.getPersonID());
        assertNull(pDao.find(bestPerson.getPersonID()));
    }

}