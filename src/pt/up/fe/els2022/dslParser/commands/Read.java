package pt.up.fe.els2022.dslParser.commands;

import org.xml.sax.SAXException;
import pt.up.fe.els2022.JSONAdapter;
import pt.up.fe.els2022.Pair;
import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.XMLAdapter;
import pt.up.fe.els2022.dslParser.Command;

import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class Read implements Command {
    List<String> parentElements;
    List<String> filePath = new ArrayList<>();
    List<String> fileID = new ArrayList<>();

    List<Column> cols = new ArrayList<>();

    FileType type = null;
    public Read() throws Error {
    }

    public void setParentElement(List<String> parentElements) {
        this.parentElements = parentElements;
    }

    public void setFilePath(List<String> filePath) {
        this.filePath = filePath;
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
                Pair pair;
                ArrayList<HashMap<String, String>> entry = new ArrayList<>();

                if (filename.endsWith(".json") || filename.endsWith(".xml")) {
                    pair = parseFile(originalHeaders, finalHeaders, filename);
                    if (pair == null) continue;


                    Table table = getTable(originalHeaders, finalHeaders, pair.entry, filePath.get(i),false);
                    table.setOutput(pair.order);

                    symbolTable.put(fileID.get(i), Collections.singletonList(table));
                }else{
                    if(Files.isDirectory(Path.of(filename))){
                        File f = new File(filename);

                        String[] pathNames = f.list();

                        assert pathNames != null;
                        List<Table> tableList = new ArrayList<>();
                        for (String pathname : pathNames) {
                            pair = parseFile(originalHeaders, finalHeaders, filename+"/"+pathname);
                            if (pair == null) continue;

                            tableList.add(getTable(originalHeaders, finalHeaders, pair.entry, filename+"/"+pathname,true));
                            tableList.get(tableList.size()-1).setOutput(pair.order);
                        }
                        symbolTable.put(fileID.get(i), tableList);
                    }else{
                        throw new Error("Filename "+ filename +" is not a accepted file or a folder ");
                    }
                }
            }

        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Error("Column does not exist in table '"+fileID+"'  ");
        }
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

