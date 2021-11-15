package service;

import dao.*;
import model.Event;
import model.Person;
import model.User;
import request.LoadRequest;
import result.LoadResult;

import java.sql.Connection;

/**
 * A class for the Load Service
 */
public class LoadService {

    private Database db;
    /**
     * Function for the Load Service
     *
     * @param r - Request object
     * @return - null as of now
     */
    public LoadResult load(LoadRequest r) {
        String error;
        if (r.getUsers() == null || r.getPersons() == null || r.getEvents() == null) {
            return new LoadResult("Error: Invalid inputs");
        }
        try {
            db = new Database();
            db.openConnection();
            Connection conn = db.getConnection();
            db.clearTables();

            UserDao uDao = new UserDao(conn);
            PersonDao pDao = new PersonDao(conn);
            EventDao eDao = new EventDao(conn);

            User[] users = r.getUsers();
            Person[] persons = r.getPersons();
            Event[] events = r.getEvents();

            for (int i = 0; i < users.length; i++) {
                uDao.insert(users[i]);
            }
            for (int i = 0; i < persons.length; i++) {
                pDao.insert(persons[i]);
            }
            for (int i = 0; i < events.length; i++) {
                eDao.insert(events[i]);
            }

            db.closeConnection(true);
            String message = "Successfully added " + users.length + " users, "
                    + persons.length + " persons, and " + events.length + " events to the database.";

            return new LoadResult(message, true, users.length, persons.length, events.length);

        } catch (DataAccessException e) {
            e.printStackTrace();
            error = e.getMessage();
            try {
                db.closeConnection(false);
            } catch (DataAccessException dataAccessException) {
                dataAccessException.printStackTrace();
                error = dataAccessException.getMessage();
            }
        }
        return new LoadResult(error);
    }
}
