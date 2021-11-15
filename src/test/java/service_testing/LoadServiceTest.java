package service_testing;

import dao.DataAccessException;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoadRequest;
import result.LoadResult;
import service.ClearService;
import service.LoadService;

import static org.junit.jupiter.api.Assertions.*;

public class LoadServiceTest {

    @BeforeEach
    public void setUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();
    }
    
    @Test
    public void loadSuccess() {
        Event event1 = new Event("Rand123", "Hello123", "random442342", 56.3f, -77.2f, "El Salvador", "San Salvador", "Death", 2000);
        Event event2 = new Event("Rand234", "Hello123", "random442342", 56.3f, -77.2f, "El Salvador", "San Salvador", "Death", 2000);

        Person person1 = new Person("random442342", "Hello123", "Hello", "World", "m", "Father343", "Mother2312", "Spouse43543");
        Person person2 = new Person("random553342", "Babe123", "Hello", "World", "f", "Father343", "Mother2312", "Spouse54353");

        User user1 = new User("Hello123", "World", "hello@world.com", "Hello", "World", "m", "random442342");
        User user2 = new User("Hello234", "Babe", "babe@world.com", "Hello", "World", "f", "random4423123");

        User[] users = new User[] {user1, user2};
        Person[] persons = new Person[] {person1, person2};
        Event[] events = new Event[] {event1, event2};

        LoadService loadService = new LoadService();
        LoadResult loadResult = loadService.load(new LoadRequest(users, persons, events));

        assertEquals(loadResult.getMessage(), "Successfully added " + users.length + " users, " + persons.length + " persons, and " + events.length + " events to the database.");
    }

    @Test
    public void loadFail() {

        Person person1 = new Person("random442342", "Hello123", "Hello", "World", "m", "Father343", "Mother2312", "Spouse43543");
        Person person2 = new Person("random553342", "Babe123", "Hello", "World", "f", "Father343", "Mother2312", "Spouse54353");

        User user1 = new User("Hello123", "World", "hello@world.com", "Hello", "World", "m", "random442342");
        User user2 = new User("Hello234", "Babe", "babe@world.com", "Hello", "World", "f", "random4423123");

        User[] users = new User[] {user1, user2};
        Person[] persons = new Person[] {person1, person2};

        LoadService loadService = new LoadService();
        LoadResult loadResult = loadService.load(new LoadRequest(users, persons, null));

        assertEquals("Error: Invalid inputs", loadResult.getMessage());
    }
}
