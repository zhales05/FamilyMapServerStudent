package Services.familycreation;

import Services.jsonhandler.InfoGeneration;
import Services.jsonhandler.Location;
import Services.jsonhandler.LocationCollection;
import dao.DataAccessException;
import dao.EventDao;
import model.Event;
import model.Person;

import java.io.IOException;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

public class EventGenerator {

    public Event generateBirth(Person person, Event kidsBirth) {
        Event event;
        int year = 0;
        InfoGeneration ig = new InfoGeneration();

        Location location = ig.getRandomLocation();
        if(person.getMotherID() == null && person.getFatherID() == null && person.getSpouseID() == null && kidsBirth == null) {
            year = getRandomNumber(2005, 1990);
        }

        else if (kidsBirth != null) {
            year = getRandomNumber(kidsBirth.getYear() - 18, (kidsBirth.getYear() - 45));
        }

        event = new Event(assignRandomName(), person.getAssociatedUsername(), person.getPersonID(), location.getLatitude(), location.getLongitude(), location.getCountry(),
                location.getCity(), "birth", year);
        return event;
    }

    public Event[] generateMarriage(Person husband, Person wife, Event kidsBirth, Event hubBirth, Event wifeBirth) {
        int year = 0;
        Event[] returnArray = new Event[2];
        InfoGeneration ig = new InfoGeneration();
        Location location = ig.getRandomLocation();
        if (kidsBirth == null) {
            year = Math.max(hubBirth.getYear(), wifeBirth.getYear());
            year = getRandomNumber(year + 35, year + 15);
        } else{
            year = kidsBirth.getYear();
            int birth = Math.max(hubBirth.getYear(), wifeBirth.getYear());
            year = getRandomNumber(year-1, birth + 13);
        }
        Event hub = new Event(assignRandomName(),husband.getAssociatedUsername(), husband.getPersonID(), location.getLatitude(), location.getLongitude(), location.getCountry(),
                location.getCity(), "marriage", year);
        Event wifeE = new Event(assignRandomName(),wife.getAssociatedUsername(), wife.getPersonID(), location.getLatitude(), location.getLongitude(), location.getCountry(),
                location.getCity(), "marriage", year);

        returnArray[0] = hub;
        returnArray[1] = wifeE;
        return returnArray;
    }

    public Event generateDeath(Person person, Event marriage, Event kidBirth, Event birth) {
        InfoGeneration ig = new InfoGeneration();
        Location location = ig.getRandomLocation();
        int year= 0;
        if (kidBirth != null) {
            year = kidBirth.getYear();
            year = getRandomNumber(year + 50, year);
        }
        else if(marriage != null) {
            year = marriage.getYear();
            year = getRandomNumber(year + 50, year);
        } else {
            year = birth.getYear();
            year = getRandomNumber(year + 100, year);
        }

        Event event = new Event(assignRandomName(), person.getAssociatedUsername(), person.getPersonID(), location.getLatitude(), location.getLongitude(), location.getCountry(),
                location.getCity(), "death", year);

        return event;
    }

    public String assignRandomName() {
        String uuid = UUID.randomUUID().toString();
        return uuid;
    }

    public int getRandomNumber(int maxRange,int minRange) {
        Random random = new Random();
        int index = random.nextInt(maxRange - minRange) + minRange;
        return index;
    }

}

