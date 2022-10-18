package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.BuilderExecutor;
import pt.up.fe.els2022.languageParser.Command;
import pt.up.fe.els2022.languageParser.commands.Merge;
import pt.up.fe.els2022.languageParser.commands.Write;

public class BuilderMerge implements InterfaceBuilder{
    Merge merge;
    BuilderExecutor builder;

    public BuilderMerge(BuilderExecutor builder) {
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

    public BuilderExecutor close(){
        return builder;
    }

    @Override
    public Command build() {
        return merge;
    }
}
