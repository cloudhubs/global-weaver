package security.domain;

/**
 * This is a DTO for the Security module. It is currently unused, as the Security module does not return data
 * that is more complex than a string.
 */
public class SecurityData {

    private boolean status;

    private String message;

    private String data;

    public SecurityData() {
        //Default Constructor
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }


}
