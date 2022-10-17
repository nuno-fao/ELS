package pt.up.fe.els2022.builder;

import pt.up.fe.els2022.languageParser.commands.AddColumn;

public class BuilderAddColumn {
    AddColumn addColumn;

    public BuilderAddColumn() {
        this.addColumn = new AddColumn();
    }

    public void setFileId(String id){
        addColumn.setFileId(id);
    }
    public void setColumn(String column){
        addColumn.setColumn(column);
    }
    public void setDefault(String defaultValue){
        addColumn.setDef(defaultValue);
    }
}
