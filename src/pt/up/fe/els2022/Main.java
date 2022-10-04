package pt.up.fe.els2022;

import pt.up.fe.els2022.languageParser.Command;
import pt.up.fe.els2022.languageParser.LanguageParser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.lang.System.exit;

public class Main {
	public static void main(String[] args) throws IOException {
		App app = new App("test/pt/up/fe/els2022/languageParser/syntactic/greatTest.txt");
		app.run();
	}
}

