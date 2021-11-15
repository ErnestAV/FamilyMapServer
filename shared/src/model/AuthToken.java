package model;

import java.util.UUID;

/**
 * Class for AUTHORIZATION TOKENS
 */
public class AuthToken {
    /**
     * Unique username
     */
    private String username;

    /**
     * Unique token for authorization
     */
    private String token;

    /**
     * Constructor for authorization tokens
     *
     * @param username - Unique username
     * @param token - Unique token for authorization
     */
    public AuthToken(String username, String token) {
        this.username = username;
        this.token = token;
    }

    public AuthToken() {
        this.username = null;
        this.token = UUID.randomUUID().toString();
    }

    public AuthToken(String username) {
        this.username = username;
        this.token = UUID.randomUUID().toString();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public String getToken() {
        return token;
    }
}
