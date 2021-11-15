package service_testing;

import dao.*;
import model.AuthToken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.FillResult;
import result.PersonResult;
import service.ClearService;
import service.FillService;
import service.PersonService;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class FillServiceTest {
    Database db;
    AuthTokenDao authTokenDao;
    AuthToken validateAuthToken;
    PersonDao pDao;
    UserDao uDao;
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

        uDao = new UserDao(conn);
        User user = new User("Rand123", "password", "user@email.com", "random", "user", "m", "randomToken1");
        uDao.insert(user);
    }

    @AfterEach
    public void tearDown() throws DataAccessException, SQLException {
        if (!db.getConnection().isClosed()) {
            db.closeConnection(false);
        }
    }

    @Test
    public void fillSuccess() throws DataAccessException {
        db.closeConnection(true);

        PersonService personService = new PersonService();
        PersonResult personResult = personService.person("randomToken1");

        assertNull(personResult.getData());
        assertEquals("No people under user", personResult.getMessage());

        FillService fillService = new FillService();
        FillResult fillResult = fillService.fill("Rand123", 4);

        personResult = personService.person("randomToken1");
        assertNotNull(personResult.getData());
        assertEquals(31, personResult.getData().length);
        assertEquals("Successfully added 31 persons and 118 events to the database.", fillResult.getMessage());
        assertTrue(fillResult.getSuccess());
    }

    @Test
    public void fillFailGenerationsIsZeroOrLess() throws DataAccessException {
        db.closeConnection(true);

        PersonService personService = new PersonService();
        PersonResult personResult = personService.person("randomToken1");

        assertNull(personResult.getData());
        assertEquals("No people under user", personResult.getMessage());

        FillService fillService = new FillService();
        FillResult fillResult = fillService.fill("Rand123", 0);

        assertEquals("Error: Number of generations has to be greater than 0", fillResult.getMessage());
        assertFalse(fillResult.getSuccess());

        FillResult fillResult1 = fillService.fill("Rand123", -2);

        assertEquals("Error: Number of generations has to be greater than 0", fillResult1.getMessage());
        assertFalse(fillResult1.getSuccess());
    }
}
