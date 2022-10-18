package pt.up.fe.els2022;

import pt.up.fe.els2022.builders.*;
import pt.up.fe.els2022.languageParser.Command;

import java.util.ArrayList;
import java.util.List;

public class BuilderExecutor {
    private List<InterfaceBuilder> builders = new ArrayList<>();
    public BuilderAddColumn addColumn() {
        var b = new BuilderAddColumn(this);
        builders.add(b);
        return b;
    }
    public BuilderRemoveColumn removeColumn() {
        var b =  new BuilderRemoveColumn(this);
        builders.add(b);
        return b;
    }

    public BuilderWrite write() {
        var b = new BuilderWrite(this);
        builders.add(b);
        return b;
    }
    public BuilderMerge merge() {
        var b = new BuilderMerge(this);
        builders.add(b);
        return b;
    }
    public BuilderSort sort() {
        var b = new BuilderSort(this);
        builders.add(b);
        return b;
    }

    public Executor build() {
        List<Command> commands = new ArrayList<Command>();
        for (InterfaceBuilder i: this.builders) {
            commands.add(i.build());
        }
        return new Executor(commands);
    }
}
