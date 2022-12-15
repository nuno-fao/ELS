package pt.up.fe.els2022;

import pt.up.fe.els2022.dslParser.Command;

import java.util.HashMap;
import java.util.List;

public class Executor{
    private Command command;
    private HashMap<String, List<Table>> symbolTable = new HashMap<>();

    Executor(Command command) {
        this.command = command;
    }

    public void run() {
       command.execute(this.symbolTable);
    }

}
