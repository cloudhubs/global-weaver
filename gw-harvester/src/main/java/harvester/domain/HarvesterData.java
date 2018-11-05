package harvester.domain;

import java.util.ArrayList;
import java.util.Objects;

public class HarvesterData {

    private int status;

    private String message;

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
