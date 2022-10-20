package pt.up.fe.els2022.dslParser.commands;

import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.TableOperations;
import pt.up.fe.els2022.dslParser.Command;

import java.util.HashMap;
import java.util.Objects;

public class Write implements Command {
    String type;
    String fileId;
    String filePath;

    public void setType(String type) {
        this.type = type;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getType() {
        return type;
    }

    public String getFileId() {
        return fileId;
    }

    public String getFilePath() {
        return filePath;
    }

    public Write() {}

    @Override
    public void println() {
        System.out.println("Write "+type+" "+fileId+" to "+filePath);
    }

    @Override
    public void execute(HashMap<String, Table> symbolTable) {
        if(Objects.equals(type, "CSV")){
            var a = symbolTable.get(fileId);
            TableOperations.write(symbolTable.get(fileId),filePath);
        }
        else{
            throw new Error("file type not supported");
        }
    }
}