package result;

/**
 * A class for a Register Result
 */
public class RegisterResult {
    /**
     * A result message
     */
    private String message;

    /**
     * A boolean to check for success
     */
    private Boolean success;

    /**
     * An authorization token
     */
    private String authtoken;

    /**
     * A unique username
     */
    private String username;

    /**
     * A unique person ID
     */
    private String personID;

    /**
     * A constructor for a register result
     *
     * @param authtoken - An authorization token
     * @param username - A unique username
     * @param personid - A unique person ID
     */
    public RegisterResult(String authtoken, String username, String personid) {
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personid;
        this.message = null;
        this.success = true;
    }

    public RegisterResult(String notGood) {
        authtoken = null;
        username = null;
        personID = null;
        message = notGood;
        success = false;
    }

    public RegisterResult() {}

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

    public String getAuthtoken() {
        return authtoken;
    }

    public void setAuthtoken(String authtoken) {
        this.authtoken = authtoken;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPersonID() {
        return personID;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }
}
