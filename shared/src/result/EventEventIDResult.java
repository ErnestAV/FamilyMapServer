package result;

import model.Event;

/**
 * A class for an Event ID Result
 */
public class EventEventIDResult {
    /**
     * A result message
     */
    private String message;

    /**
     * A boolean to check for success
     */
    private Boolean success;

    /**
     * Unique identifier for this event (non-empty string)
     */
    private String eventID;

    /**
     * User (Username) to which this person belongs
     */
    private String associatedUsername;

    /**
     * ID of person to which this event belongs
     */
    private String personID;

    /**
     * Latitude of event’s location (float)
     */
    private float latitude;

    /**
     * Longitude of event’s location (float)
     */
    private float longitude;

    /**
     * Country in which event occurred
     */
    private String country;

    /**
     * City in which event occurred
     */
    private String city;

    /**
     * Type of event (birth, baptism, christening, marriage, death, etc.)
     */
    private String eventType;

    /**
     * Year in which event occurred (integer)
     */
    private int year;

    /**
     * A constructor for an Event ID Result
     *
     * @param eventid - Unique identifier for this event (non-empty string)
     * @param associatedusername - User (Username) to which this person belongs
     * @param personid - ID of person to which this event belongs
     * @param latitude - Latitude of event’s location (float)
     * @param longitude - Longitude of event’s location (float)
     * @param country - Country in which event occurred
     * @param city  - City in which event occurred
     * @param eventtype - Type of event (birth, baptism, christening, marriage, death, etc.)
     * @param year - Year in which event occurred (integer)
     */
    public EventEventIDResult(String eventid, String associatedusername, String personid, float latitude, float longitude, String country, String city, String eventtype, int year) {
        this.eventID = eventid;
        this.associatedUsername = associatedusername;
        this.personID = personid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventtype;
        this.year = year;
        this.success = true;
    }

    public EventEventIDResult(String notGood) {
        this.message = notGood;
        this.success = false;
    }

    public EventEventIDResult(Event event, String associatedusername) {
        this.eventID = event.getEventID();
        this.associatedUsername = associatedusername;
        this.personID = event.getPersonID();
        this.latitude = event.getLatitude();
        this.longitude = event.getLongitude();
        this.country = event.getCountry();
        this.city = event.getCity();
        this.eventType = event.getEventType();
        this.year = event.getYear();
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

    public void setEventID(String eventID) {
        this.eventID = eventID;
    }

    public void setAssociatedUsername(String associatedUsername) {
        this.associatedUsername = associatedUsername;
    }

    public void setPersonID(String personID) {
        this.personID = personID;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getEventID() {
        return eventID;
    }

    public String getAssociatedUsername() {
        return associatedUsername;
    }

    public String getPersonID() {
        return personID;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public String getCountry() {
        return country;
    }

    public String getEventType() {
        return eventType;
    }

    public int getYear() {
        return year;
    }
}
