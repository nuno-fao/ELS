package pt.up.fe.els2022;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Table {
    private ArrayList<HashMap<String, String>> entries;
    private ArrayList<String> headers;

    public Table(){
        entries = new ArrayList<>();
        headers = new ArrayList<>();
    }
    public Table(ArrayList<String> headers) {
        this.entries = new ArrayList<>();
        this.headers = headers;
    }

    public Table(ArrayList<String> headers, ArrayList<HashMap<String, String>> entries) {
        this.entries = entries;
        this.headers = headers;
    }

    public Table copy(){
        ArrayList<HashMap<String, String>> newEntries = new ArrayList<>();
        ArrayList<String> newHeaders = new ArrayList<>(headers);

        for(HashMap<String, String> mapCopy : entries){
            HashMap<String, String> newMap = new HashMap<>(mapCopy);
            newEntries.add(newMap);
        }

        return new Table(newHeaders,newEntries);

    }

    public List<HashMap<String, String>> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<HashMap<String, String>> entries) {
        this.entries = entries;
    }

    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayList<String> headers) {
        this.headers = headers;
    }
}
