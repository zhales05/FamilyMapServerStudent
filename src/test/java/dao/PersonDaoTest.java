package dao;

import dao.DataAccessException;
import dao.Database;
import dao.PersonDao;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonDaoTest {
    private Database db;
    private Person bestPerson;
    private Person leastPerson;
    private PersonDao pDao;

    @BeforeEach
    public void setUp() throws DataAccessException {
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
    void findAllWithUserNameSuccess() throws DataAccessException {
        pDao.insert(bestPerson);
        leastPerson.setUsername(bestPerson.getUsername());
        pDao.insert(leastPerson);
        List<Person> personList = pDao.findAllWithUserName(bestPerson.getUsername());
        List<Person> myPersonList = new ArrayList<>();
        myPersonList.add(bestPerson);
        myPersonList.add(leastPerson);
        assertEquals(personList, myPersonList);
        leastPerson.setUsername("test");
    }

    @Test
    void findAllWithUserNameFail() throws DataAccessException {
        pDao.insert(leastPerson);
        List<Person> personList = pDao.findAllWithUserName(bestPerson.getUsername());
        List<Person> myPersonList = new ArrayList<>();
        assertEquals(personList,myPersonList);
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
        assertThrows(DataAccessException.class, () -> pDao.insert(bestPerson));
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
    void deletePass() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.delete(bestPerson.getPersonID());
        assertNull(pDao.find(bestPerson.getPersonID()));
    }

    @Test
    void deletePass2() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.delete(leastPerson.getPersonID());
        assertEquals(pDao.find(bestPerson.getPersonID()), bestPerson);
    }

    @Test
    void deleteUserNamePass() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.deleteWithUserName(bestPerson.getUsername());
        assertNull(pDao.find(bestPerson.getPersonID()));
    }

    @Test
    void deleteUsernamePass2() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.deleteWithUserName(leastPerson.getAssociatedUsername());
        assertEquals(pDao.find(bestPerson.getPersonID()), bestPerson);
    }

    @Test
    void fatherIDSuccess() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.updateFatherID(leastPerson.getFatherID(), bestPerson.getPersonID());
        assertEquals(pDao.find(bestPerson.getPersonID()).getFatherID(), leastPerson.getFatherID());
    }

    @Test
    void fatherIDFail() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.updateFatherID("152", leastPerson.getPersonID());
        assertEquals(pDao.find(bestPerson.getPersonID()).getFatherID(), bestPerson.getFatherID());
    }

    @Test
    void motherIDSuccess() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.updateMotherID(leastPerson.getMotherID(), bestPerson.getMotherID());
        assertEquals(pDao.find(bestPerson.getPersonID()).getMotherID(), leastPerson.getMotherID());
    }

    @Test
    void motherIDFail() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.updateMotherID("152", leastPerson.getPersonID());
        assertEquals(pDao.find(bestPerson.getPersonID()).getMotherID(), bestPerson.getMotherID());
    }



}