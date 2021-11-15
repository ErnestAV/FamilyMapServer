package result;

/**
 * A class for a Fill Result
 */
public class FillResult {
    /**
     * A result message
     */
    private String message;

    /**
     * A boolean to check for success
     */
    private Boolean success;

    /**
     * A constructor for a Fill Result
     * @param message - A result message
     * @param success - A boolean to check for success
     */
    public FillResult(String message, Boolean success) {
        this.message = message;
        this.success = success;
    }

    public FillResult(String message) {
        this.message = message;
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
}
