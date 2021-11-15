package request;

/**
 * A class for a Fill Request
 */
public class FillRequest {
    /**
     * A unique username
     */
    private String username;

    /**
     * Number of user's generations
     */
    private int generations;

    /**
     * A constructor for a Fill Request
     *
     * @param username - A unique username
     * @param generations - Number of user's generations
     */
    public FillRequest(String username, int generations) {
        this.username = username;
        this.generations = generations;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getGenerations() {
        return generations;
    }

    public void setGenerations(int generations) {
        this.generations = generations;
    }
}
