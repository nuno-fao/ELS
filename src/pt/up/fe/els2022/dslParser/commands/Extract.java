package pt.up.fe.els2022.dslParser.commands;

import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.TableOperations;
import pt.up.fe.els2022.builders.InterfaceBuilder;
import pt.up.fe.els2022.dslParser.Command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Extract implements Command {
    List<Integer> lines;
    List<String> columns;
    String fileId;

    String newFileId;

    public String getNewFileId() {
        return newFileId;
    }

    public void setNewFileId(String newFileId) {
        this.newFileId = newFileId;
    }

    public Extract() {
    }

    public List<Integer> getLines() {
        return lines;
    }

    public void setLines(List<Integer> lines) {
        this.lines = lines;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Override
    public void println() {

    }

    @Override
    public void execute(HashMap<String, List<Table>> symbolTable) {
        /*List<Table> copyList = new ArrayList<>();
        for(Table t: symbolTable.get(fileId)){
            Table copy
        }
        if(newFileId != null)
            symbolTable.put(newFileId , tables);
        else
            symbolTable.put(fileId , tables);*/
    }
}
