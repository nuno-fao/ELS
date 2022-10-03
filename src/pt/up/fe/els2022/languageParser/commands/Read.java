package pt.up.fe.els2022.languageParser.commands;

import pt.up.fe.els2022.languageParser.Command;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;

public class Read implements Command {
    String filePath;
    String fileID;

    List<Column> cols;
    public Read(String commandLine) throws Error {
        commandLine = commandLine.substring(4).trim();

        String[] parts = commandLine.split(" ");
        if(parts.length != 3){
            throw new Error("Read command must be ' <filename> as <id>' ");
        }

        filePath = parts[0];
        fileID = parts[2];

        cols = new ArrayList<>();
    }

    @Override
    public void addLine(String line) throws Error{
        cols.add(new Column(line));
    }

    @Override
    public void close() {

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
}
