package service;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.EventDao;
import model.AuthToken;
import model.Event;
import result.EventEventIDResult;
import java.sql.Connection;

/**
 * A class for the Event ID Service
 */
public class EventEventIDService {
    private Database db;
    private EventDao eDao;
    private AuthTokenDao aDao;
    /**
     * Function for the Event ID Service
     *
     * @param eventID - Request object
     * @param authToken - Auth Token
     * @return - null as of now
     */
    public EventEventIDResult eventWithID(String eventID, String authToken) throws DataAccessException {
        try {
            //Create a new connection
            db = new Database();
            db.openConnection();
            Connection conn = db.getConnection();

            //Make Daos from db connection
            eDao = new EventDao(conn);
            aDao = new AuthTokenDao(conn);

            AuthToken validateAuthToken = aDao.find(authToken);

            if (validateAuthToken == null) {
                db.closeConnection(false);
                return new EventEventIDResult("Invalid token");
            } else {
                //Get event (see description at specs file)
                Event event = eDao.find(eventID);

                if (event == null) {
                    db.closeConnection(false);
                    return new EventEventIDResult("Event not found");
                } else if (!validateAuthToken.getUsername().equals(event.getAssociatedUsername())) {
                    db.closeConnection(false);
                    return new EventEventIDResult("Event not found under current user");
                } else {
                    db.closeConnection(true);
                    return new EventEventIDResult(event, validateAuthToken.getUsername());
                }
            }
        } catch (DataAccessException e) {
            e.printStackTrace();
            return new EventEventIDResult("Error: " + e.getMessage());
        }
    }
}
