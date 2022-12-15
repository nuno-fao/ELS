package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.dslParser.CommandHolder;
import pt.up.fe.els2022.dslParser.Command;
import pt.up.fe.els2022.dslParser.commands.SetOutput;

public class BuilderSetOutput implements InterfaceBuilder{
    SetOutput setOutput;
    CommandHolder builder;

    public BuilderSetOutput(CommandHolder builder) {
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


    public CommandHolder close(){
        return builder;
    }

    @Override
    public Command build() {
        return setOutput;
    }
}
