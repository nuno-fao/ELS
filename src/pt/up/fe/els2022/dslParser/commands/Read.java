package pt.up.fe.els2022.dslParser.commands;

import org.xml.sax.SAXException;
import pt.up.fe.els2022.*;
import pt.up.fe.els2022.dslParser.Command;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

import static pt.up.fe.els2022.dslParser.commands.FileType.TEXT;

public class Read implements Command {
    List<String> parentElements;
    List<String> filePath = new ArrayList<>();

    List<String> filePathCopy = new ArrayList<>();
    List<String> fileID = new ArrayList<>();
    List<Column> cols = new ArrayList<>();
    List<String> include = new ArrayList<>();

    List<HashMap<String,String>> tables_and_words = new ArrayList<>();

    public List<HashMap<String, String>> getTables_and_words() {
        return tables_and_words;
    }

    public void setTables_and_words(List<HashMap<String, String>> tables_and_words) {
        this.tables_and_words = tables_and_words;
    }

    public List<String> getInclude() {
        return include;
    }

    public void setInclude(List<String> include) {
        this.include = include;
    }

    FileType type = null;
    public Read() throws Error {
    }

    public void setParentElement(List<String> parentElements) {
        this.parentElements = parentElements;
    }

    public void setFilePath(List<String> filePath) {
        this.filePath = filePath;
        for(String string : filePath){
            this.filePathCopy.add(new String(string));
        }
    }

    public void setRelativeFilePath(List<String> filePath) {
        this.filePath = filePath;
    }



    public List<String> getRelativePath(){
        return this.filePathCopy;
    }

    public void setFileID(List<String> fileID) {
        this.fileID = fileID;
    }

    public void setCols(List<Column> cols) {
        this.cols = cols;
    }

    public List<String> getParentElements() {
        return parentElements;
    }

    public List<String> getFilePath() {
        return filePath;
    }

    public List<String> getFileID() {
        return fileID;
    }

    public List<Column> getCols() {
        return cols;
    }

    public FileType getType() {
        return type;
    }

    public void setType(FileType type) {
        this.type = type;
    }

    public void addColumn(String initColumn, String finalColumn) {
        this.cols.add(new Column(initColumn, finalColumn));
    }

    public void close() {
        if(parentElements == null) throw new Error("Read command must have Parent definition");
    }

    @Override
    public void println() {}

