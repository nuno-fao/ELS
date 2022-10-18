package pt.up.fe.els2022;

import pt.up.fe.els2022.languageParser.Command;
import pt.up.fe.els2022.languageParser.LanguageParser;

import java.util.HashMap;
import java.util.List;

import static java.lang.System.exit;

public class App {
    String cf;

    public App(String configFile) {
        cf = configFile;
    }

    public void run() {
        try {
            LanguageParser parse = new LanguageParser(cf);
            List<Command> commands = parse.parse();


        } catch (Exception e) {
            e.printStackTrace();
            exit(1);
        }
    }
}
