package security.domain;

/**
 * This is a DTO for the Security module. It is currently unused, as the Security module does not return data
 * that is more complex than a string.
 */
public class SecurityData {

    /**
     * This is the HTTP status of the request.
     */
    private int status;

    /**
     * This is the HTTP message from the request.
     */
    private String message;

    /**
     * This is the actual data returned by the request.
     */
    private String data;

    public SecurityData() {
        //Default Constructor
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getData() { return data; }

    public void setData(String data) { this.data = data; }


}
