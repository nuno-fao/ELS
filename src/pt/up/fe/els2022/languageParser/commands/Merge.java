package pt.up.fe.els2022.languageParser.commands;

import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.TableOperations;
import pt.up.fe.els2022.languageParser.Command;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Merge implements Command {
    List<String> fileIds;
    String newFileId;

    String aggregate;

    String destinyColumn;
    public Merge(String commandLine) throws Error {
        Pattern p = Pattern.compile("^Merge +([^ ]+)(?: +with +(Name|Id) +on +([^ ]+))?(?: +as +([^ ]+))? *$");
        Matcher m = p.matcher(commandLine);

        if(m.find()) {
            if(m.groupCount() == 4){
                fileIds = Arrays.asList(m.group(1).split(","));
                for(int i = 0;i < fileIds.size();i++){
                    fileIds.set(i,fileIds.get(i).trim());
                }
                aggregate = m.group(2);
                destinyColumn = m.group(3);
                newFileId = m.group(4);
            }else{
                throw new Error("Merge command must be ' <file1>,<file2>,<file3>,<etc> [as <newfileID>]' ");
            }
        }else{
            throw new Error("Merge command must be ' <file1>,<file2>,<file3>,<etc> [as <newfileID>]' ");
        }
    }

    @Override
    public void addLine(String line) throws Error{
    }

    @Override
    public void close() {
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
    public void execute(HashMap<String, Table> symbolTable) {
        Table newTable = new Table();

        for(String ID : fileIds){
            Table tableCopy = symbolTable.get(ID).copy();

            if(aggregate.equals("Name")){
                TableOperations.addColumn(tableCopy,destinyColumn,tableCopy.getOrigin());
            }

            newTable = TableOperations.mergeTables(newTable,tableCopy);
        }

    }
}