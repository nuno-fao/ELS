package pt.up.fe.els2022.languageParser.commands;

import pt.up.fe.els2022.languageParser.Command;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Sort implements Command {
    String fileId;
    String col;
    String direction;
    String newFileId;
    public Sort(String commandLine) throws Error {
        commandLine = commandLine.substring(4).trim();

        String[] parts = commandLine.split(" ");
        if(parts.length != 3 && parts.length != 5){
            throw new Error("Sort command must be ' <filename> <col> <direction; desc or asc> [as <newfileID>]' ");
        }

        if(parts.length == 3){
            fileId = parts[0];
            col = parts[1];
            direction = parts[2];
        }else{
            fileId = parts[0];
            col = parts[1];
            direction = parts[2];
            if(!direction.equals("asc") && !direction.equals("desc")){
                throw new Error("Sort command must be 'asc' or 'desc', '"+direction+"' given. ");
            }
            if(!Objects.equals(parts[3], "as")){
                throw new Error("Sort command must be ' <filename> <col> <direction; desc or asc> [as <newfileID>]' ");
            }
            newFileId = parts[4];
        }
    }

    @Override
    public void addLine(String line) throws Error{
    }

    @Override
    public void close() {
    }

    @Override
    public void println() {

        String out = "Sort "+fileId+" "+col+" "+direction;
        if(newFileId != null){
            out += " as "+newFileId;
        }
        System.out.println(out);
    }
}