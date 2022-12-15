package pt.up.fe.els2022;

import pt.up.fe.els2022.dslParser.Command;
import pt.up.fe.els2022.dslParser.Parser;
import pt.up.fe.els2022.dslParser.dslParser;

import java.util.List;

import static java.lang.System.exit;

public class App {
    String cf;

    public App(String configFile) {
        cf = configFile;
    }

    public int run() {
        try {
            dslParser dslParser = new dslParser(cf);
            dslParser.parse();
            new Executor(dslParser.getBuilder().build()).run();

        } catch (Exception e) {
            e.printStackTrace();
            return 1;
        }
        return 0;
    }
}
