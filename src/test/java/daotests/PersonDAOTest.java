package daotests;

import dao.DataAccessException;
import dao.Database;
import dao.EventDao;
import dao.PersonDao;
import model.Event;
import model.Person;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static org.junit.jupiter.api.Assertions.*;

//We will use this to test that our insert method is working and failing in the right ways
public class PersonDAOTest {
    private Database db;
    private Person bestPerson;
    private PersonDao pDao;

    @BeforeEach
    public void setUp() throws DataAccessException
    {
        //here we can set up any classes or variables we will need for the rest of our tests
        //lets create a new database
        db = new Database();
        //and a new event with random data
        bestPerson = new Person("Person503", "ProfesorSalva", "Sergio",
                "Aguirre", "M", "Berlin", "Palawan",
                "Inspectora503");
        //Here, we'll open the connection in preparation for the test case to use it
        Connection conn = db.getConnection();
        //Let's clear the database as well so any lingering data doesn't affect our tests
        db.clearTables();
        //Then we pass that connection to the EventDAO so it can access the database
        pDao = new PersonDao(conn);
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
        pDao.insert(bestPerson);

        Person compareTest = pDao.find(bestPerson.getPersonID());

        assertNotNull(compareTest);

        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void insertFail() throws DataAccessException {

        pDao.insert(bestPerson);

        assertThrows(DataAccessException.class, ()-> pDao.insert(bestPerson));
    }

    @Test
    public void findFail() throws DataAccessException {
        pDao.insert(bestPerson);

        Person compareTest = pDao.find("Person504");

        assertNotEquals(bestPerson, compareTest);
    }

    @Test
    public void findSuccess() throws DataAccessException {
        pDao.insert(bestPerson);

        Person compareTest = pDao.find(bestPerson.getPersonID());

        assertEquals(bestPerson, compareTest);
    }

    @Test
    public void deleteSuccess() throws DataAccessException {
        pDao.insert(bestPerson);

        pDao.delete();

        Person compareTest = pDao.find(bestPerson.getPersonID());

        assertNull(compareTest);
    }

    @Test
    public void deletePersonFromUserFail() throws DataAccessException {
        pDao.insert(bestPerson);

        pDao.deletePersonsFromUser(bestPerson.getAssociatedUsername() + "Fail");

        assertNotNull(bestPerson);
    }

    @Test
    public void deletePersonFromUserSuccess() throws DataAccessException {
        pDao.insert(bestPerson);

        pDao.deletePersonsFromUser(bestPerson.getAssociatedUsername());

        Person compareTest = pDao.find(bestPerson.getPersonID());

        assertNull(compareTest);
    }
}
