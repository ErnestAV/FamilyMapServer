package result;
import model.*;

/**
 * A class for a Person Result
 */
public class PersonResult {
    /**
     * A result message
     */
    private String message;

    /**
     * A boolean to check for success
     */
    private Boolean success;

    /**
     * An array of Persons
     */
    private Person[] data;

    /**
     * A constructor for PersonResult
     *
     * @param persons - An array of Persons
     */
    public PersonResult(Person[] persons) {
        this.success = true;
        this.data = persons;
    }

    public PersonResult(String notGood) {
        this.message = notGood;
        this.success = false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Person[] getData() {
        return data;
    }

    public void setData(Person[] data) {
        this.data = data;
    }
}
