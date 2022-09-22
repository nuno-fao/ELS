package pt.up.fe.els2022;

import java.util.HashMap;
import java.util.List;

public class Table {
    private HashMap<String, String> entries;
    private List<String> headers;

    public Table(List<String> headers) {
        this.entries = new HashMap<>();
        this.headers = headers;
    }
}
