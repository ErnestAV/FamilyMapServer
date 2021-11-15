package service;

import dao.*;
import model.AuthToken;
import model.Person;
import model.User;
import request.RegisterRequest;
import result.RegisterResult;

import java.sql.Connection;
import java.util.UUID;

/**
 * A class for the Register Service
 */
public class RegisterService {

    private Database db;
    /**
     * Function for the Register Service
     *
     * @param r - Request object
     * @return - null as of now
     */
     public RegisterResult register(RegisterRequest r) throws DataAccessException {
         db = new Database();
         RegisterResult registerResult = new RegisterResult("RegisterResult is null");

         try {
             db.openConnection();
             Connection conn = db.getConnection();

             UserDao uDao = new UserDao(conn);
             PersonDao pDao = new PersonDao(conn);
             //EventDao eDao = new EventDao(conn);

             User userTest = uDao.find(r.getUsername());

             if (userTest == null) {
                 User user = new User(r);
                 uDao.insert(user);

                 Person person = new Person(user);
                 pDao.insert(person);

                 db.closeConnection(true);
                 FillService fillService = new FillService();
                 fillService.fill(r.getUsername(), 4);

                 UUID uuid = UUID.randomUUID();

                 db.openConnection();
                 conn = db.getConnection();
                 AuthTokenDao aDao = new AuthTokenDao(conn);
                 AuthToken authToken = new AuthToken(user.getUsername(), uuid.toString());
                 aDao.insert(authToken);

                 db.closeConnection(true);
                 return new RegisterResult(authToken.getToken(), user.getUsername(), person.getPersonID());
             }
             else {
                 db.closeConnection(false);
                 return new RegisterResult("Error: Username is already used!");
             }

         } catch (DataAccessException e) {
             e.printStackTrace();
             registerResult.setMessage(e.getMessage());
             registerResult.setSuccess(false);

             db.closeConnection(false);
         }

         db.closeConnection(false);
         return registerResult;
     }
}
