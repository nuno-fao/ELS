package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.BuilderExecutor;
import pt.up.fe.els2022.dslParser.Command;
import pt.up.fe.els2022.dslParser.commands.Extract;
import pt.up.fe.els2022.dslParser.commands.RemoveColumn;

import java.util.List;
import java.util.Set;

public class BuilderExtract implements InterfaceBuilder{
    Extract extract;
    BuilderExecutor builder;

    public BuilderExtract(BuilderExecutor builder) {
        this.extract = new Extract();
        this.builder = builder;
    }

    public BuilderExtract setFileId(String id){
        extract.setFileId(id);
        return this;
    }

    public BuilderExtract setColumns(List<String> column){
        extract.setColumns(column);
        return this;
    }

    public BuilderExtract setLines(Set<Integer> lines){
        extract.setLines(lines);
        return this;
    }

    public BuilderExtract setNewFileId(String newFileId){
        extract.setNewFileId(newFileId);
        return this;
    }

    public BuilderExecutor close(){
        return builder;
    }

    @Override
    public Command build() {
        return extract;
    }
}
