package pt.up.fe.els2022.languageParser.commands;

import pt.up.fe.els2022.languageParser.Command;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Sort implements Command {
    String fileId;
    String col;
    String direction;
    String newFileId;
    public Sort(String commandLine) throws Error {
        Pattern p = Pattern.compile("^Sort +([^ ]+) +([^ ]+) +(asc|desc)(?: as ([^ ]+))? *$");
        Matcher m = p.matcher(commandLine);

        if(m.find()) {
            if(m.groupCount() == 4){
                fileId = m.group(1);
                col = m.group(2);
                direction = m.group(3);
                newFileId = m.group(4);
            }else{
                throw new Error("Sort command must be ' <filename> <col> <direction; desc or asc> [as <newfileID>]' ");
            }
        }else{
            throw new Error("Sort command must be ' <filename> <col> <direction; desc or asc> [as <newfileID>]' ");
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

        String out = "Sort "+fileId+" "+col+" "+direction;
        if(newFileId != null){
            out += " as "+newFileId;
        }
        System.out.println(out);
    }
}