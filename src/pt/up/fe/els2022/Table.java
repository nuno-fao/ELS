package pt.up.fe.els2022;

import java.util.ArrayList;
import java.util.HashMap;

public class Table {
    private ArrayList<HashMap<String, String>> entries;
    private ArrayList<String> headers;
    private ArrayList<String> output;

    public ArrayList<String> getOutput() {
        return output;
    }

    public void setOutput(ArrayList<String> output) {
        this.output = output;
    }

    private String originFile;
    private String originFolder;

    public String getOriginFolder() {
        return originFolder;
    }

    public void setOriginFolder(String originFolder) {
        this.originFolder = originFolder;
    }

    public String getOriginFile() {
        return originFile;
    }

    public void setOriginFile(String originFile) {
        this.originFile = originFile;
    }

    public Table(){
        entries = new ArrayList<>();
        headers = new ArrayList<>();
        output = new ArrayList<>();
    }
    public Table(ArrayList<String> headers) {
        this.entries = new ArrayList<>();
        headers = new ArrayList<>();
        this.headers = headers;
        this.output = new ArrayList<>();
    }

    public Table(ArrayList<String> headers, ArrayList<HashMap<String, String>> entries) {
        this.entries = entries;
        this.headers = headers;
        headers = new ArrayList<>();
        this.output = new ArrayList<>();
    }

    public Table copy(){
        ArrayList<HashMap<String, String>> newEntries = new ArrayList<>();
        ArrayList<String> newHeaders = new ArrayList<>(headers);
        ArrayList<String> newOutput = new ArrayList<>(output);

        for(HashMap<String, String> mapCopy : entries){
            HashMap<String, String> newMap = new HashMap<>(mapCopy);
            newEntries.add(newMap);
        }

        Table tempTable = new Table(newHeaders,newEntries);
        tempTable.setOutput(newOutput);
        tempTable.setOriginFile(this.getOriginFile());
        return tempTable;

    }

    public ArrayList<HashMap<String, String>> getEntries() {
        return entries;
    }

    public void setEntries(ArrayList<HashMap<String, String>> entries) {
        this.entries = entries;
    }

    public ArrayList<String> getHeaders() {
        return headers;
    }

    public void setHeaders(ArrayList<String> headers) {
        this.headers = headers;
    }
}
