package pt.up.fe.els2022.languageParser.commands;

import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.TableOperations;
import pt.up.fe.els2022.languageParser.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetOutput implements Command {
    String fileId;

    ArrayList<String> outCols = new ArrayList<String>();

    public SetOutput(String commandLine) throws Error {
        Pattern p = Pattern.compile("^SetOutput +([^ ]+)");
        Matcher m = p.matcher(commandLine);

        if(m.find()) {
            if(m.groupCount() == 1){
                fileId = m.group(1).trim();
            }else{
                throw new Error("SetOutput must have only the file id defined, ' ");
            }
        }else{
            throw new Error("SetOutput must have only the file id defined, ' ");
        }
    }

    @Override
    public void addLine(String line) throws Error{
        outCols.add(line.trim());
    }

    @Override
    public void close() {
        if(outCols.size() == 0) throw new Error("SetOutput must have at least one output column");
    }

    @Override
    public void println() {

    }

    @Override
    public void execute(HashMap<String, Table> symbolTable) {
        symbolTable.get(fileId).setOutput(outCols);
    }
}