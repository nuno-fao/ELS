package pt.up.fe.els2022;

import pt.up.fe.els2022.languageParser.Command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Executor {
    List<Command> commands;
    HashMap<String, Table> symbolTable = new HashMap<>();

    Executor(List<Command> commands) {
        this.commands = commands;
    }

    public void run() {
        for (Command command : commands) {
            command.execute(this.symbolTable);
        }
    }
}
