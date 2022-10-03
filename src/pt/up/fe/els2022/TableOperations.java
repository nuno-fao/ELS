package pt.up.fe.els2022;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

public class TableOperations {
    public static void addColumn(Table table, String name, String value){
        table.getHeaders().add("value");
        List<HashMap<String, String>> entries = table.getEntries();
        for (HashMap<String,String> entry:entries) {
            entry.put(name,value);
        }
    }

    public static void removeColumn(Table table, String name){
        table.getHeaders().remove(name);
        List<HashMap<String, String>> entries = table.getEntries();
        for (HashMap<String,String> entry:entries) {
            entry.remove(name);
        }
    }

    public static void sortBy(Table table, String col, boolean ascending){
        List<HashMap<String,String>> entries = table.getEntries();

        entries.sort(new Comparator<HashMap<String, String>>() {

            public int compare(HashMap<String, String> o1, HashMap<String, String> o2) {
                // compare two instance of `Score` and return `int` as result.
                return o1.get(col).compareTo(o2.get(col));
            }
        });
    }

    public static Table mergeTables(Table table1, Table table2){
        Table newTable = new Table();

//        newTable.setHeaders( Stream.concat(table1.getHeaders().stream(), table2.getHeaders().stream()).toList());
//        newTable.setEntries( Stream.concat(table1.getEntries().stream(), table2.getEntries().stream()).toList());

        return newTable;

    }
}
