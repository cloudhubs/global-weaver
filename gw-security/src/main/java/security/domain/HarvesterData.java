package security.domain;

import java.util.ArrayList;
import java.util.Objects;

/**
 * This is a DTO for the Harvester module. It allows other modules to easily obtain the information about local
 * weavers through the Harvester module.
 */
public class HarvesterData {

    /**
     * This is the HTTP status of the request.
     */
    private int status;

    /**
     * This is the HTTP message from the request.
     */
    private String message;

    /**
     * This is a list of all LocalWeaverResult objects associated with the request.
     */
    private ArrayList<LocalWeaverResult> data;

    public HarvesterData() {
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

    public ArrayList<LocalWeaverResult> getData() {
        return data;
    }

    public void setData(ArrayList<LocalWeaverResult> data) {
        this.data = data;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof HarvesterData)) return false;
        HarvesterData that = (HarvesterData) o;
        return getStatus() == that.getStatus() &&
                Objects.equals(getMessage(), that.getMessage()) &&
                Objects.equals(getData(), that.getData());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStatus(), getMessage(), getData());
    }
}
