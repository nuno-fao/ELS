package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.BuilderExecutor;
import pt.up.fe.els2022.dslParser.Command;
import pt.up.fe.els2022.dslParser.commands.FileType;
import pt.up.fe.els2022.dslParser.commands.Read;

import java.util.HashMap;
import java.util.List;

public class BuilderRead implements InterfaceBuilder{
    Read read;
    BuilderExecutor builder;

    public BuilderRead(BuilderExecutor builder) {
        this.read = new Read();
        this.builder = builder;
    }

    public BuilderRead setFilesPaths(List<String> filesPaths){
        read.setFilePath(filesPaths);
        return this;
    }
    public BuilderRead setFilesIds(List<String> filesIds){
        read.setFileID(filesIds);
        return this;
    }

    public BuilderRead setParentElements(List<String> parentElements){
        this.read.setParentElement(parentElements);
        return this;
    }

    public BuilderRead setFileType(FileType type){
        this.read.setType(type);
        return this;
    }

    public BuilderRead addWordByCol(String startswith,int col){
        HashMap<String,String> word = new HashMap<>();
        word.put("starts",startswith);
        word.put("col", String.valueOf(col));
        this.read.getTables_and_words().add(word);
        return this;
    }

    public BuilderRead addWordByCol(int line,int col){
        HashMap<String,String> word = new HashMap<>();
        word.put("line", String.valueOf(line));
        word.put("col", String.valueOf(col));
        this.read.getTables_and_words().add(word);
        return this;
    }
    public BuilderRead addWordByWord(String startswith,int word){
        HashMap<String,String> w = new HashMap<>();
        w.put("start",startswith);
        w.put("word", String.valueOf(word));
        this.read.getTables_and_words().add(w);
        return this;
    }

    public BuilderRead addWordByWord(int line,int word){
        HashMap<String,String> w = new HashMap<>();
        w.put("linw", String.valueOf(line));
        w.put("word", String.valueOf(word));
        this.read.getTables_and_words().add(w);
        return this;
    }

    public void addColumn(String initName, String finalName){
        this.read.addColumn(initName, finalName);
    }


    public BuilderExecutor close(){
        return builder;
    }

    @Override
    public Command build() {
        return read;
    }
}
