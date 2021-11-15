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
import static org.junit.jupiter.api.Assertions.*;
import result.EventResult;
import service.ClearService;
import service.EventService;

import java.sql.Connection;
import java.sql.SQLException;

public class EventServiceTest {
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
    public void getEvents() throws DataAccessException {
        EventDao eDao = new EventDao(conn);
        Event event1 = new Event("Event1", "Rand123", "random442342", 56.3f, -77.2f, "El Salvador", "San Salvador", "Death", 2000);
        Event event23 = new Event("Event23", "Rand123", "random442342", 56.3f, -77.2f, "El Salvador", "San Salvador", "Death", 2000);
        Event event2 = new Event("Event2", "Rand234", "random442342", 56.3f, -77.2f, "El Salvador", "San Salvador", "Death", 2000);

        eDao.insert(event1);
        eDao.insert(event23);
        eDao.insert(event2);

        db.closeConnection(true);

        EventService eventService = new EventService();
        EventResult eventResult = eventService.event("randomToken1");

        assertNotNull(eventResult.getData());
        assertEquals(2, eventResult.getData().length);
        assertTrue(eventResult.getSuccess());
    }

    @Test
    public void getEventsFailInvalidToken() throws DataAccessException {
        EventDao eDao = new EventDao(conn);
        Event event1 = new Event("Event1", "Rand123", "random442342", 56.3f, -77.2f, "El Salvador", "San Salvador", "Death", 2000);
        Event event23 = new Event("Event23", "Rand123", "random442342", 56.3f, -77.2f, "El Salvador", "San Salvador", "Death", 2000);

        eDao.insert(event1);
        eDao.insert(event23);

        db.closeConnection(true);

        EventService eventService = new EventService();
        EventResult eventResult = eventService.event("randomToken21");

        assertEquals("Invalid token", eventResult.getMessage());
        assertFalse(eventResult.getSuccess());
    }
}
