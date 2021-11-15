package model;

import java.util.UUID;

/**
 * Class for an EVENT
 */
public class Event {

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
     * Constructor for an event
     *
     * @param eventid - Unique identifier for this event (non-empty string)
     * @param associatedusername - User (Username) to which this person belongs
     * @param personid - ID of person to which this event belongs
     * @param latitude - Latitude of event’s location (float)
     * @param longitude - Longitude of event’s location (float)
     * @param city - City in which event occurred
     * @param country - Country in which event occurred
     * @param eventtype - Type of event (birth, baptism, christening, marriage, death, etc.)
     * @param year - Year in which event occurred (integer)
     */
    public Event(String eventid, String associatedusername, String personid, float latitude, float longitude, String country, String city, String eventtype, int year) {
        this.eventID = eventid;
        this.associatedUsername = associatedusername;
        this.personID = personid;
        this.latitude = latitude;
        this.longitude = longitude;
        this.country = country;
        this.city = city;
        this.eventType = eventtype;
        this.year = year;
    }

    public Event() {
        this.eventID = UUID.randomUUID().toString();
        this.associatedUsername = null;
        this.personID = null;
        this.latitude = 0;
        this.longitude = 0;
        this.country = null;
        this.city = null;
        this.eventType = null;
        this.year = 0;
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

    public void setCity(String city) { this.city = city; }

    public String getCity() { return city; }

    public String getEventID() {
        return eventID;
    }

    public String getAssociatedUsername() { return associatedUsername; }

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

    public int getYear() { return year; }

    @Override
    public boolean equals(Object o) {
        if (o == null)
            return false;
        if (o instanceof Event) {
            Event oEvent = (Event) o;
            return oEvent.getEventID().equals(getEventID()) &&
                    oEvent.getAssociatedUsername().equals(getAssociatedUsername()) &&
                    oEvent.getPersonID().equals(getPersonID()) &&
                    oEvent.getLatitude() == (getLatitude()) &&
                    oEvent.getLongitude() == (getLongitude()) &&
                    oEvent.getCountry().equals(getCountry()) &&
                    oEvent.getCity().equals(getCity()) &&
                    oEvent.getEventType().equals(getEventType()) &&
                    oEvent.getYear() == (getYear());
        } else {
            return false;
        }
    }
}
