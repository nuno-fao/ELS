package pt.up.fe.els2022;

import pt.up.fe.els2022.builders.*;
import pt.up.fe.els2022.dslParser.CMDHolder;
import pt.up.fe.els2022.dslParser.Command;
import pt.up.fe.els2022.dslParser.commands.ReadDir;
import pt.up.fe.els2022.dslParser.commands.Write;

import java.util.ArrayList;
import java.util.List;

public class BuilderExecutor implements CMDHolder {
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

    public BuilderJoin join() {
        var b = new BuilderJoin(this);
        builders.add(b);
        return b;
    }

    public BuilderSort sort() {
        var b = new BuilderSort(this);
        builders.add(b);
        return b;
    }

    public BuilderRead read() {
        var b = new BuilderRead(this);
        builders.add(b);
        return b;
    }

    public BuilderSetOutput setOutput() {
        var b = new BuilderSetOutput(this);
        builders.add(b);
        return b;
    }

    public BuilderExtract extract() {
        var b = new BuilderExtract(this);
        builders.add(b);
        return b;
    }

    public BuilderSum sum() {
        var b = new BuilderSum(this);
        builders.add(b);
        return b;
    }

    public BuilderAverage average() {
        var b = new BuilderAverage(this);
        builders.add(b);
        return b;
    }


    public BuilderAppend append() {
        var b = new BuilderAppend(this);
        builders.add(b);
        return b;
    }

    public BuilderRename rename() {
        var b = new BuilderRename(this);
        builders.add(b);
        return b;
    }

    public BuilderCompress compress() {
        var b = new BuilderCompress(this);
        builders.add(b);
        return b;
    }


    public BuilderReadDir readDir() {
        var b = new BuilderReadDir(this);
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
