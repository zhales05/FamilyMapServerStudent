package Services;

import dao.*;
import model.AuthToken;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PersonsServiceTest {
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
                "Sheila","Parkers","f", "Sheila_Parkers");
        authToken = new AuthToken("Sheila_Parkers", "AuthToken");
        Connection conn = db.getConnection();
        db.clearTables();
        pDao = new PersonDao(conn);
        aDao = new AuthTokenDao(conn);
        uDao = new UserDao(conn);
    }

    @Test
    void getPersons() throws DataAccessException {
        pDao.insert(bestPerson);
        pDao.insert(leastPerson);
        aDao.insert(authToken);
        uDao.insert(user);

        List<Person> personList = new ArrayList<>();
        personList.add(bestPerson);
        personList.add(leastPerson);

        PersonsService ps = new PersonsService();
        db.closeConnection(true);

        PersonResult result = ps.getPersons(authToken.getAuthToken());
        assertEquals(personList, result.data);
    }

    @Test
    void getPersonsFail() throws DataAccessException {

        aDao.insert(authToken);
        uDao.insert(user);

        List<Person> personList = new ArrayList<>();
        personList.add(bestPerson);
        personList.add(leastPerson);

        PersonsService ps = new PersonsService();
        db.closeConnection(true);

        PersonResult result = ps.getPersons(authToken.getAuthToken());
        assertNotEquals(personList, result.data);
    }
}