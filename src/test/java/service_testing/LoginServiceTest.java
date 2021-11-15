package service_testing;

import dao.*;
import model.Person;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import request.LoginRequest;
import result.LoginResult;
import service.LoginService;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

public class LoginServiceTest {
    private Database db;
    private UserDao uDao;
    public PersonDao pDao;

    User user;
    Person person;

    @BeforeEach
    public void setUp() throws DataAccessException {
        db = new Database();
        db.openConnection();
        Connection conn = db.getConnection();


        uDao = new UserDao(conn);
        pDao = new PersonDao(conn);

        user = new User("Hello123", "World", "hello@world.com", "Hello", "World", "m", "random442342");
        person = new Person("random442342", "Hello123", "Hello", "World", "m", "Father343", "Mother2312", "Spouse43543");

        uDao.delete();
        pDao.delete();
    }

    @AfterEach
    public void tearDown() throws DataAccessException, SQLException {
        if (!db.getConnection().isClosed()) {
            db.closeConnection(false);
        }
    }

    @Test
    public void loginSuccessful() throws DataAccessException {
        uDao.insert(user);
        pDao.insert(person);

        db.closeConnection(true);

        LoginService loginService = new LoginService();
        LoginResult loginResult = loginService.login(new LoginRequest(user.getUsername(), user.getPassword()));

        assertNotNull(loginResult);
        assertEquals("Hello123", loginResult.getUsername());
        assertNull(loginResult.getMessage());
        assertNotNull(loginResult.getPersonID());
        assertNotNull(loginResult.getAuthtoken());
    }

    @Test
    public void loginFail() throws DataAccessException { //Check with TA
        db.clearTables();
        db.closeConnection(true);

        LoginService loginService = new LoginService();
        LoginResult loginResult = loginService.login(new LoginRequest(user.getUsername(), user.getPassword()));

        db.openConnection();
        Connection conn = db.getConnection();

        uDao = new UserDao(conn);
        pDao = new PersonDao(conn);

        assertNotNull(loginResult);
        assertFalse(loginResult.getSuccess());
        assertEquals("Error: username or password incorrect", loginResult.getMessage());
        assertNull(uDao.find(user.getUsername()));
    }

    @Test
    public void loginPasswordFail() throws DataAccessException { //Check with TA
        uDao.insert(user);
        pDao.insert(person);
        db.closeConnection(true);

        LoginService loginService = new LoginService();
        LoginResult loginResult = loginService.login(new LoginRequest(user.getUsername(), user.getPassword() + "_fail"));

        db.openConnection();
        Connection conn = db.getConnection();

        uDao = new UserDao(conn);
        pDao = new PersonDao(conn);

        assertNotNull(loginResult);
        assertFalse(loginResult.getSuccess());
        assertEquals("Error: username or password incorrect", loginResult.getMessage());
    }

}
