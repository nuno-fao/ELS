package pt.up.fe.els2022.dslParser.commands;

import pt.up.fe.els2022.Table;
import pt.up.fe.els2022.dslParser.Command;

import java.util.HashMap;
import java.util.List;

public class Block implements Command{
    private List<Command> commands;
    private HashMap<String, List<Table>> symbolTable = new HashMap<>();

    public Block(List<Command> commands) {
        this.commands = commands;
    }

    @Override
    public void println() {

    }

    @Override
    public void execute(HashMap<String, List<Table>> symbolTable) {

    }
}
