package model;

import request.RegisterRequest;

import java.util.UUID;

/**
 * Class for a USER
 */
public class User {

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
     * Unique Person ID assigned to this user’s generated Person object
     */
    private String personID;

    /**
     * Constructor to create a user
     *
     * @param username - Unique username
     * @param password - User’s password
     * @param email - User’s email address
     * @param firstname - User’s first name
     * @param lastname - User’s last name
     * @param gender - User’s gender
     * @param personid - Unique Person ID assigned to this user’s generated Person object
     */
    public User(String username, String password, String email, String firstname, String lastname, String gender, String personid) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.firstName = firstname;
        this.lastName = lastname;
        this.gender = gender;
        this.personID = personid;
    }

    public User(RegisterRequest r){
        this.username = r.getUsername();
        this.password = r.getPassword();
        this.email = r.getEmail();
        this.firstName = r.getFirstName();
        this.lastName = r.getLastName();
        this.gender = r.getGender();
        String personid = UUID.randomUUID().toString();
        this.personID = personid;
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

    public void setPersonID(String personID) {
        this.personID = personID;
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

    public String getPersonID() {
        return personID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof User) {
            User oUser = (User) o;
            return oUser.getUsername().equals(getUsername()) &&
                    oUser.getPassword().equals(getPassword()) &&
                    oUser.getEmail().equals(getEmail()) &&
                    oUser.getFirstName().equals(getFirstName()) &&
                    oUser.getLastName().equals(getLastName()) &&
                    oUser.getGender().equals(getGender()) &&
                    oUser.getPersonID().equals(getPersonID());
        } else {
            return false;
        }
    }
}
