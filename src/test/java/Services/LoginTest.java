package Services;

import dao.*;
import model.AuthToken;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class LoginTest {
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
    void login() throws DataAccessException {
        uDao.insert(user);
        db.closeConnection(true);

        LoginRequest lr = new LoginRequest("test", "parker");
        Login login = new Login();
        LoginResult result = login.login(lr);
        Connection conn = db.openConnection();
        aDao = new AuthTokenDao(conn);
        authToken = aDao.find(result.getAuthToken());
        LoginResult test = new LoginResult(authToken.getAuthToken(), user.getUserName(), user.getPersonID());
        assertEquals(test.personID,result.personID);
    }

    @Test
    void loginFail() throws DataAccessException {

        db.closeConnection(true);
        LoginRequest lr = new LoginRequest("test", "parker");
        Login login = new Login();
        LoginResult result = login.login(lr);

        LoginResult test = new LoginResult("error no user with this username");
        assertEquals(test.getMessage(),result.getMessage());
    }
}