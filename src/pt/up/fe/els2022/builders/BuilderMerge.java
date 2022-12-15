package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.dslParser.CommandHolder;
import pt.up.fe.els2022.dslParser.Command;
import pt.up.fe.els2022.dslParser.commands.Merge;

public class BuilderMerge implements InterfaceBuilder{
    Merge merge;
    CommandHolder builder;

    public BuilderMerge(CommandHolder builder) {
        this.merge = new Merge();
        this.builder = builder;
    }

    public BuilderMerge addFileId(String fileId) {
        merge.addFileId(fileId);
        return this;
    }

    public BuilderMerge setAggregate(String aggregate) {
        merge.setAggregate(aggregate);
        return this;
    }
    public BuilderMerge setDestinyColumn(String destinyColumn) {
        merge.setDestinyColumn(destinyColumn);
        return this;
    }
    public BuilderMerge setNewFileId(String newFileId) {
        merge.setNewFileId(newFileId);
        return this;
    }

    public CommandHolder close(){
        return builder;
    }

    @Override
    public Command build() {
        return merge;
    }
}
