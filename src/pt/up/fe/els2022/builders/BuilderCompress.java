package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.dslParser.CMDHolder;
import pt.up.fe.els2022.dslParser.Command;
import pt.up.fe.els2022.dslParser.commands.Compress;

public class BuilderCompress implements InterfaceBuilder {
    Compress compress;
    CMDHolder builder;

    public BuilderCompress(CMDHolder builder){
        this.builder = builder;
        compress = new Compress();
    }

    public BuilderCompress setFileId(String id){
        compress.setFileId(id);
        return this;
    }

    public BuilderCompress setSuffix(String id){
        compress.setSuffix(id);
        return this;
    }

    public BuilderCompress setNewFileId(String id){
        compress.setNewFileId(id);
        return this;
    }

    public CMDHolder close(){
        return builder;
    }

    @Override
    public Command build() {
        return compress;
    }


}
