package pt.up.fe.els2022.dslParser.commands;

import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.dslParser.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SetOutput implements Command {
    String fileId;

    ArrayList<String> outCols = new ArrayList<String>();

    public SetOutput() throws Error {}

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public void addColumn(String column) throws Error{
        outCols.add(column.trim());
    }

    @Override
    public void println() {

    }

    @Override
    public void execute(HashMap<String, List<Table>> symbolTable) {
        for (Table t : symbolTable.get(fileId)){
            t.setOutput(outCols);
        }
    }
}