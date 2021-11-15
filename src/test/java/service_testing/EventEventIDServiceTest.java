package service_testing;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.EventDao;
import model.AuthToken;
import model.Event;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.EventEventIDResult;
import service.ClearService;
import service.EventEventIDService;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class EventEventIDServiceTest {
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
    public void getEvent() throws DataAccessException {
        EventDao eDao = new EventDao(conn);
        Event event1 = new Event("Event1", "Rand123", "random442342", 56.3f, -77.2f, "El Salvador", "San Salvador", "Death", 2000);

        eDao.insert(event1);

        db.closeConnection(true);

        EventEventIDService eventService = new EventEventIDService();
        EventEventIDResult eventEventIDResult = eventService.eventWithID(event1.getEventID(), "randomToken1");

        assertNotNull(eventEventIDResult);
        assertEquals("Rand123", eventEventIDResult.getAssociatedUsername());
        assertEquals(event1.getEventID(), eventEventIDResult.getEventID());
        assertTrue(eventEventIDResult.getSuccess());
    }

    @Test
    public void getEventFailInvalidToken() throws DataAccessException {
        EventDao eDao = new EventDao(conn);
        Event event1 = new Event("Event1", "Rand123", "random442342", 56.3f, -77.2f, "El Salvador", "San Salvador", "Death", 2000);

        eDao.insert(event1);

        db.closeConnection(true);

        EventEventIDService eventService = new EventEventIDService();
        EventEventIDResult eventEventIDResult = eventService.eventWithID(event1.getEventID(), "randomToken12");

        assertEquals("Invalid token", eventEventIDResult.getMessage());
        assertFalse(eventEventIDResult.getSuccess());
    }
}
