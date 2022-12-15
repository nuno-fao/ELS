package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.dslParser.CommandHolder;
import pt.up.fe.els2022.dslParser.Command;
import pt.up.fe.els2022.dslParser.commands.AddColumn;

public class BuilderAddColumn implements InterfaceBuilder {
    AddColumn addColumn;
    CommandHolder builder;

    public BuilderAddColumn(CommandHolder builder) {
        this.addColumn = new AddColumn();
        this.builder = builder;
    }

    public BuilderAddColumn setFileId(String id){
        addColumn.setFileId(id);
        return this;
    }
    public BuilderAddColumn setColumn(String column){
        addColumn.setColumn(column);
        return this;
    }
    public BuilderAddColumn setNewFileId(String newFileId){
        addColumn.setNewFileId(newFileId);
        return this;
    }
    public BuilderAddColumn setDefault(String defaultValue){
        addColumn.setDef(defaultValue);
        return this;
    }

    public CommandHolder close(){
        return builder;
    }

    @Override
    public Command build() {
        return addColumn;
    }
}
