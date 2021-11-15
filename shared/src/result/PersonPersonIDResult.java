package result;

import model.Person;

/**
 * A class for a Person ID Result
 */
public class PersonPersonIDResult {
    /**
     * A result message
     */
    private String message;

    /**
     * A boolean to check for success
     */
    private Boolean success;

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
    public PersonPersonIDResult(String personid, String associatedusername, String firstname, String lastname, String gender, String fatherid, String motherid, String spouseid) {
        this.personID = personid;
        this.associatedUsername = associatedusername;
        this.firstName = firstname;
        this.lastName = lastname;
        this.gender = gender;
        this.fatherID = fatherid;
        this.motherID = motherid;
        this.spouseID = spouseid;
        this.success = true;
    }

    public PersonPersonIDResult(String notGood) {
        this.message = notGood;
        this.success = false;
    }

    public PersonPersonIDResult(Person person, String associatedusername) {
        this.personID = person.getPersonID();
        this.associatedUsername = associatedusername;
        this.firstName = person.getFirstName();
        this.lastName = person.getLastName();
        this.gender = person.getGender();
        this.fatherID = person.getFatherID();
        this.motherID = person.getMotherID();
        this.spouseID = person.getSpouseID();
        this.success = true;
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
}
