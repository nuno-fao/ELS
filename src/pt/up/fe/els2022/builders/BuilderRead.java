package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.BuilderExecutor;
import pt.up.fe.els2022.dslParser.Command;
import pt.up.fe.els2022.dslParser.commands.FileType;
import pt.up.fe.els2022.dslParser.commands.Read;

import java.util.List;

public class BuilderRead implements InterfaceBuilder{
    Read read;
    BuilderExecutor builder;

    public BuilderRead(BuilderExecutor builder) {
        this.read = new Read();
        this.builder = builder;
    }

    public BuilderRead setFilesPaths(List<String> filesPaths){
        read.setFilePath(filesPaths);
        return this;
    }
    public BuilderRead setFilesIds(List<String> filesIds){
        read.setFileID(filesIds);
        return this;
    }

    public BuilderRead setParentElements(List<String> parentElements){
        this.read.setParentElement(parentElements);
        return this;
    }

    public BuilderRead setFileTYpe(FileType type){
        this.read.setType(type);
        return this;
    }

    public void addColumn(String initName, String finalName){
        this.read.addColumn(initName, finalName);
    }


    public BuilderExecutor close(){
        return builder;
    }

    @Override
    public Command build() {
        return read;
    }
}
