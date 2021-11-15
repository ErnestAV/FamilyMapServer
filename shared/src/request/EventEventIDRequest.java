package request;

/**
 * Class for an EVENT ID REQUEST
 */
public class EventEventIDRequest {
    /**
     * An event ID
     */
    private String eventid;

    /**
     * Constructor for Event ID Request
     * @param eventid - An event ID
     */
    public EventEventIDRequest(String eventid) {
        this.eventid = eventid;
    }

    public String getEventid() {
        return eventid;
    }

    public void setEventid(String eventid) {
        this.eventid = eventid;
    }
}
