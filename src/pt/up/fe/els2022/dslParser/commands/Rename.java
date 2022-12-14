package pt.up.fe.els2022.dslParser.commands;

import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.TableOperations;
import pt.up.fe.els2022.dslParser.Command;

import java.util.HashMap;
import java.util.List;

public class Rename implements Command {

    String fileId;
    List<String> colList;
    List<String> newNames;
    String newFileId;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public List<String> getColList() {
        return colList;
    }

    public void setColList(List<String> colList) {
        this.colList = colList;
    }

    public List<String> getNewNames() {
        return newNames;
    }

    public void setNewNames(List<String> newNames) {
        this.newNames = newNames;
    }

    public String getNewFileId() {
        return newFileId;
    }

    public void setNewFileId(String newFileId) {
        this.newFileId = newFileId;
    }

    @Override
    public void println() {

    }

    @Override
    public void execute(HashMap<String, List<Table>> symbolTable) {
        if(newFileId!=null){
            List<Table> newList = TableOperations.listCopy(symbolTable.get(fileId));
            TableOperations.rename(newList,colList,newNames);
            symbolTable.put(newFileId,newList);
        }
        else{
            TableOperations.rename(symbolTable.get(fileId),colList,newNames);
        }
    }
}
