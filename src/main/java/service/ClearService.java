package service;

import dao.DataAccessException;
import dao.Database;
import result.ClearResult;

/**
 * A class for the Clear Service
 */
public class ClearService {

    private Database db;

    /**
     * Function to clear
     * @return - null as of now
     */
    public ClearResult clear() throws DataAccessException {
        ClearResult result = new ClearResult();
        try {
            db = new Database();
            db.openConnection();
            db.clearTables();
            db.closeConnection(true);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            result.setMessage("Data Access Error");

            try {
                db.closeConnection(false);
            }
            catch (DataAccessException x) {
                db.closeConnection(false);
                result.setMessage(x.getMessage());
                return result;
            }

            db.closeConnection(false);
            return result;
        }
        result.setSuccess(true);
        result.setMessage("Clear succeeded");

        return result;
    }
}
