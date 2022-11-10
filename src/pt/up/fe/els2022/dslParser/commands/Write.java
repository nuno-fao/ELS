package pt.up.fe.els2022.dslParser.commands;

import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.TableOperations;
import pt.up.fe.els2022.dslParser.Command;

import java.util.HashMap;
import java.util.List;
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
    public void execute(HashMap<String, List<Table>> symbolTable) {
        if(Objects.equals(type, "CSV")){
            List<Table> list = symbolTable.get(fileId);
            if(list.size() == 1 ){
                if(!filePath.endsWith(".csv")){
                    TableOperations.write(symbolTable.get(fileId).get(0),filePath + ".csv");
                }
                else{
                    TableOperations.write(symbolTable.get(fileId).get(0),filePath);
                }
            }else{
                int i = 0;
                for (Table t : list){
                    TableOperations.write(t,filePath+"_"+i+".csv");
                    i++;
                }
            }
        }
        else{
            throw new Error("file type not supported");
        }
    }
}