package pt.up.fe.els2022.languageParser.commands;

import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.languageParser.Command;

import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddColumn implements Command {
    String fileId;

    String column;

    String def;
    String newFileId;
    public AddColumn(String commandLine) throws Error {
        Pattern p = Pattern.compile("^AddColumn +([^ ]+) +([^ ]+) +([^ ]+)(?: +as +([^ ]+))? *$");
        Matcher m = p.matcher(commandLine);

        if(m.find()) {
            if(m.groupCount() == 4){
                fileId = m.group(1);
                column = m.group(2);
                def = m.group(3);
                newFileId = m.group(4);
            }else{
                throw new Error("AddColumn command must be ' <file> <col> <defaultValue> [as <newfileID>]' ");
            }
        }else{
            throw new Error("AddColumn command must be ' <file> <col> <defaultValue> [as <newfileID>]' ");
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

    }
}