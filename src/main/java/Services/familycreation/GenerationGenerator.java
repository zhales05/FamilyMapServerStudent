package Services.familycreation;

import Services.jsonhandler.InfoGeneration;
import dao.DataAccessException;
import dao.Database;
import dao.EventDao;
import dao.PersonDao;
import model.Event;
import model.Person;

import java.sql.Connection;

public class GenerationGenerator {

    Database db;
    Connection conn;
    EventGenerator makeEvents = new EventGenerator();
    InfoGeneration ig = new InfoGeneration();
    int totalGens;

    /**
     * constructor creates a connection through database, might need to add deconstructor that ends connection.
     */
    public GenerationGenerator(int wantedGens) {
        db = new Database();
        try {
            conn = db.getConnection();
        } catch (DataAccessException e) {
            e.printStackTrace();
        }
        totalGens = wantedGens;
    }
    public void startGenerateParents(Person originalPerson, int genCount){
            //PersonDao pDao = new PersonDao(conn);

        try {
            generateParents(originalPerson,genCount);
            db.closeConnection(true);
            return;
        } catch (DataAccessException e) {
            e.printStackTrace();
        }

    }
    public void generateParents(Person originalPerson, int genCount) {
        if (genCount > totalGens){
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
            Event kidBirth = eDao.findBirth(originalPerson.getPersonID());

            String firstName = ig.getRandomMaleFirstName();
            String lastName = ig.getRandomLastName();
            String personID = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase() + "_" +
                    lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();
            Person dad = new Person(personID, originalPerson.getAssociatedUsername(), firstName, lastName, "m");
            firstName = ig.getRandomFemaleFirstName();
             personID = firstName.substring(0,1).toUpperCase() + firstName.substring(1).toLowerCase() + "_" +
                    lastName.substring(0,1).toUpperCase() + lastName.substring(1).toLowerCase();
            Person mom = new Person(personID, originalPerson.getAssociatedUsername(), firstName, lastName, "f");

            mom.setSpouseId(dad.getPersonID());
            dad.setSpouseId(mom.getPersonID());
            personArray[0] = dad;
            personArray[1] = mom;

            originalPerson.setFatherID(dad.getPersonID());
            originalPerson.setMotherID(mom.getPersonID());

            PersonDao pDao = new PersonDao(conn);
            pDao.updateFatherID(dad.getPersonID(), originalPerson.getPersonID());
            pDao.updateMotherID(mom.getPersonID(), originalPerson.getPersonID());

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
    }

}
