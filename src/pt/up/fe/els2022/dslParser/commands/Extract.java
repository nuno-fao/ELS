package pt.up.fe.els2022.dslParser.commands;

import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.TableOperations;
import pt.up.fe.els2022.builders.InterfaceBuilder;
import pt.up.fe.els2022.dslParser.Command;

import java.util.*;

public class Extract implements Command {
    Set<Integer> lines;
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

    public Set<Integer> getLines() {
        return lines;
    }

    public void setLines(Set<Integer> lines) {
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
        List<Table> copyList = TableOperations.listCopy(symbolTable.get(fileId));
        if(this.columns != null) {
            for (String col : columns) {
                TableOperations.removeColumn(copyList, col);
            }
        }
        for(Table t: copyList){
            ArrayList<Integer> removeList = new ArrayList<>();
            for (int i = 1;i < t.getEntries().size()+1;i++){
                if(!lines.contains(i)){
                    removeList.add(i);
                }
            }
            for(int i = removeList.size()-1;i >=0;i--){
                t.getEntries().remove(removeList.get(i) -1);
            }
        }
        if(newFileId != null)
            symbolTable.put(newFileId , copyList);
        else
            symbolTable.put(fileId , copyList);
    }
}
