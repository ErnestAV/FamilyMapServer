package request;
import model.*;

public class LoadRequest {

    /**
     * Array of users to load
     */
    private User[] users;

    /**
     * Array of persons to load
     */
    private Person[] persons;

    /**
     * Array of events to load
     */
    private  Event[] events;

    /**
     *
     * @param users - Array of users to load
     * @param persons - Array of persons to load
     * @param events - Array of events to load
     */
    public LoadRequest(User[] users, Person[] persons, Event[] events) {
        this.users = users;
        this.persons = persons;
        this.events = events;
    }

    public User[] getUsers() {
        return users;
    }

    public void setUsers(User[] users) {
        this.users = users;
    }

    public Person[] getPersons() {
        return persons;
    }

    public void setPersons(Person[] persons) {
        this.persons = persons;
    }

    public Event[] getEvents() {
        return events;
    }

    public void setEvents(Event[] events) {
        this.events = events;
    }
}
