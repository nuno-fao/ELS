package pt.up.fe.els2022.languageParser.commands;

import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.TableOperations;
import pt.up.fe.els2022.languageParser.Command;

import java.util.HashMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Write implements Command {
    String type;
    String fileId;
    String filePath;
    public Write(String commandLine) throws Error {
        Pattern p = Pattern.compile("^Write +(XML|CSV) +([^ ]+) +([^ ]+) *$");
        Matcher m = p.matcher(commandLine);

        if(m.find()) {
            if(m.groupCount() == 3){
                type = m.group(1);
                fileId = m.group(2);
                filePath = m.group(3);
            }else{
                throw new Error("Sort command must be 'Write <XML|CSV> <fileId> <filePath>' ");
            }
        }else{
            throw new Error("Sort command must be 'Write <XML|CSV> <fileId> <filePath>' ");
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
        System.out.println("Write "+type+" "+fileId+" "+filePath);
    }

    @Override
    public void execute(HashMap<String, Table> symbolTable) {
        if(Objects.equals(type, ".csv")){
            TableOperations.write(symbolTable.get(fileId),filePath);
        }
        else{
            throw new Error("file type not supported");
        }
    }
}