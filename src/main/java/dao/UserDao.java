package dao;
import model.*;

import java.sql.*;

/**
 * A class for User Dao
 */
public class UserDao {
    /**
     * A connection variable
     */
    private Connection conn;

    /**
     * A constructor for the UserDao
     * @param conn - A connection variable
     */
    public UserDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Function to insert a User
     *
     * @param user - User object
     * @throws DataAccessException - If a data access error occurs, throw this exception
     */
    public void insert(User user) throws DataAccessException {
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO User (username, password, email, firstname, lastname, " +
                "gender, personid) VALUES(?,?,?,?,?,?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, user.getUsername());
            stmt.setString(2, user.getPassword());
            stmt.setString(3, user.getEmail());
            stmt.setString(4, user.getFirstName());
            stmt.setString(5, user.getLastName());
            stmt.setString(6, user.getGender());
            stmt.setString(7, user.getPersonID());

            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while inserting into the database: " + e.getMessage());
        }
    }

    /**
     * Function to find a user
     *
     * @param username - User name string
     * @return - null as of now
     */
    public User find(String username) throws DataAccessException {
        User user;
        ResultSet rs = null;
        String sql = "SELECT * FROM user WHERE username = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            rs = stmt.executeQuery();
            if (rs.next()) {
                user = new User(rs.getString("username"), rs.getString("password"),
                        rs.getString("email"), rs.getString("firstname"), rs.getString("lastname"),
                        rs.getString("gender"), rs.getString("personid"));
                return user;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding user");
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
     * Deletes the user table
     * @throws DataAccessException - If a data access error occurs, throw this exception
     */
    public void delete() throws DataAccessException {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM user";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing user table");
        }
    }

    public boolean userVerification(String username, String password) throws DataAccessException, SQLException {
        ResultSet rs = null;
        String sql = "SELECT username, password FROM user WHERE username = ? AND password = ?";
        try (PreparedStatement preparedStatement = conn.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            rs = preparedStatement.executeQuery();

            if (rs.next()) {
                return rs.getString("username").equals(username)
                        && rs.getString("password").equals(password);
            } else {
                return false;
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Issue accessing database");
        }
    }
}
