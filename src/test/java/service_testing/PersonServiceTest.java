package service_testing;

import dao.*;
import model.AuthToken;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.EventResult;
import result.PersonResult;
import service.ClearService;
import service.EventService;
import service.PersonService;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class PersonServiceTest {
    Database db;
    AuthTokenDao authTokenDao;
    Connection conn;

    @BeforeEach
    public void setUp() throws DataAccessException {
        ClearService clearService = new ClearService();
        clearService.clear();

        db = new Database();
        db.openConnection();
        conn = db.getConnection();

        authTokenDao = new AuthTokenDao(conn);
        authTokenDao.insert(new AuthToken("Rand123", "randomToken1"));
        authTokenDao.insert(new AuthToken("Rand234", "randomToken2"));
    }

    @AfterEach
    public void tearDown() throws DataAccessException, SQLException {
        if (!db.getConnection().isClosed()) {
            db.closeConnection(false);
        }
    }

    @Test
    public void getPersons() throws DataAccessException {
        PersonDao pDao = new PersonDao(conn);
        Person person1 = new Person("random442342", "Rand123", "Hello", "World", "m", "Father344", "Mother2316", "Spouse43547");
        Person person12 = new Person("random442352", "Rand123", "Hello", "World", "m", "Father344", "Mother2316", "Spouse43597");
        Person person2 = new Person("random553342", "Rand234", "Hello", "World", "f", "Father343", "Mother2312", "Spouse54353");

        pDao.insert(person1);
        pDao.insert(person2);
        pDao.insert(person12);

        db.closeConnection(true);

        PersonService personService = new PersonService();
        PersonResult personResult = personService.person("randomToken1");

        assertNotNull(personResult.getData());
        assertEquals(2, personResult.getData().length);
        assertTrue(personResult.getSuccess());
    }

    @Test
    public void getPersonsFailInvalidToken() throws DataAccessException {
        PersonDao pDao = new PersonDao(conn);
        Person person1 = new Person("random442342", "Rand123", "Hello", "World", "m", "Father344", "Mother2316", "Spouse43547");
        Person person12 = new Person("random442352", "Rand123", "Hello", "World", "m", "Father344", "Mother2316", "Spouse43597");

        pDao.insert(person1);
        pDao.insert(person12);

        db.closeConnection(true);

        EventService eventService = new EventService();
        EventResult eventResult = eventService.event("randomToken21");

        assertEquals("Invalid token", eventResult.getMessage());
        assertFalse(eventResult.getSuccess());
    }
}
