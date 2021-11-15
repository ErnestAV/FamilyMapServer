package request;

public class RegisterRequest {
    /**
     * Unique username
     */
    private String username;

    /**
     * User’s password
     */
    private String password;

    /**
     * User’s email address
     */
    private String email;

    /**
     * User’s first name
     */
    private String firstName;

    /**
     * User’s last name
     */
    private String lastName;

    /**
     * User’s gender
     */
    private String gender;

    /**
     * A constructor for an event request
     *
     * @param username - Unique username
     * @param password - User’s password
     * @param email - User’s email address
     * @param firstname - User’s first name
     * @param lastname - User’s last name
     * @param gender - User’s gender
     */
    public RegisterRequest(String username, String password, String email, String firstname, String lastname, String gender) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstname;
        this.lastName = lastname;
        this.gender = gender;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getGender() {
        return gender;
    }
}
