package model;

import java.util.UUID;

/**
 * Class for a PERSON
 */
public class Person {

    /**
     * Unique identifier for this person (non-empty string)
     */
    private String personID;

    /**
     * User (Username) to which this person belongs
     */
    private String associatedUsername;

    /**
     * Person’s first name (non-empty string)
     */
    private String firstName;

    /**
     * Person’s last name (non-empty string)
     */
    private String lastName;

    /**
     * Person’s gender (string: “f” or “m”)
     */
    private String gender;

    /**
     * Person ID of person’s father (possibly null)
     */
    private String fatherID;

    /**
     * Person ID of person’s mother (possibly null)
     */
    private String motherID;

    /**
     * Spouse ID: Person ID of person’s spouse (possibly null)
     */
    private String spouseID;

    /**
     * Constructor to create a person
     *
     * @param personid - Unique identifier for this person (non-empty string)
     * @param associatedusername - User (Username) to which this person belongs
     * @param firstname - Person’s first name (non-empty string)
     * @param lastname - Person’s last name (non-empty string)
     * @param gender - Person’s gender (string: “f” or “m”)
     * @param fatherid - Person ID of person’s father (possibly null)
     * @param motherid - Person ID of person’s mother (possibly null)
     * @param spouseid - Person ID of person’s spouse (possibly null)
     */
    public Person(String personid, String associatedusername, String firstname, String lastname, String gender, String fatherid, String motherid, String spouseid) {
        this.personID = personid;
        this.associatedUsername = associatedusername;
        this.firstName = firstname;
        this.lastName = lastname;
        this.gender = gender;
        this.fatherID = fatherid;
        this.motherID = motherid;
        this.spouseID = spouseid;
    }

    public Person(User u){
        this.personID = u.getPersonID();
        this.associatedUsername = u.getUsername();
        this.firstName = u.getFirstName();
        this.lastName = u.getLastName();
        this.gender = u.getGender();
        fatherID = new String();
        motherID = new String();
        spouseID = new String();
    }

    public Person() {
        this.personID = UUID.randomUUID().toString();
        this.associatedUsername = null;
        this.firstName = null;
        this.lastName = null;
        this.gender = null;
        this.fatherID = null;
        this.motherID = null;
        this.spouseID = null;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
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

    public void setFatherID(String fatherID) {
        this.fatherID = fatherID;
    }

    public void setMotherID(String motherID) {
        this.motherID = motherID;
    }

    public void setSpouseID(String spouseID) {
        this.spouseID = spouseID;
    }

    public String getPersonID() {
        return personID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
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

    public String getFatherID() {
        return fatherID;
    }

    public String getMotherID() {
        return motherID;
    }

    public String getSpouseID() {
        return spouseID;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof Person) {
            Person oPerson = (Person) o;
            return oPerson.getPersonID().equals(getPersonID()) &&
                    oPerson.getAssociatedUsername().equals(getAssociatedUsername()) &&
                    oPerson.getFirstName().equals(getFirstName()) &&
                    oPerson.getLastName().equals(getLastName()) &&
                    oPerson.getGender().equals(getGender()) &&
                    oPerson.getFatherID().equals(getFatherID()) &&
                    oPerson.getMotherID().equals(getMotherID()) &&
                    oPerson.getSpouseID().equals(getSpouseID());
        } else {
            return false;
        }
    }
}
