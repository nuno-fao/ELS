package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.BuilderExecutor;
import pt.up.fe.els2022.dslParser.Command;
import pt.up.fe.els2022.dslParser.commands.Write;

public class BuilderWrite implements InterfaceBuilder{
    Write write;
    BuilderExecutor builder;

    public BuilderWrite(BuilderExecutor builder) {
        this.write = new Write();
        this.builder = builder;
    }

    public BuilderWrite setType(String type) {
        this.write.setType(type);
        return this;
    }
    public BuilderWrite setFileId(String id) {
        this.write.setFileId(id);
        return this;
    }
    public BuilderWrite setFilePath(String path) {
        this.write.setFilePath(path);
        return this;
    }

    public BuilderExecutor close(){
        return builder;
    }

    @Override
    public Command build() {
        return write;
    }
}
