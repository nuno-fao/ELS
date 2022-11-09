package pt.up.fe.els2022.dslParser;

import pt.up.fe.els2022.Table;

import java.util.HashMap;
import java.util.List;

public interface Command {
    void println();

    void execute(HashMap<String, List<Table>> symbolTable);
}
