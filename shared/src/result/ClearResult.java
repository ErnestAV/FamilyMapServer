package result;
/**
 * A class for a Clear Result
 */
public class ClearResult {

    /**
     * A result message
     */
    private String message;

    /**
     * A boolean to check for success
     */
    private boolean success;

    /**
     * A constructor for a Clear Result
     *
     * @param message - A result message
     * @param success - A boolean to check for success
     */
    public ClearResult(String message, boolean success) {
        this.message = message;
        this.success = success;
    }

    public ClearResult() {}

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
}
