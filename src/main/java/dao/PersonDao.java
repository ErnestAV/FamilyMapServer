package dao;
import java.sql.*;
import java.util.ArrayList;

import model.*;

/**
 * A class for the PersonDao
 */
public class PersonDao {
    /**
     * A connection variable
     */
    private final Connection conn;

    /**
     * A constructor for the PersonDao
     *
     * @param conn - A connection variable
     */
    public PersonDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Function to insert a Person
     *
     * @param person - Person object
     * @throws DataAccessException - If a data access error occurs, throw this exception
     */
    public void insert(Person person) throws DataAccessException{
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO person (personid, associatedusername, firstname, lastname, gender, " +
                "fatherid, motherid, spouseid) VALUES(?,?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, person.getPersonID());
            stmt.setString(2, person.getAssociatedUsername());
            stmt.setString(3, person.getFirstName());
            stmt.setString(4, person.getLastName());
            stmt.setString(5, person.getGender());
            stmt.setString(6, person.getFatherID());
            stmt.setString(7, person.getMotherID());
            stmt.setString(8, person.getSpouseID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Function to find a Person
     *
     * @param personid - Person ID string
     * @return - Return either the person if found, or null if it isn't found
     * @throws DataAccessException - If a data access error occurs, throw this exception
     */
    public Person find(String personid) throws DataAccessException {
        Person person;
        ResultSet rs = null;
        String sql = "SELECT * FROM person WHERE personid = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, personid);
            rs = stmt.executeQuery();
            if (rs.next()) {
                person = new Person(rs.getString("personid"), rs.getString("associatedusername"),
                        rs.getString("firstname"), rs.getString("lastname"), rs.getString("gender"),
                        rs.getString("fatherid"), rs.getString("motherid"), rs.getString("spouseid"));
                return person;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding person");
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
     * Deletes the person table
     * @throws DataAccessException - If a data access error occurs, throw this exception
     */
    public void delete() throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM person";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing person table");
        }
    }

    public boolean deletePersonsFromUser(String associatedusername) throws DataAccessException {
        String sql = "DELETE FROM person WHERE associatedusername ='"+associatedusername+"';";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Error deleting persons from user: " + e.getMessage());
            return false;
        }
        return true;
    }

    public Person[] allPersons(String associatedusername) throws DataAccessException {
        Person person;
        ArrayList<Person> allPersons = new ArrayList<>();
        ResultSet rs = null;
        String sql = "SELECT * FROM person WHERE associatedusername = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, associatedusername);
            rs = stmt.executeQuery();
            while (rs.next()) {
                person = new Person(rs.getString("personid"), rs.getString("associatedusername"),
                        rs.getString("firstname"), rs.getString("lastname"), rs.getString("gender"),
                        rs.getString("fatherid"), rs.getString("motherid"), rs.getString("spouseid"));

                allPersons.add(person);
            }
            return allPersons.toArray(new Person[0]);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding persons");
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
