package service_testing;

import dao.*;
import model.AuthToken;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.PersonPersonIDResult;
import service.ClearService;
import service.PersonPersonIDService;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class PersonPersonIDServiceTest {
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
    public void getPerson() throws DataAccessException {
        PersonDao pDao = new PersonDao(conn);
        Person person1 = new Person("random442342", "Rand123", "Hello", "World", "m", "Father344", "Mother2316", "Spouse43547");

        pDao.insert(person1);

        db.closeConnection(true);

        PersonPersonIDService personService = new PersonPersonIDService();
        PersonPersonIDResult personPersonIDResult = personService.personWithID(person1.getPersonID(), "randomToken1");

        assertNotNull(personPersonIDResult);
        assertEquals("Rand123", personPersonIDResult.getAssociatedUsername());
        assertEquals(person1.getPersonID(), personPersonIDResult.getPersonID());
        assertTrue(personPersonIDResult.getSuccess());
    }

    @Test
    public void getPersonsFailInvalidToken() throws DataAccessException {
        PersonDao pDao = new PersonDao(conn);
        Person person1 = new Person("random442342", "Rand123", "Hello", "World", "m", "Father344", "Mother2316", "Spouse43547");

        pDao.insert(person1);

        db.closeConnection(true);

        PersonPersonIDService personPersonIDService = new PersonPersonIDService();
        PersonPersonIDResult personPersonIDResult = personPersonIDService.personWithID(person1.getPersonID(), "randomToken12");

        assertEquals("Invalid token", personPersonIDResult.getMessage());
        assertFalse(personPersonIDResult.getSuccess());
    }
}
