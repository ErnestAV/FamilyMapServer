package service;

import dao.AuthTokenDao;
import dao.DataAccessException;
import dao.Database;
import dao.UserDao;
import model.AuthToken;
import model.User;
import request.LoginRequest;
import result.LoginResult;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.UUID;

/**
 * A class for the Login Service
 */
public class LoginService {
    /**
     * Function for the Login Service
     *
     * @param loginRequest - Request object
     * @return - null as of now
     */
    public LoginResult login(LoginRequest loginRequest) throws DataAccessException {
        Database db = new Database();
        try {
            Connection conn = db.getConnection();
            UserDao uDao = new UserDao(conn);

            boolean success = uDao.userVerification(loginRequest.getUsername(), loginRequest.getPassword());

            if (success) {
                UUID uuid = UUID.randomUUID();

                AuthTokenDao aDao = new AuthTokenDao(conn);
                AuthToken authToken = new AuthToken(loginRequest.getUsername(), uuid.toString());
                aDao.insert(authToken);

                User user = uDao.find(loginRequest.getUsername());

                db.closeConnection(true);
                return new LoginResult(authToken.getToken(), loginRequest.getUsername(), user.getPersonID());
            }
            else {
                db.closeConnection(false);
                return new LoginResult("Error: username or password incorrect");
            }
        }
        catch (DataAccessException | SQLException e) {
            db.closeConnection(false);
            e.printStackTrace();
        }

        return null;
    }
}
