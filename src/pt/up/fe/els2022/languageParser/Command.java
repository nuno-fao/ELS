package pt.up.fe.els2022.languageParser;

import pt.up.fe.els2022.Table;

import java.util.HashMap;

public interface Command {
    void addLine(String line);
    void close();
    void println();

    void execute(HashMap<String, Table> symbolTable);
}
