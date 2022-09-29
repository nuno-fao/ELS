package pt.up.fe.els2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Table {
    private List<HashMap<String, String>> entries;
    private List<String> headers;

    public Table(){}
    public Table(List<String> headers) {
        this.entries = new ArrayList<>();
        this.headers = headers;
    }

    public List<HashMap<String, String>> getEntries() {
        return entries;
    }

    public void setEntries(List<HashMap<String, String>> entries) {
        this.entries = entries;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }
}
