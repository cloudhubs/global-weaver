package security.domain;

import java.util.ArrayList;

public class HarvesterData {

    private boolean status;

    private String message;

    private ArrayList<LocalWeaverResult> data;

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

    public ArrayList<LocalWeaverResult> getData() {
        return data;
    }

    public void setData(ArrayList<LocalWeaverResult> data) {
        this.data = data;
    }
}
