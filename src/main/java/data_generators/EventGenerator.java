package data_generators;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import model.Event;
import model.Person;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class EventGenerator {
    private Random randomizer = new Random();
    private String userName;
    private ArrayList<Event> eventArrayList;
    private enum EventTypes {
        Birth,
        Marriage,
        Death;

        public static EventTypes getRandomType() {
            Random random = new Random();
            return values()[random.nextInt(values().length)];
        }
    }

    public EventGenerator(String usernameInput)
    {
        userName = usernameInput;
        eventArrayList = new ArrayList<Event>();
    }

    public ArrayList<Event> getEventArrayList()
    {
        return eventArrayList;
    }

    //-----FUNCTION TO GENERATE RANDOM EVENTS-----//
    public void makeRandomEvent(Person currPerson, int currentYear) {
        int yearsUntilLifeHappens = 10;
        int yearOfEvent = currentYear + yearsUntilLifeHappens + randomizer.nextInt(20);

        Event randomEvent = generateLocationOfEvent();
        randomEvent.setYear(yearOfEvent);
        randomEvent.setEventType(EventTypes.getRandomType().toString());
        randomEvent.setAssociatedUsername(userName);
        randomEvent.setPersonID(currPerson.getPersonID());

        eventArrayList.add(randomEvent);
    }

    //-----FUNCTIONS TO GENERATE SPECIFIC EVENTS-----//
    public void birthEvent(Person currentP, int currentYear) {
        Event birthEvent;

        int birthYear = 0;
        birthYear = currentYear - randomizer.nextInt(10);

        birthEvent = generateLocationOfEvent();
        birthEvent.setPersonID(currentP.getPersonID());
        birthEvent.setEventType("Birth");
        birthEvent.setAssociatedUsername(currentP.getAssociatedUsername());
        birthEvent.setYear(birthYear);

        eventArrayList.add(birthEvent);
    }

    public void marriageEvent(Person groom, Person bride, int currentYear) {
        Event marriageEvent;
        int marriageYear = 0;
        marriageYear = currentYear + randomizer.nextInt(6) + 20;

        marriageEvent = generateLocationOfEvent();
        marriageEvent.setAssociatedUsername(userName);
        marriageEvent.setEventType("Marriage");
        marriageEvent.setYear(marriageYear);
        marriageEvent.setPersonID(groom.getPersonID());

        Event marriageEventComplete = new Event();
        marriageEventComplete.setPersonID(bride.getPersonID());
        marriageEventComplete.setEventType("Marriage");
        marriageEventComplete.setAssociatedUsername(userName);
        marriageEventComplete.setYear(marriageYear);
        marriageEventComplete.setLongitude(marriageEvent.getLongitude());
        marriageEventComplete.setLatitude(marriageEvent.getLatitude());
        marriageEventComplete.setCity(marriageEvent.getCity());
        marriageEventComplete.setCountry(marriageEvent.getCountry());

        eventArrayList.add(marriageEvent);
        eventArrayList.add(marriageEventComplete);
    }

    public void deathEvent(Person currentP, int currentYear) {
        Event deathEvent;
        int deathYear = 0;
        int average = 30;

        deathYear = average + currentYear + randomizer.nextInt(50);

        if (deathYear > 2021) {
            deathYear = 2021;
        }
        deathEvent = generateLocationOfEvent();
        deathEvent.setPersonID(currentP.getPersonID());
        deathEvent.setEventType("Death");
        deathEvent.setAssociatedUsername(currentP.getAssociatedUsername());
        deathEvent.setYear(deathYear);

        eventArrayList.add(deathEvent);
    }

    //-----EVENT LOCATION GENERATION-----//

    public Event generateLocationOfEvent() {
        Event locationOfEvent = new Event();

        try {
            FileReader fileReader = new FileReader(new File("json/locations.json"));
            JsonObject objetoRaiz = JsonParser.parseReader(fileReader).getAsJsonObject();
            JsonArray locationArray = objetoRaiz.getAsJsonArray("data");

            int index = randomizer.nextInt(locationArray.size());
            JsonObject newLocation = locationArray.get(index).getAsJsonObject();

            String newCity = newLocation.get("city").toString().substring(1, newLocation.get("city").toString().length() - 1);
            String newCountry = newLocation.get("country").toString().substring(1, newLocation.get("country").toString().length() - 1);
            Float latitude = newLocation.get("latitude").getAsFloat();
            Float longitude = newLocation.get("longitude").getAsFloat();

            locationOfEvent.setCity(newCity);
            locationOfEvent.setCountry(newCountry);
            locationOfEvent.setLatitude(latitude);
            locationOfEvent.setLongitude(longitude);

            return locationOfEvent;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return new Event();
    }

}
