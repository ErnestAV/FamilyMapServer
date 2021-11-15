package result;
/**
 * A class for a Load Result
 */
public class LoadResult {
    /**
     * A result message
     */
    private String message;

    /**
     * A boolean to check for success
     */
    private Boolean success;

    private int numUsers;
    private int numPersons;
    private int numEvents;

    /**
     *
     * A constructor for a Load Result
     *
     * @param message - A result message
     * @param success - A boolean to check for success
     * @param numUsers - An int for numUsers
     * @param numPersons - An int for numPersons
     * @param numEvents - An int for numEvents
     */
    public LoadResult(String message, Boolean success, int numUsers, int numPersons, int numEvents) {
        this.message = message;
        this.success = success;
        this.numUsers = numUsers;
        this.numPersons = numPersons;
        this.numEvents = numEvents;
    }

    public LoadResult(String error) {
        this.message = error;
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
}
