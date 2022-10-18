package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.BuilderExecutor;
import pt.up.fe.els2022.languageParser.Command;
import pt.up.fe.els2022.languageParser.commands.Sort;
import pt.up.fe.els2022.languageParser.commands.Write;

public class BuilderSort implements InterfaceBuilder{
    Sort sort;
    BuilderExecutor builder;

    public BuilderSort(BuilderExecutor builder) {
        this.sort = new Sort();
        this.builder = builder;
    }

    public BuilderSort setCol(String col) {
        this.sort.setCol(col);
        return this;
    }
    public BuilderSort setFileId(String id) {
        this.sort.setFileId(id);
        return this;
    }
    public BuilderSort setNewFileId(String newFileId) {
        this.sort.setNewFileId(newFileId);
        return this;
    }
    public BuilderSort setDirection(String direction) {
        this.sort.setDirection(direction);
        return this;
    }

    public BuilderExecutor close(){
        return builder;
    }

    @Override
    public Command build() {
        return sort;
    }
}
