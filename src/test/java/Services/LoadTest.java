package Services;

import dao.*;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LoadTest {

    private Database db;
    PersonDao pDao;
    Person bestPerson;
    Person leastPerson;
    User user;
    Event event;
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
        event = new Event("ewafwe","tst", "34q g", 4.5f, 5.6f, "China", "Berlin",
                "Marriage", 1997);
        Connection conn = db.getConnection();
        db.clearTables();
    }

    @Test
    void clearAndLoadData() throws DataAccessException {
        List<User> users = new ArrayList<>();
        List<Person> persons = new ArrayList<>();;
        List<Event> events = new ArrayList<>();;

        users.add(user);
        persons.add(bestPerson);
        persons.add(leastPerson);
        events.add(event);
        db.closeConnection(false);
        LoadRequest request = new LoadRequest(users,persons, events);
        Load load = new Load();
        LoadResult result = load.clearAndLoadData(request);
        LoadResult loadResult = new LoadResult(true, "Successfully added 1 users, 2 persons, and 1 events to the database.");
        assertEquals(loadResult.getMessage(), result.getMessage());
    }

}