package pt.up.fe.els2022.languageParser.commands;

import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.XMLAdapter;
import pt.up.fe.els2022.languageParser.Command;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Read implements Command {
    String parentElement;
    String filePath;
    String fileID;

    List<Column> cols;
    public Read(String commandLine) throws Error {
        Pattern p = Pattern.compile("^Read +([^ ]+) +as +([^ ]+) *$");
        Matcher m = p.matcher(commandLine);

        if(m.find()) {
            if(m.groupCount() == 2){
                filePath = m.group(1);
                fileID = m.group(2);

                cols = new ArrayList<>();
            }else{
                throw new Error("Read command incorrect");
            }
        }else{
            throw new Error("Sort command incorrect");
        }
    }

    @Override
    public void addLine(String line) throws Error{
        line = line.trim();
        if(line.startsWith("Parent")){
            parentElement = line.substring(6).trim();
        } else if (line.startsWith("Col")) {
            cols.add(new Column(line));
        }else{
            throw new Error("Read command incorrect");
        }
    }

    @Override
    public void close() {
        if(parentElement == null) throw new Error("Read command must have Parent definition");
    }

    @Override
    public void println() {
        System.out.println("Read "+filePath+" as "+fileID);
        System.out.println("    Parent "+parentElement);
        for (var c:cols) {
            c.println();
        }
        System.out.println("End");
    }

    @Override
    public void execute(HashMap<String, Table> symbolTable) {
        try {
            List<String> headers = new ArrayList<>();
            List<String> elements = new ArrayList<>();

            for (Column column : cols) {
                headers.add(column.initName);
                elements.add(column.finalName);
            }

            HashMap<String, String> entry = XMLAdapter.parseFile(filePath, headers, elements, parentElement);

            for (Map.Entry<String, String> item : entry.entrySet()) {
                System.out.println("Key: " + item.getKey() + " Value: " + item.getValue());
            }
        }catch (Exception e) {
            throw new Error("Column does not exist in table '"+fileID+"'  ");
        }
    }
}

class Column{
    String initName;
    String finalName;
    public Column(String col) throws Error{
        col = col.substring(3).trim();
        String[] parts = col.trim().split("=>");
        if(((parts.length != 2) && parts.length != 1) || (parts.length == 1 &&  col.contains("=>"))){
            throw new Error("Col command must be '<originalName> => <newName>' ");
        }
        if(parts.length == 2) {
            initName = parts[0].trim();
            finalName = parts[1].trim();
        }else{
            initName = col;
            finalName = col;
        }
    }

    void println(){
        if(finalName.equals(initName)){
            System.out.println("    "+finalName);
        }else{
            System.out.println("    "+initName+" => "+finalName);
        }
    }
}
