package pt.up.fe.els2022.languageParser.commands;

import pt.up.fe.els2022.languageParser.Command;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Merge implements Command {
    List<String> fileIds;
    String newFileId;
    public Merge(String commandLine) throws Error {
        Pattern p = Pattern.compile("^Merge +([^ ]+)(?: as ([^ ]+))?");
        Matcher m = p.matcher(commandLine);

        if(m.find()) {
            if(m.groupCount() == 2){
                fileIds = Arrays.asList(m.group(1).split(","));
                for(int i = 0;i < fileIds.size();i++){
                    fileIds.set(i,fileIds.get(i).trim());
                }
                newFileId = m.group(2);
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

        if(newFileId != null){
            out += " as "+newFileId;
        }
        System.out.println(out);
    }
}