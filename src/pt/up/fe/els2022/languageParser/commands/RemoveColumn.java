package pt.up.fe.els2022.languageParser.commands;

import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.TableOperations;
import pt.up.fe.els2022.languageParser.Command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RemoveColumn implements Command {
    String fileId;

    String column;
    String newFileId;
    public RemoveColumn(String commandLine) throws Error {
        Pattern p = Pattern.compile("^RemoveColumn +([^ ]+) +([^ ]+)(?: +as +([^ ]+))? *$");
        Matcher m = p.matcher(commandLine);

        if(m.find()) {
            if(m.groupCount() == 3){
                fileId = m.group(1);
                column = m.group(2);
                newFileId = m.group(3);
            }else{
                throw new Error("RemoveColumn command must be ' <file> <col> [as <newfileID>]' ");
            }
        }else{
            throw new Error("RemoveColumn command must be ' <file> <col> [as <newfileID>]' ");
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
        String out = "Remove "+fileId+" "+column;

        if(newFileId != null){
            out += " as "+newFileId;
        }
        System.out.println(out);
    }

    @Override
    public void execute(HashMap<String, Table> symbolTable) {
        if(newFileId!=null){
            Table newTable = symbolTable.get(fileId).copy();
            TableOperations.removeColumn(newTable,column);
            symbolTable.put(newFileId,newTable);
        }
        else{
            TableOperations.removeColumn(symbolTable.get(fileId),column);
        }
    }
}