package pt.up.fe.els2022.dslParser.commands;

import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.TableOperations;
import pt.up.fe.els2022.dslParser.Command;

import java.util.*;

public class Sort implements Command {
    String fileId;
    String col;
    String direction;
    String newFileId;

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public void setCol(String col) {
        this.col = col;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public void setNewFileId(String newFileId) {
        this.newFileId = newFileId;
    }

    public String getFileId() {
        return fileId;
    }

    public String getCol() {
        return col;
    }

    public String getDirection() {
        return direction;
    }

    public String getNewFileId() {
        return newFileId;
    }

    public Sort() {
    }

    @Override
    public void println() {

        String out = "Sort "+fileId+" "+col+" "+direction;
        if(newFileId != null){
            out += " as "+newFileId;
        }
        System.out.println(out);
    }

    @Override
    public void execute(HashMap<String, List<Table>> symbolTable) {

        boolean ascending = Objects.equals(direction, "asc");

        if(newFileId!=null){
            List<Table> newList = TableOperations.listCopy(symbolTable.get(fileId));
            TableOperations.sortBy(newList,col,ascending);
            symbolTable.put(newFileId,newList);
        }
        else{
            TableOperations.sortBy(symbolTable.get(fileId),col,ascending);
        }

    }
}