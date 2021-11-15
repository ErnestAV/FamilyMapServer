package request;
/**
 * A request class for PersonPersonIDRequest
 */
public class PersonPersonIDRequest {

    /**
     * The person's id
     */
    private String personid;

    /**
     *
     * @param personid - The person's id
     */
    public PersonPersonIDRequest(String personid) {
        this.personid = personid;
    }

    public void setPersonid(String personid) {
        this.personid = personid;
    }

    public String getPersonid() {
        return personid;
    }
}
