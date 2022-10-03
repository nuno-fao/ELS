package pt.up.fe.els2022.languageParser.commands;

import pt.up.fe.els2022.languageParser.Command;

import java.util.ArrayList;
import java.util.List;

public class Sort implements Command {
    String fileId;
    String col;
    String direction;
    String newFileId;
    public Sort(String commandLine) throws Error {
        commandLine = commandLine.substring(4).trim();

        String[] parts = commandLine.split(" ");
        if(parts.length != 3){
            throw new Error("Read command must be ' <filename> as <id>' ");
        }
    }

    @Override
    public void addLine(String line) throws Error{
    }

    @Override
    public void close() {

    }
}