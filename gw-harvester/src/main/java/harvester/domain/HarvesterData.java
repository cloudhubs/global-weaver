package harvester.domain;

public class HarvesterData {

    private boolean status;

    private String message;

    private String data;

    public HarvesterData() {
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
