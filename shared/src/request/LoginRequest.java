package request;

/**
 * A class for a Login Request
 */
public class LoginRequest {

    /**
     * Unique username
     */
    private String username;

    /**
     * User’s password
     */
    private String password;

    /**
     * A constructor for a login request
     * @param username - Unique username
     * @param password - User’s password
     */
    public LoginRequest(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
