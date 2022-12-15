package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.dslParser.CommandHolder;
import pt.up.fe.els2022.dslParser.Command;
import pt.up.fe.els2022.dslParser.commands.Sum;

public class BuilderSum implements InterfaceBuilder {

    Sum sum;
    CommandHolder builder;

    public BuilderSum(CommandHolder builder){
        this.sum = new Sum();
        this.builder = builder;
    }

    public BuilderSum setFileId(String id){
        sum.setFileId(id);
        return this;
    }

    public BuilderSum setNewFileId(String id){
        sum.setNewFileId(id);
        return this;
    }

    public CommandHolder close(){
        return builder;
    }

    @Override
    public Command build() {
        return sum;
    }
}
