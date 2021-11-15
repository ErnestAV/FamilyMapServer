package result;
import model.Event;

/**
 * A class for an Event Result
 */
public class EventResult {
    /**
     * A result message
     */
    private String message;

    /**
     * A boolean to check for success
     */
    private Boolean success;

    /**
     * An array of events
     */
    private Event[] data;

    /**
     * A constuctor for an Event Result
     *
     * @param events - An array of events
     */
    public EventResult(Event[] events) {
        this.success = true;
        this.data = events;
    }

    public EventResult(String message) {
        this.message = message;
        this.success = false;
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

    public Event[] getData() {
        return data;
    }

    public void setData(Event[] data) {
        this.data = data;
    }
}
