package service;

import dao.DataAccessException;
import dao.Database;
import dao.EventDao;
import model.AuthToken;
import model.Event;
import result.EventResult;
import dao.AuthTokenDao;
import java.sql.Connection;

/**
 * A class for the Event Service
 */
public class EventService {
    private Database db;
    private EventDao eDao;
    private AuthTokenDao aDao;

    /**
     * Function for the Event Service
     *
     * @param authToken - Auth Token
     * @return - null as of now
     */
    public EventResult event(String authToken) throws DataAccessException {
        //Create a new connection
        db = new Database();
        Connection conn = db.getConnection();

        //Make Daos from db connection
        eDao = new EventDao(conn);
        aDao = new AuthTokenDao(conn);

        AuthToken validateAuthToken = aDao.find(authToken);

        if (validateAuthToken == null) {
            db.closeConnection(false);
            return new EventResult("Invalid token");
        }
        else {
            //Get all events (see description at specs file)
            Event[] data = eDao.allEvents(aDao.findUsername(authToken).getUsername());

            if (data == null || data.length == 0) {
                db.closeConnection(false);
                return new EventResult("No events under user");
            }
            else {
                db.closeConnection(true);
                return new EventResult(data);
            }
        }
    }
}
