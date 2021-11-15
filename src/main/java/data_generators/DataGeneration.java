package data_generators;

import model.Event;
import model.Person;

import java.util.ArrayList;

public class DataGeneration {

    private ArrayList<Person> persons;
    private ArrayList<Event> events;

    public DataGeneration(ArrayList<Person> personArray, ArrayList<Event> eventArray) {
        this.persons = personArray;
        this.events = eventArray;
    }

    public ArrayList<Person> getPersons()
    {
        return persons;
    }

    public ArrayList<Event> getEventsArray()
    {
        return events;
    }

}
