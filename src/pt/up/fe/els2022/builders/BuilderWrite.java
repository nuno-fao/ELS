package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.dslParser.CMDHolder;
import pt.up.fe.els2022.dslParser.Command;
import pt.up.fe.els2022.dslParser.commands.Write;

public class BuilderWrite implements InterfaceBuilder{
    Write write;
    CMDHolder builder;

    public BuilderWrite(CMDHolder builder) {
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

    public CMDHolder close(){
        return builder;
    }

    @Override
    public Command build() {
        return write;
    }
}
