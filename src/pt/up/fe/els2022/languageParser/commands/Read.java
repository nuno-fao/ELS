package pt.up.fe.els2022.languageParser.commands;

import pt.up.fe.els2022.languageParser.Command;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Read implements Command {
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
                throw new Error("Sort command must be 'Write <XML|CSV> <fileId> <filePath>' ");
            }
        }else{
            throw new Error("Sort command must be 'Write <XML|CSV> <fileId> <filePath>' ");
        }
    }

    @Override
    public void addLine(String line) throws Error{
        cols.add(new Column(line));
    }

    @Override
    public void close() {

    }

    @Override
    public void println() {
        System.out.println("Read "+filePath+" as "+fileID);
        for (var c:cols) {
            c.println();
        }
        System.out.println("End");
    }

    @Override
    public void execute() {

    }
}

class Column{
    String initName;
    String finalName;
    public Column(String col) throws Error{
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
