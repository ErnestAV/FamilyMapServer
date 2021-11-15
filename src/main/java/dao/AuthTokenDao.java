package dao;

import model.*;

import javax.xml.crypto.Data;
import java.sql.*;

/**
 * A class for the AuthTokenDao
 */
public class AuthTokenDao {
    /**
     * A connection variable
     */
    private Connection conn;

    /**
     * A constructor for the AuthTokenDao
     * @param conn - A connection variable
     */
    public AuthTokenDao(Connection conn) {
        this.conn = conn;
    }

    /**
     * Function to insert an authorization token
     *
     * @param authToken - AuthToken object
     * @throws dao.DataAccessException - Exception
     */
    public void insert(AuthToken authToken) throws DataAccessException{
        //We can structure our string to be similar to a sql command, but if we insert question
        //marks we can change them later with help from the statement
        String sql = "INSERT INTO authorizationtoken (username, token) VALUES(?,?)";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            //Using the statements built-in set(type) functions we can pick the question mark we want
            //to fill in and give it a proper value. The first argument corresponds to the first
            //question mark found in our sql String
            stmt.setString(1, authToken.getUsername());
            stmt.setString(2, authToken.getToken());

            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error encountered while inserting into the database");
        }
    }

    /**
     * Function to find an authorization token
     *
     * @param authTokenToFind - Username string
     * @return - null as of now
     */
    public AuthToken find(String authTokenToFind) throws DataAccessException {
        ResultSet rs = null;
        String sql = "SELECT * FROM authorizationtoken WHERE token = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authTokenToFind);
            rs = stmt.executeQuery();
            if (rs.next()) {
                AuthToken authToken = new AuthToken(rs.getString("username"), rs.getString("token"));
                return authToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding authorization token");
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

    public AuthToken findUsername(String authToken) throws DataAccessException {
        ResultSet rs = null;
        String sql = "SELECT * FROM authorizationtoken WHERE token = ?;";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, authToken);
            rs = stmt.executeQuery();
            if (rs.next()) {
                AuthToken newAuthToken = new AuthToken(rs.getString("username"), rs.getString("token"));
                return newAuthToken;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DataAccessException("Error encountered while finding authorization token");
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
     * Deletes the authorization token table
     * @throws DataAccessException - If a data access error occurs, throw this exception
     */
    public void delete() throws DataAccessException
    {
        try (Statement stmt = conn.createStatement()){
            String sql = "DELETE FROM authorizationtoken";
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new DataAccessException("SQL Error encountered while clearing authorization token table");
        }
    }
}
