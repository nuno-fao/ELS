package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.dslParser.CMDHolder;
import pt.up.fe.els2022.dslParser.Command;
import pt.up.fe.els2022.dslParser.commands.RemoveColumn;

public class BuilderRemoveColumn implements InterfaceBuilder{
    RemoveColumn removeColumn;
    CMDHolder builder;

    public BuilderRemoveColumn(CMDHolder builder) {
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

    public CMDHolder close(){
        return builder;
    }


    @Override
    public Command build() {
        return removeColumn;
    }
}
