package Services;

import Services.familycreation.EventGenerator;
import Services.familycreation.GenerationGenerator;
import Services.jsonhandler.InfoGeneration;
import dao.*;
import model.AuthToken;
import model.Event;
import model.Person;
import model.User;

import java.sql.Connection;

/**
 * handles registration requests
 */
public class Register {
    Database db;
    Connection conn;
    EventGenerator makeEvents = new EventGenerator();
    InfoGeneration ig = new InfoGeneration();

    /**
     * constructor creates a connection through database, might need to add deconstructor that ends connection.
     */
    public Register() {
        db = new Database();
        try {
            conn = db.getConnection();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }



    /**
     * @param r
     * @return Register Result depending on whether it is successful or not
     */
    public RegisterResult register(RegisterRequest r) {
        UserDao uDao = new UserDao(conn);
        PersonDao pDao = new PersonDao(conn);
        EventDao eDao = new EventDao(conn);
        AuthTokenDao aDao = new AuthTokenDao(conn);
        RegisterResult rr = new RegisterResult("Error");
        Event birth;
        String firstName;
        String lastName;
        String personID;
        Person userPerson;

        firstName = r.getFirstName();
        lastName = r.getLastName();
        personID = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase() + "_" +
                lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();


        User user = new User(r.getUserName(), r.getPassword(), r.getEmail(), firstName, lastName, r.getGender(), personID);
        User test;
        //check if username has already been taken, maybe change to a function
        try {

            userPerson = userToPersonConversion(user);
            birth = makeEvents.generateBirth(userPerson, null);
            birth.setAssociatedUsername(userPerson.getUsername());
            AuthToken token = new AuthToken(userPerson.getPersonID(), "rr" + ig.assignRandomID());


        //committed to database
            test = uDao.find(user.getUserName());
            if (test != null) {
                RegisterResult result = new RegisterResult("error Username already taken by another user");
                db.closeConnection(false);
                return result;
            }

            System.out.println(user.getPersonID());
            uDao.insert(user);
            System.out.println("after user Database Inserts");
            pDao.insert(userPerson);
            System.out.println("after person Inserts");
            eDao.insert(birth);
            System.out.println("Post Database Inserts");
            aDao.insert(token);

            db.closeConnection(true); // this could mess somethings up
            GenerationGenerator create = new GenerationGenerator(4);
            create.startGenerateParents(userPerson, 1);

            rr = new RegisterResult(token.getAuthToken(),userPerson.getAssociatedUsername(), userPerson.getPersonID());

            return rr;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        try {
            db.closeConnection(false);
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        System.out.println("Right before return");
        return rr;
    }
    /*
    public void generateParents(Person originalPerson, int genCount) {
       if (genCount > 4){
            return;
        }
        Event dadBirth;
        Event momBirth;
        EventDao eDao = new EventDao(conn);
        Event[] marriageEvents;
        Event[] eventsArray = new Event[6];
        Person[] personArray = new Person[2];
        Event hubMarriage;
        Event wifeMarriage;
        Event hubDeath;
        Event wifeDeath;


        try {
            Event kidBirth = eDao.find(originalPerson.getPersonID());

            Person dad = new Person(ig.assignRandomID(), originalPerson.getAssociatedUsername(), ig.getRandomMaleFirstName(), ig.getRandomLastName(), "m");
            Person mom = new Person(ig.assignRandomID(), originalPerson.getAssociatedUsername(), ig.getRandomFemaleFirstName(), ig.getRandomLastName(), "f");

            mom.setSpouseId(dad.getPersonID());
            dad.setSpouseId(mom.getPersonID());
            personArray[0] = dad;
            personArray[1] = mom;

            originalPerson.setFatherID(dad.getPersonID());
            originalPerson.setMotherID(mom.getPersonID());

            dadBirth = makeEvents.generateBirth(dad, kidBirth);
            momBirth = makeEvents.generateBirth(mom, kidBirth);

            marriageEvents = makeEvents.generateMarriage(dad, mom, kidBirth, dadBirth, momBirth);
            hubMarriage = marriageEvents[0];
            wifeMarriage = marriageEvents[1];

            hubDeath = makeEvents.generateDeath(dad, hubMarriage, kidBirth, dadBirth);
            wifeDeath = makeEvents.generateDeath(mom, wifeMarriage, kidBirth, momBirth);

            eventsArray[0] = marriageEvents[0];
            eventsArray[1] = marriageEvents[1];
            eventsArray[2] = dadBirth;
            eventsArray[3] = momBirth;
            eventsArray[4] = hubDeath;
            eventsArray[5] = wifeDeath;

            commitChangesToDataBase(personArray, eventsArray, conn);

            genCount++;
            generateParents(dad, genCount);
            generateParents(mom, genCount);

        } catch (DataAccessException e) {
            e.printStackTrace();
        }

    }

    public void commitChangesToDataBase(Person[] pArray, Event[] eArray, Connection conn) {
        PersonDao pDao = new PersonDao(conn);
        EventDao eDao = new EventDao(conn);
        try {
            for (Person p : pArray) {
                pDao.insert(p);
            }
            for (Event e : eArray) {
                eDao.insert(e);
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
    }*/

    public Person userToPersonConversion(User user) {
        Person person = new Person(user.getPersonID(), user.getUserName(), user.getFirstName(), user.getLastName(), user.getGender());
        return person;
    }
}


