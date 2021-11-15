package dao;
import model.Event;

import java.sql.*;
import java.util.ArrayList;

public class EventDao {
    /**
     * A connection variable
     */
    private final Connection conn;

    /**
     * A constructor for the EventDao
     *
     * @param conn - A connection variable
     */
    public EventDao(Connection conn)
    {
        this.conn = conn;
    }

    /**
     * Function to insert an Event
     *
     * @param event - Event object
     * @throws DataAccessException - If a data access error occurs, throw this exception
     */
    public void insert(Event event) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO events (eventid, associatedusername, personid, latitude, longitude, " +
                "country, city, eventtype, year) VALUES(?,?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, event.getEventID());
            stmt.setString(2, event.getAssociatedUsername());
            stmt.setString(3, event.getPersonID());
            stmt.setFloat(4, event.getLatitude());
            stmt.setFloat(5, event.getLongitude());
            stmt.setString(6, event.getCountry());
            stmt.setString(7, event.getCity());
            stmt.setString(8, event.getEventType());
            stmt.setInt(9, event.getYear());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Function to find an Event
     *
     * @param eventID - Unique identifier for this event (non-empty string)
     * @return - Return either the event if found, or null if it isn't found
     * @throws DataAccessException - If a data access error occurs, throw this exception
     */
    public Event find(String eventID) throws DataAccessException {
        Event event;
        ResultSet rs = null;
        String sql = "SELECT * FROM events WHERE eventid = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, eventID);
            rs = stmt.executeQuery();
            if (rs.next()) {
                event = new Event(rs.getString("eventid"), rs.getString("associatedusername"),
                        rs.getString("personid"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventtype"),
                        rs.getInt("year"));
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding event");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }

        }
        return null;
    }

    /**
     * Deletes the events table
     * @throws DataAccessException - If a data access error occurs, throw this exception
     */
    public void delete() throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM events";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing events");
        }
    }

    public boolean deleteEventsFromUser(String associatedusername) throws DataAccessException {
        String sql = "DELETE FROM events WHERE associatedusername ='"+associatedusername+"';";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error deleting events from user: " + e.getMessage());
            return false;
        }
        return true;
    }

    public Event[] allEvents(String username) throws DataAccessException {
        Event event;
        ArrayList<Event> allEvents = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM events WHERE associatedusername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            while (rs.next()) {
                event = new Event(rs.getString("eventid"), rs.getString("associatedusername"),
                        rs.getString("personid"), rs.getFloat("latitude"), rs.getFloat("longitude"),
                        rs.getString("country"), rs.getString("city"), rs.getString("eventtype"),
                        rs.getInt("year"));

                allEvents.add(event);
            }
            return allEvents.toArray(new Event[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding events");
        } finally {
            if(rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
