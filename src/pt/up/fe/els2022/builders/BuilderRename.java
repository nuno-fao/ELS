package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.dslParser.CMDHolder;
import pt.up.fe.els2022.dslParser.Command;
import pt.up.fe.els2022.dslParser.commands.Rename;

public class BuilderRename implements InterfaceBuilder{
    Rename rename;
    CMDHolder builder;

    public BuilderRename(CMDHolder builder) {
        this.rename = new Rename();
        this.builder = builder;
    }


    public CMDHolder close(){
        return builder;
    }

    @Override
    public Command build() {
        return rename;
    }
}
