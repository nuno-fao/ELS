package pt.up.fe.els2022.dslParser.commands;

import pt.up.fe.els2022.Main;
import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.TableOperations;
import pt.up.fe.els2022.dslParser.Command;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class ReadDir implements Command {
    Table pile = new Table();
    String folderPath = null;
    String pileId = null;
    String fileId = null;

    public List<Command> getCommandList() {
        return commandList;
    }

    public void setCommandList(List<Command> commandList) {
        this.commandList = commandList;
    }

    List<Command> commandList = new ArrayList<>();

    public String getFolderPath() {
        return folderPath;
    }

    public void setFolderPath(String folderPath) {
        this.folderPath = folderPath;
    }

    public String getPileId() {
        return pileId;
    }

    public void setPileId(String pileId) {
        this.pileId = pileId;
    }

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    @Override
    public void println() {

    }

    @Override
    public void execute(HashMap<String, List<Table>> symbolTable) {
        File dir = new File(this.folderPath);
        for (File file : dir.listFiles()) {
            if (file.isDirectory()) {
                for (Command c : commandList) {
                    if (c instanceof Read) {
                        List<String> newFilePath = new ArrayList<>();
                        for (String i : ((Read) c).getRelativePath()) {
                            newFilePath.add(file.getPath()+"/" + i);
                        }
                        ((Read) c).setRelativeFilePath(newFilePath);
                    }
                    if (c instanceof Write) {
                        ((Write) c).setFilePath(file.getPath()+"/" +((Write) c).getFilePath());
                    }
                    c.execute(symbolTable);
                }
            }
            if(pileId != null){
                for (Table t:symbolTable.get(pileId)){
                    pile = TableOperations.mergeTables(pile,t);
                }
            }
        }
        symbolTable.put(fileId, Collections.singletonList(pile));
    }
}
