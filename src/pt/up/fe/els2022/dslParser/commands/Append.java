package pt.up.fe.els2022.dslParser.commands;

import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.TableOperations;
import pt.up.fe.els2022.dslParser.Command;

import java.util.HashMap;
import java.util.List;

public class Append implements Command {
    String fileId;
    String suffix;
    String newFileId;

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
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
            TableOperations.append(newList,suffix);
            symbolTable.put(newFileId,newList);
        }
        else{
            TableOperations.append(symbolTable.get(fileId),suffix);
        }
    }
}
