package pt.up.fe.els2022;

import pt.up.fe.els2022.dslParser.Command;

import java.util.HashMap;
import java.util.List;

public class Executor {
    private List<Command> commands;
    private HashMap<String, Table> symbolTable = new HashMap<>();

    Executor(List<Command> commands) {
        this.commands = commands;
    }

    public void run() {
        for (Command command : commands) {
            command.execute(this.symbolTable);
        }
    }
}
