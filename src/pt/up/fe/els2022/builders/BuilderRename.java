package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.dslParser.CommandHolder;
import pt.up.fe.els2022.dslParser.Command;
import pt.up.fe.els2022.dslParser.commands.Rename;

import java.util.List;

public class BuilderRename implements InterfaceBuilder{
    Rename rename;
    CommandHolder builder;

    public BuilderRename(CommandHolder builder) {
        this.rename = new Rename();
        this.builder = builder;
    }

    public BuilderRename setNewFileId(String newFileId) {
        rename.setNewFileId(newFileId);
        return this;
    }
    public BuilderRename setFileId(String fileId) {
        rename.setFileId(fileId);
        return this;
    }
    public BuilderRename setColList(List<String> colllist) {
        rename.setColList(colllist);
        return this;
    }
    public BuilderRename setNewColllist(List<String> colllist) {
        rename.setNewNames(colllist);
        return this;
    }


    public CommandHolder close(){
        return builder;
    }

    @Override
    public Command build() {
        return rename;
    }
}
