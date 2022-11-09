package pt.up.fe.els2022.dslParser;

import pt.up.fe.els2022.BuilderExecutor;
import pt.up.fe.els2022.builders.BuilderRead;
import pt.up.fe.els2022.builders.BuilderSetOutput;
import pt.up.fe.els2022.dslParser.commands.*;

import java.io.*;
import java.util.*;
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
    public void parse() throws IOException {
        BufferedReader reader;
        reader = new BufferedReader(languageFile);
        String line = reader.readLine();

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
                    lineCounter += ParseCommandRead(line,reader);
                } else if (line.startsWith("SetOutput")) {
                    lineCounter += ParseCommandSetOutput(line,reader);
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
                }else if (line.startsWith("Join")) {
                    ParseCommandJoin(line);
                }else if (line.startsWith("Extract")) {
                    ParseCommandExtract(line);
                } else {
                    throw new Error("Command not found around ");
                }
            }catch (Error e) {
                throw new Error(e.getMessage() +"LINE: " + lineCounter);
            }
            line = reader.readLine();
        }

        reader.close();
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
    void ParseCommandJoin(String commandLine){
        Pattern p = Pattern.compile("^Join +([^ ]+)(?: +as +([^ ]+))? *$");
        Matcher m = p.matcher(commandLine);

        if(m.find()) {
            if(m.groupCount() == 2){
                var b = builder.Join();
                var fileIds = Arrays.asList(m.group(1).split(","));
                for(int i = 0;i < fileIds.size();i++){
                    b = b.addFileId(fileIds.get(i).trim());
                }
                b.setNewFileId(m.group(2));
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

    void ParseCommandExtract(String commandLine){
        Pattern p = Pattern.compile("^Extract +([^ ]+)(?: +Columns +([^ ]+))?(?: +Lines +([^ ]+))?(?: +as ([^ ]+))? *$");
        Matcher m = p.matcher(commandLine);

        var a = m.groupCount();

        if(m.find()) {
            if(m.groupCount() == 4){
                Set<Integer> lines = null;
                List<String> columns = null;
                if(m.group(3) != null){
                    lines = new HashSet<>();
                    for(String line:m.group(3).split(",")){
                        lines.add(Integer.parseInt(line));
                    }
                }if(m.group(2) != null){
                    columns = Arrays.asList(m.group(2).split(","));
                }
                builder.extract()
                        .setFileId(m.group(1))
                        .setColumns(columns)
                        .setLines(lines)
                        .setNewFileId(m.group(4));
            }else{
                throw new Error("Sort command must be ' <filename> <col> <direction; desc or asc> [as <newfileID>]' ");
            }
        }else{
            throw new Error("Sort command must be ' <filename> <col> <direction; desc or asc> [as <newfileID>]' ");
        }
    }

    int ParseCommandRead(String commandLine,BufferedReader reader) throws IOException{
        int lineCounter = 0;

        Pattern p = Pattern.compile("^Read +([^ ]+)(?: +(TEXT|JSON|XML))? +as +([^ ]+) *$");
        Matcher m = p.matcher(commandLine);

        BuilderRead b = builder.read();

        if(m.find()) {
            if(m.groupCount() == 3){
                List<String> filePath =  List.of(m.group(1).split(","));
                List<String> fileID = List.of(m.group(3).split(","));

                b
                        .setFilesPaths(filePath)
                        .setFilesIds(fileID)
                        .setFileTYpe(FileType.fromString(m.group(2)));

                if(filePath.size() != fileID.size()) {
                    throw new Error("Read command incorrect, number of files must be equal to number of identifiers, ");
                }
            }else{
                throw new Error("Read command incorrect");
            }
        }else{
            throw new Error("Sort command incorrect");
        }
        commandLine = reader.readLine();

        while (commandLine != null) {
            commandLine = commandLine.trim();

            lineCounter++;
            if (commandLine.trim().length() == 0) {
                commandLine = reader.readLine();
                continue;
            }

            if(commandLine.startsWith("Parent")){
                List<String> elems = new ArrayList<>(Arrays.asList((commandLine.substring(6).trim().split(" "))));
                b.setParentElements(elems);
            } else if (commandLine.startsWith("Col")) {
                var col = commandLine.substring(3).trim();
                String[] parts = col.trim().split("=>");
                if(((parts.length != 2) && parts.length != 1) || (parts.length == 1 &&  col.contains("=>"))){
                    throw new Error("Col command must be '<originalName> => <newName>' ");
                }
                if(parts.length == 2) {
                    b.addColumn(parts[0].trim(),parts[1].trim());
                }else{
                    b.addColumn(col,col);
                }
            }else if (commandLine.startsWith("End")){
                b.close();
                return lineCounter;
            }else{
                throw new Error("Read command incorrect");
            }

            commandLine = reader.readLine();
        }
        return lineCounter;
    }

    int ParseCommandSetOutput(String commandLine,BufferedReader reader) throws IOException{
        int lineCounter = 0;

        Pattern p = Pattern.compile("^SetOutput +([^ ]+)");
        Matcher m = p.matcher(commandLine);

        BuilderSetOutput b = builder.setOutput();

        if(m.find()) {
            if(m.groupCount() == 1){
                b.setFilesId(m.group(1).trim());
            }else{
                throw new Error("SetOutput must have only the file id defined, ' ");
            }
        }else{
            throw new Error("SetOutput must have only the file id defined, ' ");
        }

        commandLine = reader.readLine();

        while (commandLine != null) {
            commandLine = commandLine.trim();
            lineCounter++;
            if (commandLine.startsWith("End")) {
                b.close();
                return lineCounter;
            }else {
                if (commandLine.trim().length() == 0) {
                    commandLine = reader.readLine();
                    continue;
                }
            }
            b.addColumn(commandLine.trim());

            commandLine = reader.readLine();
        }
        return lineCounter;
    }
}

