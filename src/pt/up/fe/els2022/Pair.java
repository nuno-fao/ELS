package pt.up.fe.els2022;

import java.util.ArrayList;
import java.util.HashMap;

public class Pair {
    public final ArrayList<HashMap<String, String>> entry;
    public final ArrayList<String> order;

    public Pair(ArrayList<HashMap<String, String>> entry, ArrayList<String> order) {
        this.entry = entry;
        this.order = order;
    }
};