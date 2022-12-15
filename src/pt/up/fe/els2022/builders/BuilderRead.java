package pt.up.fe.els2022.builders;

import pt.up.fe.els2022.dslParser.CommandHolder;
import pt.up.fe.els2022.dslParser.Command;
import pt.up.fe.els2022.dslParser.commands.FileType;
import pt.up.fe.els2022.dslParser.commands.Read;

import java.util.HashMap;
import java.util.List;

public class BuilderRead implements InterfaceBuilder{
    Read read;
    CommandHolder builder;

    public BuilderRead(CommandHolder builder) {
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

    public BuilderRead addWordByCol(String startswith,int col,String name){
        HashMap<String,String> word = new HashMap<>();
        word.put("start",startswith);
        word.put("col", String.valueOf(col));
        word.put("name", name);
        this.read.getTables_and_words().add(word);
        return this;
    }

    public BuilderRead addWordByCol(int line,int col,String name){
        HashMap<String,String> word = new HashMap<>();
        word.put("line", String.valueOf(line));
        word.put("col", String.valueOf(col));
        word.put("name", name);
        this.read.getTables_and_words().add(word);
        return this;
    }
    public BuilderRead addWordByWord(String startswith,int word,String name){
        HashMap<String,String> w = new HashMap<>();
        w.put("start",startswith);
        w.put("word", String.valueOf(word));
        w.put("name", name);
        this.read.getTables_and_words().add(w);
        return this;
    }

    public BuilderRead addWordByWord(int line,int word,String name){
        HashMap<String,String> w = new HashMap<>();
        w.put("line", String.valueOf(line));
        w.put("word", String.valueOf(word));
        w.put("name", name);
        this.read.getTables_and_words().add(w);
        return this;
    }

    public BuilderRead addTable(int start,int headerLength){
        HashMap<String,String> w = new HashMap<>();
        w.put("start", String.valueOf(start));
        w.put("header", String.valueOf(headerLength));
        this.read.getTables_and_words().add(w);
        return this;
    }


    public BuilderRead setIncludes(List<String> includes){
        this.read.setInclude(includes);
        return this;
    }

    public void addColumn(String initName, String finalName){
        this.read.addColumn(initName, finalName);
    }


    public CommandHolder close(){
        return builder;
    }

    @Override
    public Command build() {
        return read;
    }
}
