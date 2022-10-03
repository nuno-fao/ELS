package pt.up.fe.els2022.languageParser;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class LanguageParserTest {

    @Test
    public void parse() throws IOException {
        LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/config.txt");
        parse.parse();
    }
}