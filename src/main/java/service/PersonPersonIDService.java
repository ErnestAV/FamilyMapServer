package service;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.PersonDao;
import model.AuthToken;
import model.Person;
import result.PersonPersonIDResult;

import java.sql.Connection;

/**
 * A class for the Person ID Service
 */
public class PersonPersonIDService {
    private Database db;
    private PersonDao pDao;
    private AuthTokenDao aDao;
    /**
     * Function for the Person ID Service
     *
     * @param personID - Request object
     * @param authToken - Auth Token
     * @return - null as of now
     */
    public PersonPersonIDResult personWithID(String personID, String authToken) throws DataAccessException {
        try {
            db = new Database();
            db.openConnection();
            Connection conn = db.getConnection();

            pDao = new PersonDao(conn);
            aDao = new AuthTokenDao(conn);

            AuthToken validateAuthToken = aDao.find(authToken);

            if (validateAuthToken == null) {
                db.closeConnection(false);
                return new PersonPersonIDResult("Invalid token");
            }
            else {
                Person person = pDao.find(personID);

                if (person == null) {
                    db.closeConnection(false);
                    return new PersonPersonIDResult("Person not found");
                } else if (!validateAuthToken.getUsername().equals(person.getAssociatedUsername())) {
                    db.closeConnection(false);
                    return new PersonPersonIDResult("Person not found under current user");
                } else {
                    db.closeConnection(true);
                    return new PersonPersonIDResult(person, validateAuthToken.getUsername());
                }
            }

        } catch (DataAccessException e) {
            e.printStackTrace();
            return new PersonPersonIDResult("Error: " + e.getMessage());
        }
    }
}