    @Override
    public void execute(HashMap<String, List<Table>> symbolTable) {
        try {
            ArrayList<String> originalHeaders = new ArrayList<>();
            ArrayList<String> finalHeaders = new ArrayList<>();
            for (Column column : cols) {
                originalHeaders.add(column.initName);
                finalHeaders.add(column.finalName);
            }

            for (int i = 0;i < filePath.size(); i++){
                String filename = filePath.get(i);

                if(type == TEXT) {
                    if (Files.isDirectory(Path.of(filename))) {
                        File f = new File(filename);

                        String[] pathNames = f.list();

                        assert pathNames != null;
                        List<Table> tableList = new ArrayList<>();
                        for (String pathname : pathNames) {
                            Table out = null;
                            for(var table_or_word: tables_and_words){
                                Table t = getWordOrTable( i, filename+"/"+pathname, table_or_word);
                                if(out == null){
                                    out = t;
                                }else{
                                    out = TableOperations.joinTables(out,t);
                                }
                            }
                            for (String include: include) {
                                if(Objects.equals(include, "folder")) {
                                    assert out != null;
                                    TableOperations.addColumn(Collections.singletonList(out), include, out.getOriginFolder());
                                    out.getOutput().remove(include);
                                    out.getOutput().add(0,include);
                                }else if(Objects.equals(include, "file")){
                                    assert out != null;
                                    TableOperations.addColumn(Collections.singletonList(out), include, out.getOriginFile());
                                    out.getOutput().add(0,include);
                                }
                            }
                            tableList.add(out);
                        }
                        symbolTable.put(fileID.get(i), tableList);
                    } else {
                        Table out = null;
                        List<Table> outList = new ArrayList<>();
                        for(var table_or_word: tables_and_words){
                            Table t = getWordOrTable( i, filename, table_or_word);
                            if(out == null){
                                out = t;
                            }else{
                                out = TableOperations.joinTables(out,t);
                            }
                        }
                        outList.add(out);
                        for (String include: include) {
                            include = include.trim();
                            if(Objects.equals(include, "folder")) {
                                assert out != null;
                                TableOperations.addColumn(outList, include, out.getOriginFolder());
                                out.getOutput().remove(include);
                                out.getOutput().add(0,include);
                            }else if(Objects.equals(include, "file")){
                                assert out != null;
                                TableOperations.addColumn(outList, include, out.getOriginFile());
                                out.getOutput().remove(include);
                                out.getOutput().add(0,include);
                            }
                        }
                        symbolTable.put(fileID.get(i), outList);
                    }
                }else{
                    Pair pair;
                    ArrayList<HashMap<String, String>> entry = new ArrayList<>();

                    if (filename.endsWith(".json") || filename.endsWith(".xml")) {
                        pair = parseFile(originalHeaders, finalHeaders, filename);
                        if (pair == null) continue;

                        Table table = getTable(originalHeaders, finalHeaders, pair.entry, filePath.get(i),false);
                        table.setOutput(pair.order);


                        for (String include: include) {
                            include = include.trim();
                            if(include.equals("folder")) {
                                TableOperations.addColumn(Collections.singletonList(table), include, table.getOriginFolder());
                                table.getOutput().remove(include);
                                table.getOutput().add(0,include);
                            }else if(Objects.equals(include, "file")){
                                TableOperations.addColumn(Collections.singletonList(table), include, table.getOriginFile());
                                table.getOutput().remove(include);
                                table.getOutput().add(0,include);
                            }
                        }

                        symbolTable.put(fileID.get(i), Collections.singletonList(table));
                    } else {
                        if (Files.isDirectory(Path.of(filename))) {
                            File f = new File(filename);

                            String[] pathNames = f.list();

                            assert pathNames != null;
                            List<Table> tableList = new ArrayList<>();
                            for (String pathname : pathNames) {
                                pair = parseFile(originalHeaders, finalHeaders, filename+"/"+pathname);
                                if (pair == null) continue;


                                Table t = getTable(originalHeaders, finalHeaders, pair.entry, filename+"/"+pathname,true);
                                t.setOutput(pair.order);

                                for (String include: include) {
                                    include = include.trim();
                                    if(Objects.equals(include, "folder")) {
                                        TableOperations.addColumn(Collections.singletonList(t), include, t.getOriginFolder());
                                        t.getOutput().remove(include);
                                        t.getOutput().add(0,include);
                                    }else if(Objects.equals(include, "file")){
                                        TableOperations.addColumn(Collections.singletonList(t), include, t.getOriginFile());
                                        t.getOutput().remove(include);
                                        t.getOutput().add(0,include);
                                    }
                                }

                                tableList.add(t);
                            }
                            symbolTable.put(fileID.get(i), tableList);
                        } else {
                            throw new Error("Filename " + filename + " is not a accepted file or a folder ");
                        }
                    }
                }
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Error("Column does not exist in table '"+fileID+"'  ");
        }
    }

    private Table getWordOrTable(int i, String pathname, HashMap<String, String> table_or_word) {
        String w = null;
        Table t = null;
        if(table_or_word.containsKey("start") && table_or_word.containsKey("col")){
            w = TextAdapter.wordSwCol(pathname, table_or_word.get("start"), Integer.parseInt(table_or_word.get("col")));
        }else if(table_or_word.containsKey("start") && table_or_word.containsKey("word")){
            w = TextAdapter.wordSwNum(pathname, table_or_word.get("start"), Integer.parseInt(table_or_word.get("word")));
        }else if(table_or_word.containsKey("line") && table_or_word.containsKey("word")){
            w = TextAdapter.wordLineNum(pathname, Integer.parseInt(table_or_word.get("line")), Integer.parseInt(table_or_word.get("word")));
        }else if(table_or_word.containsKey("line") && table_or_word.containsKey("col")){
            w = TextAdapter.wordLineCol(pathname, Integer.parseInt(table_or_word.get("line")), Integer.parseInt(table_or_word.get("col")));
        }else if(table_or_word.containsKey("start") && table_or_word.containsKey("header")){
            t = TextAdapter.table(pathname,Integer.parseInt(table_or_word.get("start")),Integer.parseInt(table_or_word.get("header")));
        }
        if(w != null){
            ArrayList<HashMap<String,String>> list = new ArrayList<>();
            HashMap<String,String> map = new HashMap<>();
            map.put(table_or_word.get("name"),w);
            list.add(map);


            Table table = getTable(new ArrayList<>(Collections.singleton(table_or_word.get("name"))), new ArrayList<>(Collections.singleton(table_or_word.get("name"))), list, filePath.get(i),false);
            table.setOutput(new ArrayList<>(Collections.singleton(table_or_word.get("name"))));
            return table;
        }else if(t != null){
            Path p = Paths.get(pathname);
            t.setOriginFile(p.getFileName().toString());
            t.setOriginFolder(p.getParent().toString());
            return t;
        }
        throw new Error(" ");
    }

    private Pair parseFile(ArrayList<String> originalHeaders, ArrayList<String> finalHeaders, String filename) throws IOException, SAXException, ParserConfigurationException {
        Pair entry = null;

        if ((filename.endsWith(".json") && this.type == FileType.JSON) || (filename.endsWith(".json") && this.type == null)) {
            if(this.type == FileType.JSON && !filename.endsWith(".json")){
                return null;
            }
            boolean root = false;
            if (parentElements.contains("ROOT")) {
                root = true;
                parentElements.remove("ROOT");
            }
            entry = JSONAdapter.parseFile(filename, originalHeaders, finalHeaders, parentElements, root);
            if(root) parentElements.add("ROOT");
        } else if ((filename.endsWith(".xml") && this.type == FileType.XML) || (filename.endsWith(".xml") && this.type == null)) {
            if(this.type == FileType.XML && !filename.endsWith(".xml")){
                return null;
            }
            entry = XMLAdapter.parseFile(filename, originalHeaders, finalHeaders, parentElements);
        }
        return entry;
    }

    private static Table getTable(ArrayList<String> originalHeaders, ArrayList<String> finalHeaders, ArrayList<HashMap<String, String>> entry, String file,boolean folder) {
        Table table = new Table();
        table.setEntries(entry);
        table.setHeaders(finalHeaders);

        Path path = Paths.get(file);
        Path fileName = path.getFileName();

        table.setOriginFile(fileName.toString());
        table.setOriginFolder(path.getParent().toString());

        if(originalHeaders.size() == 0){
            var e  = table.getEntries().get(0);
            table.setHeaders(new ArrayList<>(e.keySet()));
        }
        return table;
    }
}

class Column{
    String initName;
    String finalName;
    public Column(String initName, String finalName) throws Error{
        this.initName = initName;
        this.finalName = finalName;
    }

    void println(){
        if(finalName.equals(initName)){
            System.out.println("    "+finalName);
        }else{
            System.out.println("    "+initName+" => "+finalName);
        }
    }
}

