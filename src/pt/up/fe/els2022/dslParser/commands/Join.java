package pt.up.fe.els2022.dslParser.commands;

import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.TableOperations;
import pt.up.fe.els2022.dslParser.Command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Join implements Command {
    List<String> fileIds = new ArrayList<>();
    String newFileId;
    String destinyColumn;

    public void setFileIds(List<String> fileIds) {
        this.fileIds = fileIds;
    }

    public void addFileId(String fileId) {
        this.fileIds.add(fileId);
    }

    public void setNewFileId(String newFileId) {
        this.newFileId = newFileId;
    }

    public void setDestinyColumn(String destinyColumn) {
        this.destinyColumn = destinyColumn;
    }

    public List<String> getFileIds() {
        return fileIds;
    }

    public String getNewFileId() {
        return newFileId;
    }

    public String getDestinyColumn() {
        return destinyColumn;
    }

    public Join() {
    }

    @Override
    public void println() {
        String out = "Join ";

        for(var f: fileIds) {
           out+=f+",";
        }
        out = out.substring(0,out.length()-1);

        if(newFileId != null){
            out += " as "+newFileId;
        }
        System.out.println(out);
    }

    @Override
    public void execute(HashMap<String, List<Table>> symbolTable) {
        Table newTable = new Table();

        for(String ID : fileIds){
            List<Table> listCopy = TableOperations.listCopy(symbolTable.get(ID));

            for (Table t: listCopy){
                newTable = TableOperations.joinTables(newTable,t);
            }
        }

        if(newFileId != null)
            symbolTable.put(newFileId , Collections.singletonList(newTable));
        else
            symbolTable.put(fileIds.get(0) , Collections.singletonList(newTable));
    }
}