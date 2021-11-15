package result;

/**
 * A class for a Login Result
 */
public class LoginResult {
    /**
     * A result message
     */
    private String message;

    /**
     * A boolean to check for success
     */
    private boolean success;

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
     * A constructor for LoginResult
     *
     * @param authtoken - An authorization token
     * @param username - A unique username
     * @param personid - A unique person ID
     */
    public LoginResult(String authtoken, String username, String personid) {
        this.success = true;
        this.authtoken = authtoken;
        this.username = username;
        this.personID = personid;
    }

    public LoginResult(String message) {
        this.message = message;
        this.success = false;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean getSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
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
