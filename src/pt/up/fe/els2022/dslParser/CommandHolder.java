package pt.up.fe.els2022.dslParser;

import pt.up.fe.els2022.builders.*;

public interface CommandHolder {
    public BuilderAddColumn addColumn();
    public BuilderRemoveColumn removeColumn() ;

    public BuilderWrite write() ;
    public BuilderMerge merge();
    public BuilderJoin join() ;
    public BuilderSort sort() ;

    public BuilderRead read();

    public BuilderSetOutput setOutput() ;

    public BuilderExtract extract() ;

    public BuilderSum sum();

    public BuilderAverage average();

    public BuilderReadDir readDir();
    public BuilderAppend append();
    public BuilderRename rename();

    public BuilderCompress compress();

    public Command build();

}
