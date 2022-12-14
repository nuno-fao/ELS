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
    public static void addColumn(List<Table> tables, String name, String value){
        for(Table table : tables) {
            table.getHeaders().add(name);
            List<HashMap<String, String>> entries = table.getEntries();
            for (HashMap<String, String> entry : entries) {
                entry.put(name, value);
            }
            if(!table.getOutput().contains(name))
                table.getOutput().add(name);
        }
    }

    public static void removeColumn(List<Table> tables, String name){
        for(Table table : tables) {
            table.getHeaders().remove(name);
            List<HashMap<String, String>> entries = table.getEntries();
            for (HashMap<String, String> entry : entries) {
                entry.remove(name);
            }
            table.getOutput().remove(name);
        }
    }



    private static Pattern pattern = Pattern.compile("-?\\d+(\\.\\d+)?");

    public static boolean isNumeric(String strNum) {
        if (strNum == null) {
            return false;
        }
        return pattern.matcher(strNum).matches();
    }

    public static void sortBy(List<Table> tables, String col, boolean ascending){
        for(Table table : tables) {
            List<HashMap<String, String>> entries = table.getEntries();

            boolean is_numeric = true;
            for (var v : entries) {
                if (!isNumeric(v.get(col))) {
                    is_numeric = false;
                    break;
                }
            }
            boolean finalIs_numeric = is_numeric;
            entries.sort(new Comparator<HashMap<String, String>>() {
                public int compare(HashMap<String, String> o1, HashMap<String, String> o2) {
                    // compare two instance of `Score` and return `int` as result.
                    if (finalIs_numeric) {
                        return ascending ?  Float.compare(Float.parseFloat(o1.get(col)), Float.parseFloat(o2.get(col))) : Float.compare(Float.parseFloat(o2.get(col)), Float.parseFloat(o1.get(col)));
                    } else {
                        return ascending ? o1.get(col).compareTo(o2.get(col)) : o2.get(col).compareTo(o1.get(col));
                    }
                }
            });
        }
    }

    public static Table mergeTables(Table table1, Table table2){
        ArrayList<HashMap<String, String>> newEntries = new ArrayList<>();
        ArrayList<String> newHeaders = new ArrayList<>();
        ArrayList<String> newOutput = new ArrayList<>();

        newHeaders.addAll(table1.getHeaders());
        for(String header : table2.getHeaders()){
            if(!newHeaders.contains(header)){
                newHeaders.add(header);
            }
        }

        newOutput.addAll(table1.getOutput());
        for(String output : table2.getOutput()){
            if(!newOutput.contains(output)){
                newOutput.add(output);
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

        Table outTable = new Table(newHeaders,newEntries);
        outTable.setOutput(newOutput);

        return outTable;

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

        for (int i = 0; i < Math.max(table2.getEntries().size(),table1.getEntries().size()); i++) {
            if(table1.getEntries().size() != 0 ){
                HashMap<String,String> aux = new HashMap<>();
                if(table1.getEntries().size() > i){
                    aux.putAll(table1.getEntries().get(i));
                }
                if(table2.getEntries().size() > i){
                    aux.putAll(table2.getEntries().get(i));
                }
                newEntries.add(aux);
            }
            else {
                newEntries.add(table2.getEntries().get(i));
            }
        }
        Table outTable = new Table(newHeaders, newEntries);
        ArrayList<String> newOutput = new ArrayList<>();
        newOutput.addAll(table1.getOutput());
        newOutput.addAll(table2.getOutput());
        outTable.setOutput(newOutput);
        return outTable;
    }

    public static List<Table> listCopy(List<Table> list){
        List<Table> newList = new ArrayList<>();
        for (Table t : list){
            newList.add(t.copy());
        }
        return newList;
    }

    public static void getSums(List<Table> tableList){
        for(Table table : tableList){
            HashMap<String, String>sums = new HashMap<>();
            for (String header: table.getHeaders()) {
                double auxFloat = 0.0;

                for (HashMap<String, String> entry: table.getEntries()){
                    try
                    {
                        auxFloat += Double.parseDouble(entry.get(header));
                    }
                    catch (NumberFormatException e)
                    {
                        sums.put(header,null);
                        break;
                    }
                }
                sums.put(header,Double.toString(auxFloat));

            }
            table.getEntries().add(sums);
        }

    }

    public static void getAverage(List<Table> tableList){
        for(Table table : tableList) {
            HashMap<String, String> sums = new HashMap<>();
            for (String header : table.getHeaders()) {
                double auxFloat = 0.0;
                int i = 0;

                for (HashMap<String, String> entry : table.getEntries()) {
                    try {
                        auxFloat += Double.parseDouble(entry.get(header));
                        i++;
                    } catch (NumberFormatException e) {
                        sums.put(header, null);
                        break;
                    }
                }
                sums.put(header, Double.toString(auxFloat / i));
            }
            table.getEntries().add(sums);
        }
    }

    public static void rename(List<Table> tables, List<String> targets, List<String> newNames) {
        for(Table table : tables){ //iterar tabelas
            for(int i=0; i<targets.size();i++){ //iterar pelas colunas
                if(table.getHeaders().contains(targets.get(i))){
                    String header = targets.get(i);
                    for(HashMap<String, String> entry : table.getEntries()){ //ir a cada entry, meter no novo nome, apagar antigo
                        entry.put(newNames.get(i),entry.get(header));
                        entry.remove(header);
                    }
                    table.getHeaders().set(i,newNames.get(i));
                }
            }
        }
    }

    public static void append(List<Table> tables, String suffix){
        for(Table table : tables){ //iterar tabelas
            for(int i=0; i<table.getHeaders().size();i++){ //iterar pelas colunas
                String header = table.getHeaders().get(i);
                for(HashMap<String, String> entry : table.getEntries()){ //ir a cada entry, meter no novo nome, apagar antigo
                    entry.put(header + suffix,entry.get(header));
                    entry.remove(header);
                }
                table.getHeaders().set(i,header + suffix);
            }
        }
    }

    /*public static void compress(List<Table> tables, String suffix){
        for(Table table : tables){
            HashMap<String,String> newEntries = new HashMap<>();
            ArrayList<String> newHeaders = new ArrayList<>();
            for(int i=1;i<table.getEntries().size();i++){
                for(int j=0;j<table.getHeaders().size();j++){
                    String newHeader = table.getHeaders().get(j) + suffix + i
                    String value = table.getEntries().get(i).get(table.getHeaders().get(j));

                }
            }
        }

    }*/

}
