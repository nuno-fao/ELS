package pt.up.fe.els2022.dslParser.commands;

import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.TableOperations;
import pt.up.fe.els2022.dslParser.Command;

import java.util.*;

public class Merge implements Command {
    List<String> fileIds = new ArrayList<>();
    String newFileId;

    String aggregate;

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

    public void setAggregate(String aggregate) {
        this.aggregate = aggregate;
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

    public String getAggregate() {
        return aggregate;
    }

    public String getDestinyColumn() {
        return destinyColumn;
    }

    public Merge() {
    }

    @Override
    public void println() {
        String out = "Merge ";

        for(var f: fileIds) {
           out+=f+",";
        }
        out = out.substring(0,out.length()-1);


        if(aggregate != null){
            out += " with "+aggregate + " on "+destinyColumn;
        }
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

            if(aggregate != null && aggregate.equals("Name")){
                TableOperations.addColumn(listCopy,destinyColumn,listCopy.get(0).getOrigin());
            }

            for(Table t: listCopy){
                newTable = TableOperations.mergeTables(newTable,t);
            }
        }

        if(newFileId != null)
            symbolTable.put(newFileId , Collections.singletonList(newTable));
        else
            symbolTable.put(fileIds.get(0) , Collections.singletonList(newTable));
    }
}