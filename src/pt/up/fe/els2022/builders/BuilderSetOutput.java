package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.BuilderExecutor;
import pt.up.fe.els2022.dslParser.Command;
import pt.up.fe.els2022.dslParser.commands.Read;
import pt.up.fe.els2022.dslParser.commands.SetOutput;

import java.util.List;

public class BuilderSetOutput implements InterfaceBuilder{
    SetOutput setOutput;
    BuilderExecutor builder;

    public BuilderSetOutput(BuilderExecutor builder) {
        this.setOutput = new SetOutput();
        this.builder = builder;
    }

    public BuilderSetOutput setFilesId(String filesId){
        setOutput.setFileId(filesId);
        return this;
    }

    public BuilderSetOutput addColumn(String name){
        this.setOutput.addColumn(name);
        return this;
    }


    public BuilderExecutor close(){
        return builder;
    }

    @Override
    public Command build() {
        return setOutput;
    }
}
