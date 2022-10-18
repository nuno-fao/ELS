package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.BuilderExecutor;
import pt.up.fe.els2022.languageParser.Command;
import pt.up.fe.els2022.languageParser.commands.RemoveColumn;

public class BuilderRemoveColumn implements InterfaceBuilder{
    RemoveColumn removeColumn;
    BuilderExecutor builder;

    public BuilderRemoveColumn(BuilderExecutor builder) {
        this.removeColumn = new RemoveColumn();
        this.builder = builder;
    }

    public BuilderRemoveColumn setFileId(String id){
        removeColumn.setFileId(id);
        return this;
    }
    public BuilderRemoveColumn setColumn(String column){
        removeColumn.setColumn(column);
        return this;
    }
    public BuilderRemoveColumn setNewFileId(String newFileId){
        removeColumn.setNewFileId(newFileId);
        return this;
    }

    public BuilderExecutor close(){
        return builder;
    }


    @Override
    public Command build() {
        return removeColumn;
    }
}
