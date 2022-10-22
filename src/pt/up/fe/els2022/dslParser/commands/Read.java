package pt.up.fe.els2022.dslParser.commands;

import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.XMLAdapter;
import pt.up.fe.els2022.dslParser.Command;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Read implements Command {
    List<String> parentElements;
    List<String> filePath = new ArrayList<>();
    List<String> fileID = new ArrayList<>();

    List<Column> cols = new ArrayList<>();
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

    public void addColumn(String initColumn, String finalColumn) {
        this.cols.add(new Column(initColumn, finalColumn));
    }

    public void close() {
        if(parentElements == null) throw new Error("Read command must have Parent definition");
    }

    @Override
    public void println() {}

    @Override
    public void execute(HashMap<String, Table> symbolTable) {
        try {
            ArrayList<String> originalHeaders = new ArrayList<>();
            ArrayList<String> finalHeaders = new ArrayList<>();
            for (Column column : cols) {
                originalHeaders.add(column.initName);
                finalHeaders.add(column.finalName);
            }

            for (int i = 0;i < filePath.size(); i++){
                ArrayList<HashMap<String, String>> entry = XMLAdapter.parseFile(filePath.get(i), originalHeaders, finalHeaders, parentElements);
                Table table = new Table();
                table.setEntries(entry);
                table.setHeaders(finalHeaders);

                Path path = Paths.get(filePath.get(i));
                Path fileName = path.getFileName();

                table.setOrigin(fileName.toString());

                symbolTable.put(fileID.get(i),table);
            }
        }catch (Exception e) {
            System.out.println(e.getMessage());
            throw new Error("Column does not exist in table '"+fileID+"'  ");
        }
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
