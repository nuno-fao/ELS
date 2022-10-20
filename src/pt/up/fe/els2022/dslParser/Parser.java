package pt.up.fe.els2022.dslParser;

import pt.up.fe.els2022.BuilderExecutor;
import pt.up.fe.els2022.builders.BuilderRead;
import pt.up.fe.els2022.dslParser.commands.*;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private BuilderExecutor builderExecutor = new BuilderExecutor();
    private FileReader languageFile;
    private List<Command> commands = new ArrayList<>();

    private BuilderExecutor builder = new BuilderExecutor();
    public Parser(String filePath) throws FileNotFoundException {
        languageFile = new FileReader(filePath);
    }

    public BuilderExecutor getBuilder() {
        return builder;
    }

    public List<Command> parse() throws IOException {
        BufferedReader reader;
        reader = new BufferedReader(languageFile);
        String line = reader.readLine();
        Command currentCommand = null;

        int lineCounter = 0;

        while (line != null){
            lineCounter++;

            if(line.trim().length() == 0) {
                line = reader.readLine();
                continue;
            }

            try {
                if(line.startsWith("#")) {
                    continue;
                } else if (line.startsWith("Read")) {
                    ParseCommandRead(line,reader);
                } else if (line.startsWith("SetOutput")) {
                    currentCommand = new SetOutput(line);
                } else if (line.startsWith("Sort")) {
                    ParseCommandSort(line);
                }else if (line.startsWith("Merge")) {
                    ParseCommandMerge(line);
                }else if (line.startsWith("RemoveColumn")) {
                    ParseCommandRemoveColumn(line);
                }else if (line.startsWith("AddColumn")) {
                    ParseCommandAddColumn(line);
                }else if (line.startsWith("Write")) {
                    ParseCommandWrite(line);
                } else {
                        throw new Error("Command not found around ");
                }
            }catch (Error e) {
                throw new Error(e.getMessage() +"LINE: " + lineCounter);
            }
            line = reader.readLine();
        }

        reader.close();
        return commands;
    }

    void println(){
        System.out.println("--------------------------------");
        for (var a:commands) {
            a.println();
        }
        System.out.println("--------------------------------");
    }

    void ParseCommandAddColumn(String commandLine){
        Pattern p = Pattern.compile("^AddColumn +([^ ]+) +([^ ]+) +([^ ]+)(?: +as +([^ ]+))? *$");
        Matcher m = p.matcher(commandLine);

        if(m.find()) {
            if(m.groupCount() == 4){
               builder.addColumn()
                        .setFileId(m.group(1))
                        .setColumn(m.group(2))
                        .setDefault(m.group(3))
                        .setNewFileId(m.group(4))
                        .close();
            }else{
                throw new Error("AddColumn command must be ' <file> <col> <defaultValue> [as <newfileID>]' ");
            }
        }else{
            throw new Error("AddColumn command must be ' <file> <col> <defaultValue> [as <newfileID>]' ");
        }
    }
    void ParseCommandRemoveColumn(String commandLine){
        Pattern p = Pattern.compile("^RemoveColumn +([^ ]+) +([^ ]+)(?: +as +([^ ]+))? *$");
        Matcher m = p.matcher(commandLine);

        if(m.find()) {
            if(m.groupCount() == 3){
                builder.removeColumn()
                        .setFileId(m.group(1))
                        .setColumn(m.group(2))
                        .setNewFileId(m.group(3))
                        .close();
            }else{
                throw new Error("RemoveColumn command must be ' <file> <col> [as <newfileID>]' ");
            }
        }else{
            throw new Error("RemoveColumn command must be ' <file> <col> [as <newfileID>]' ");
        }
    }
    void ParseCommandWrite(String commandLine){
        Pattern p = Pattern.compile("^Write +(XML|CSV) +([^ ]+) +to +([^ ]+) *$");
        Matcher m = p.matcher(commandLine);

        if(m.find()) {
            if(m.groupCount() == 3){
                builder.write()
                        .setType(m.group(1))
                        .setFileId(m.group(2))
                        .setFilePath(m.group(3))
                        .close();
            }else{
                throw new Error("Sort command must be 'Write <XML|CSV> <fileId> to <filePath>' ");
            }
        }else{
            throw new Error("Sort command must be 'Write <XML|CSV> <fileId> to <filePath>' ");
        }
    }
    void ParseCommandMerge(String commandLine){
        Pattern p = Pattern.compile("^Merge +([^ ]+)(?: +with +(Name|Id) +on +([^ ]+))?(?: +as +([^ ]+))? *$");
        Matcher m = p.matcher(commandLine);

        if(m.find()) {
            if(m.groupCount() == 4){
                var b = builder.merge();
                var fileIds = Arrays.asList(m.group(1).split(","));
                for(int i = 0;i < fileIds.size();i++){
                    b = b.addFileId(fileIds.get(i).trim());
                }
                b.setAggregate(m.group(2))
                        .setDestinyColumn(m.group(3))
                        .setNewFileId(m.group(4));
            }else{
                throw new Error("Merge command must be ' <file1>,<file2>,<file3>,<etc> [as <newfileID>]' ");
            }
        }else{
            throw new Error("Merge command must be ' <file1>,<file2>,<file3>,<etc> [as <newfileID>]' ");
        }
    }
    void ParseCommandSort(String commandLine){
        Pattern p = Pattern.compile("^Sort +([^ ]+) +([^ ]+) +(asc|desc)(?: as ([^ ]+))? *$");
        Matcher m = p.matcher(commandLine);

        if(m.find()) {
            if(m.groupCount() == 4){
                builder.sort()
                        .setFileId(m.group(1))
                        .setCol(m.group(2))
                        .setDirection(m.group(3))
                        .setNewFileId(m.group(4));
            }else{
                throw new Error("Sort command must be ' <filename> <col> <direction; desc or asc> [as <newfileID>]' ");
            }
        }else{
            throw new Error("Sort command must be ' <filename> <col> <direction; desc or asc> [as <newfileID>]' ");
        }
    }

    int ParseCommandRead(String commandLine,BufferedReader reader) throws IOException{
        int lineCounter = 1;

        Pattern p = Pattern.compile("^Read +([^ ]+) +as +([^ ]+) *$");
        Matcher m = p.matcher(commandLine);

        BuilderRead b = builder.read();

        if(m.find()) {
            if(m.groupCount() == 2){
                List<String> filePath =  List.of(m.group(1).split(","));
                List<String> fileID = List.of(m.group(2).split(","));

                b
                        .setFilesPaths(filePath)
                        .setFilesIds(fileID);

                if(filePath.size() != fileID.size()) {
                    throw new Error("Read command incorrect, number of files must be equal to number of identifiers, ");
                }
            }else{
                throw new Error("Read command incorrect");
            }
        }else{
            throw new Error("Sort command incorrect");
        }

        while (commandLine != null) {
            lineCounter++;
            if (commandLine.trim().length() == 0) {
                commandLine = reader.readLine();
                continue;
            }

            commandLine = commandLine.trim();
            if(commandLine.startsWith("Parent")){
                List<String> elems = List.of(commandLine.substring(6).trim().split(","));
                b.setParentElements(elems);
            } else if (commandLine.startsWith("Col")) {

                cols.add(new Column(line));
            }else{
                throw new Error("Read command incorrect");
            }

            commandLine = reader.readLine();
        }
        return lineCounter;
    }
}

