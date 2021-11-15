package service_testing;

import dao.*;
import model.Event;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import result.ClearResult;
import service.ClearService;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

public class ClearServiceTest {
    private Database db;
    private EventDao eDao;
    private UserDao uDao;
    public PersonDao pDao;

    Event event;
    User user;
    Person person;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        db.openConnection();
        Connection conn = db.getConnection();

        eDao = new EventDao(conn);
        uDao = new UserDao(conn);
        pDao = new PersonDao(conn);

        event = new Event("Rand123", "Hello123", "random442342", 56.3f, -77.2f, "El Salvador", "San Salvador", "Death", 2000);
        user = new User("Hello123", "World", "hello@world.com", "Hello", "World", "m", "random442342");
        person = new Person("A", "Hello123", "A", "B", "m", "Father343", "Mother2312", "Spouse43543");
    }

    @AfterEach
    public void tearDown() throws DataAccessException, SQLException {
        if (!db.getConnection().isClosed()) {
            db.closeConnection(false);
        }
    }

    @Test
    public void clearDataBaseSuccess() throws DataAccessException {
        eDao.insert(event);
        uDao.insert(user);
        pDao.insert(person);

        db.closeConnection(true);

        ClearService clearService = new ClearService();
        ClearResult clearResult = clearService.clear();

        assertTrue(clearResult.getSuccess());
    }

}
