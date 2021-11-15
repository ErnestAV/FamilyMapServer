package service;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.PersonDao;
import model.AuthToken;
import model.Person;
import result.PersonResult;

import java.sql.Connection;

/**
 * A class for the Person Service
 */
public class PersonService {

    private Database db;
    private PersonDao pDao;
    private AuthTokenDao aDao;
    /**
     * Function for the Person Service
     *
     * @param authToken - Auth Token
     * @return - null as of now
     */
    public PersonResult person(String authToken) throws DataAccessException {
        db = new Database();
        Connection conn = db.getConnection();

        aDao = new AuthTokenDao(conn);
        pDao = new PersonDao(conn);

        AuthToken validateAuthToken = aDao.find(authToken);

        if (validateAuthToken == null) {
            db.closeConnection(false);
            return new PersonResult("Invalid token");
        }
        else {
            Person[] data = pDao.allPersons(validateAuthToken.getUsername());

            if (data == null || data.length == 0) {
                db.closeConnection(true);
                return new PersonResult("No people under user");
            }
            else {
                db.closeConnection(true);
                return new PersonResult(data);
            }
        }
    }
}
