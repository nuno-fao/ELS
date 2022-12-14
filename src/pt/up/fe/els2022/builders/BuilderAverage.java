package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.BuilderExecutor;
import pt.up.fe.els2022.dslParser.Command;
import pt.up.fe.els2022.dslParser.commands.Average;

public class BuilderAverage implements InterfaceBuilder{
    Average average;
    BuilderExecutor builder;

    public BuilderAverage(BuilderExecutor builder){
        this.average = new Average();
        this.builder = builder;
    }

    public BuilderAverage setFileId(String id){
            average.setFileId(id);
        return this;
    }

    public BuilderAverage setNewFileId(String id){
        average.setNewFileId(id);
        return this;
    }

    public BuilderExecutor close(){
        return builder;
    }

    @Override
    public Command build() {
        return average;
    }
}
