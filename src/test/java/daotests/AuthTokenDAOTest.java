package daotests;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import model.AuthToken;
import model.User;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

public class AuthTokenDAOTest {
    private Database db;
    private AuthToken bestAuthToken;
    private User user;
    private AuthTokenDao aDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        user = new User("RandomUser", "password","email@email.com", "Random", "User", "m", "123");
        bestAuthToken = new AuthToken(user.getUsername());
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        aDao = new AuthTokenDao(conn);
    }

    @AfterEach
    public void tearDown() throws DataAccessException {
        //Here we close the connection to the database file so it can be opened elsewhere.
        //We will leave commit to false because we have no need to save the changes to the database
        //between test cases
        db.closeConnection(false);
    }

    @Test
    public void insertPass() throws DataAccessException {
        aDao.insert(bestAuthToken);

        AuthToken compareTest = aDao.find(bestAuthToken.getToken());

        assertNotNull(compareTest);

        assertEquals(bestAuthToken.getToken(), compareTest.getToken());
    }

    @Test
    public void insertFail() throws DataAccessException {
        //lets do this test again but this time lets try to make it fail
        //if we call the method the first time it will insert it successfully
        aDao.insert(bestAuthToken);
        //but our sql table is set up so that "eventID" must be unique. So trying to insert it
        //again will cause the method to throw an exception
        //Note: This call uses a lambda function. What a lambda function is is beyond the scope
        //of this class. All you need to know is that this line of code runs the code that
        //comes after the "()->" and expects it to throw an instance of the class in the first parameter.
        assertThrows(DataAccessException.class, ()-> aDao.insert(bestAuthToken));
    }

    @Test
    public void findSuccess() throws DataAccessException {
        aDao.insert(bestAuthToken);

        AuthToken compareTest = aDao.find(bestAuthToken.getToken());

        assertEquals(bestAuthToken.getToken(), compareTest.getToken());
    }

    @Test
    public void findFail() throws DataAccessException {
        aDao.insert(bestAuthToken);

        user = new User("RandomUser2", "password","email@email.com", "Random", "User", "m", "123");
        AuthToken compareAuth = new AuthToken(user.getUsername());

        AuthToken compare1 = aDao.find(bestAuthToken.getToken());
        AuthToken compare2 = aDao.find(compareAuth.getToken());

        assertNotEquals(compare1, compare2);
    }

    @Test
    public void findUsernameSuccess() throws DataAccessException {
        aDao.insert(bestAuthToken);

        AuthToken compareTest = aDao.findUsername(bestAuthToken.getToken());

        assertEquals(bestAuthToken.getUsername(), compareTest.getUsername());
    }

    @Test
    public void findUsernameFail() throws DataAccessException {
        aDao.insert(bestAuthToken);

        User user1 = new User("NewRandomUser", "password","email@email.com", "Random", "User", "m", "1234");

        AuthToken newestAuthToken = new AuthToken(user1.getUsername());
        aDao.insert(newestAuthToken);

        AuthToken compareTest = aDao.find(newestAuthToken.getToken());

        assertNotEquals(compareTest.getUsername(), bestAuthToken.getUsername());
    }

    @Test
    public void deleteSuccess() throws DataAccessException {
        aDao.insert(bestAuthToken);

        aDao.delete();

        AuthToken compareTest = aDao.find(bestAuthToken.getToken());

        assertNull(compareTest);
    }
}
