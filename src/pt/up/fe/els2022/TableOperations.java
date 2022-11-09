package pt.up.fe.els2022;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class TableOperations {
    public static void addColumn(Table table, String name, String value){
        table.getHeaders().add(name);
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



    private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public static void sortBy(Table table, String col, boolean ascending){

        List<HashMap<String,String>> entries = table.getEntries();

        boolean is_numeric = true;
        for (var v : entries){
            if(!isNumeric(v.get(col))){
                is_numeric = false;
                break;
            }
        }
        boolean finalIs_numeric = is_numeric;
        entries.sort(new Comparator<HashMap<String, String>>() {
            public int compare(HashMap<String, String> o1, HashMap<String, String> o2) {
                // compare two instance of `Score` and return `int` as result.
                if(finalIs_numeric){
                    var v = ascending ? (int) (Float.parseFloat(o1.get(col)) - Float.parseFloat(o2.get(col))) : (int) (Float.parseFloat(o2.get(col)) - Float.parseFloat(o1.get(col)));
                    return v;
                }else{
                    return ascending ? o1.get(col).compareTo(o2.get(col)) : o2.get(col).compareTo(o1.get(col));
                }
            }
        });
    }

    public static Table mergeTables(Table table1, Table table2){
        ArrayList<HashMap<String, String>> newEntries = new ArrayList<>();
        ArrayList<String> newHeaders = new ArrayList<>();

        newHeaders.addAll(table1.getHeaders());
        for(String header : table2.getHeaders()){
            if(!newHeaders.contains(header)){
                newHeaders.add(header);
            }
        }


        for(HashMap<String, String> mapCopy : table1.getEntries()){
            HashMap<String, String> newMap = new HashMap<>(mapCopy);
            newEntries.add(newMap);
        }

        for(HashMap<String, String> mapCopy : table2.getEntries()){
            HashMap<String, String> newMap = new HashMap<>(mapCopy);
            newEntries.add(newMap);
        }

        return new Table(newHeaders,newEntries);

    }

    public static void write(Table table, String path)  {
        ArrayList<String> order;
        if(!table.getOutput().isEmpty()){
            order = table.getOutput();
        }
        else{
            order = table.getHeaders();
        }

        Path writeTo = Path.of(path);

        StringBuilder line = new StringBuilder();
        for(String col : order){
            line.append(col);
            line.append(",");
        }
        line.setCharAt(line.length()-1,'\n');

        for(HashMap<String,String> tableLine : table.getEntries()){
            for(String col : order){
                String value = tableLine.get(col);
                line.append(value == null ? "" : value );
                line.append(",");
            }
            line.setCharAt(line.length()-1,'\n');
        }
        line.setLength(line.length()-1);

        try {
            Files.createDirectories(writeTo.getParent());
            File outputFile = new File(path);
            outputFile.delete();
            outputFile.createNewFile();

            FileWriter writer = new FileWriter(path);
            writer.write(line.toString());
            writer.close();

        } catch (IOException e) {
            throw new Error(e);
        }
    }

    public static Table joinTables(Table table1, Table table2)  {
        ArrayList<HashMap<String, String>> newEntries = new ArrayList<>();
        ArrayList<String> newHeaders = new ArrayList<>();

        newHeaders.addAll(table1.getHeaders());

        for(String header : table2.getHeaders()){
            if(!newHeaders.contains(header)){
                newHeaders.add(header);
            }
        }

        for (int i = 0; i < table2.getEntries().size(); i++) {
            if(table1.getEntries().size() != 0 ){
                HashMap<String,String> aux = new HashMap<>();
                aux.putAll(table1.getEntries().get(i));
                aux.putAll(table2.getEntries().get(i));
                newEntries.add(aux);
            }
            else {
                newEntries.add(table2.getEntries().get(i));
            }
        }

        return new Table(newHeaders,newEntries);
    }

    public static List<Table> listCopy(List<Table> list){
        List<Table> newList = new ArrayList<>();
        for (Table t : list){
            newList.add(t.copy());
        }
        return newList;
    }

}
