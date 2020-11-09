package Services;

import dao.DataAccessException;
import dao.Database;
import dao.PersonDao;
import dao.UserDao;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

class RegisterTest {
    private Database db;
    RegisterRequest rr;
    Register r;
    User user;
    UserDao uDao;
    User compareUser;


    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        user = new User("sheila", "parker", "sheila@parker.com",
                "Sheila","Parkers","f", "Sheila_Parkers");
        rr = new RegisterRequest("sheila", "parker", "sheila@parker.com", "Sheila","Parkers","f");
        Connection conn = db.getConnection();
        db.clearTables();
        uDao = new UserDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        db.closeConnection(false);
    }

    @Test
    void register() throws DataAccessException {
        r = new Register();
        db.closeConnection(true);
        r.register(rr);
        db = new Database();
        Connection conn = db.getConnection();
        uDao = new UserDao(conn);
        compareUser = uDao.find(user.getUserName());
        assertEquals(user, compareUser);
    }

  /*  @Test
    void registerFail() throws DataAccessException {
        r = new Register();
        db.closeConnection(true);
        r.register(rr);
        RegisterResult result = r.register(rr);
        RegisterResult test = new RegisterResult("error Username already taken by another user");
        assertEquals(test, result);
        db = new Database();
        Connection conn = db.getConnection();
    }*/


}