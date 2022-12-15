package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.dslParser.CommandHolder;
import pt.up.fe.els2022.dslParser.Command;
import pt.up.fe.els2022.dslParser.commands.Join;

public class BuilderJoin implements InterfaceBuilder{
    Join join;
    CommandHolder builder;

    public BuilderJoin(CommandHolder builder) {
        this.join = new Join();
        this.builder = builder;
    }

    public BuilderJoin addFileId(String fileId) {
        join.addFileId(fileId);
        return this;
    }

    public BuilderJoin setDestinyColumn(String destinyColumn) {
        join.setDestinyColumn(destinyColumn);
        return this;
    }
    public BuilderJoin setNewFileId(String newFileId) {
        join.setNewFileId(newFileId);
        return this;
    }

    public CommandHolder close(){
        return builder;
    }

    @Override
    public Command build() {
        return join;
    }
}
