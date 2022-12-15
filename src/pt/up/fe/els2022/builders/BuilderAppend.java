package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.dslParser.CommandHolder;
import pt.up.fe.els2022.dslParser.Command;
import pt.up.fe.els2022.dslParser.commands.Append;

public class BuilderAppend implements InterfaceBuilder{
    Append append;
    CommandHolder builder;

    public BuilderAppend(CommandHolder builder) {
        this.append = new Append();
        this.builder = builder;
    }

    public BuilderAppend setFileId(String fileId) {
        append.setFileId(fileId);
        return this;
    }
    public BuilderAppend setSuffix(String suffix) {
        append.setSuffix(suffix);
        return this;
    }
    public BuilderAppend setNewFileId(String newFileId) {
        append.setNewFileId(newFileId);
        return this;
    }


    public CommandHolder close(){
        return builder;
    }

    @Override
    public Command build() {
        return append;
    }
}
