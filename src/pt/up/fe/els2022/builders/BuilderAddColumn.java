package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.BuilderExecutor;
import pt.up.fe.els2022.languageParser.Command;
import pt.up.fe.els2022.languageParser.commands.AddColumn;

public class BuilderAddColumn implements InterfaceBuilder {
    AddColumn addColumn;
    BuilderExecutor builder;

    public BuilderAddColumn(BuilderExecutor builder) {
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

    public BuilderExecutor close(){
        return builder;
    }

    @Override
    public Command build() {
        return addColumn;
    }
}
