package pt.up.fe.els2022.languageParser.commands;

import pt.up.fe.els2022.languageParser.Command;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class Merge implements Command {
    List<String> fileIds;
    String newFileId;
    public Merge(String commandLine) throws Error {
        commandLine = commandLine.substring(5).trim();

        String[] parts = commandLine.split(" ");
        if(parts.length != 1 && parts.length != 3){
            throw new Error("Sort command must be ' <file1>,<file2>,<file3>,<etc> [as <newfileID>]' ");
        }

        fileIds = Arrays.asList(parts[0].split(","));
        for(int i = 0;i < fileIds.size();i++){
            fileIds.set(i,fileIds.get(i).trim());
        }

        if(parts.length == 1){
            newFileId = fileIds.get(0).trim();
        }else{
            if(!Objects.equals(parts[1], "as")){
                throw new Error("Sort command must be ' <file1>,<file2>,<file3>,<etc> [as <newfileID>]' ");
            }
            newFileId = parts[2].trim();
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

        out += " as "+newFileId;
        if(newFileId != null){
            out += " as "+newFileId;
        }
        System.out.println(out);
    }
}