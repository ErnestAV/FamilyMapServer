package service;

import dao.*;
import data_generators.DataGeneration;
import data_generators.DataGenerator;
import model.Person;
import model.User;
import result.FillResult;
import java.sql.Connection;

/**
 * A class for the Fill Service
 */
public class FillService {
    Database db;
    private UserDao uDao;
    private PersonDao pDao;
    private EventDao eDao;

    /**
    private ArrayList<String> maleNames;
    private ArrayList<String> femaleNames;
    private ArrayList<String> surNames;

    private ArrayList<String> locations;**/

    /**
     * @param username          - Username
     * @param numbOfGenerations - Generations
     * @return - A fill result
     */
    public FillResult fill(String username, int numbOfGenerations) throws DataAccessException {
        db = new Database();
        db.openConnection();
        Connection conn = db.getConnection();

        uDao = new UserDao(conn);
        pDao = new PersonDao(conn);
        eDao = new EventDao(conn);

        if (numbOfGenerations <= 0) {
            db.closeConnection(false);
            return new FillResult("Error: Number of generations has to be greater than 0", false);
        }
        try {
            User currentUser = uDao.find(username);
            if (currentUser == null) {
                db.closeConnection(false);
                return new FillResult("Error: User doesn't exist", false);
            }
            Person currentPerson = pDao.find(currentUser.getPersonID());
            if (!pDao.deletePersonsFromUser(currentUser.getUsername()) || !eDao.deleteEventsFromUser(currentUser.getUsername())){
                db.closeConnection(false);
                return new FillResult("Error while deleting Person and Event data for User", false);
            }
            else {
                DataGenerator dataGenerator = new DataGenerator();

                //Convert user to a person

                if (currentPerson == null) {
                    currentPerson = new Person();
                    currentPerson.setAssociatedUsername(currentUser.getUsername());
                    currentPerson.setFirstName(currentUser.getFirstName());
                    currentPerson.setLastName(currentUser.getLastName());
                    currentPerson.setGender(currentUser.getGender());
                }

                DataGeneration generationData = dataGenerator.generateAllTheGenerations(currentPerson, numbOfGenerations);

                for (int i = 0; i < generationData.getPersons().size(); i++) {
                    pDao.insert(generationData.getPersons().get(i));
                }

                for (int j = 0; j < generationData.getEventsArray().size(); j++) {
                    eDao.insert(generationData.getEventsArray().get(j));
                }

                db.closeConnection(true);
                return new FillResult("Successfully added " + generationData.getPersons().size()
                        + " persons and " + generationData.getEventsArray().size() + " events to the database.", true);
            }

        } catch (DataAccessException e) {
            e.printStackTrace();
            db.closeConnection(false);
        }
        db.closeConnection(false);
        return new FillResult("Error in Fill Service");
    }
}