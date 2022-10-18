package pt.up.fe.els2022.languageParser.commands;

import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.TableOperations;
import pt.up.fe.els2022.languageParser.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AddColumn implements Command {
    String fileId;
    String column;
    String def;
    String newFileId;

    public String getFileId() {
        return fileId;
    }

    public String getColumn() {
        return column;
    }

    public String getDef() {
        return def;
    }

    public String getNewFileId() {
        return newFileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public void setColumn(String column) {
        this.column = column;
    }

    public void setDef(String def) {
        this.def = def;
    }

    public void setNewFileId(String newFileId) {
        this.newFileId = newFileId;
    }

    public AddColumn(){}

    @Override
    public void addLine(String line) throws Error{}

    @Override
    public void close() {}

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
            TableOperations.addColumn(newTable,column,def);
            symbolTable.put(newFileId,newTable);
        }
        else{
            TableOperations.addColumn(symbolTable.get(fileId),column,def);
        }
    }
}