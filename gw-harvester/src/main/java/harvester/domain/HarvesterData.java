package harvester.domain;

import java.util.ArrayList;

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
}
