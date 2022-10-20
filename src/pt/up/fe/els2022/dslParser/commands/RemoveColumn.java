package pt.up.fe.els2022.dslParser.commands;

import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.TableOperations;
import pt.up.fe.els2022.dslParser.Command;

import java.util.HashMap;

public class RemoveColumn implements Command {
    String fileId;

    String column;
    String newFileId;

    public String getFileId() {
        return fileId;
    }

    public String getColumn() {
        return column;
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

    public void setNewFileId(String newFileId) {
        this.newFileId = newFileId;
    }

    public RemoveColumn() throws Error {}

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