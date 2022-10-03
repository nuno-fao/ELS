package pt.up.fe.els2022.languageParser;

import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.Assert.*;

public class LanguageParserTest {

    @Test
    public void ReadTest() throws IOException {
        LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/readTest.txt");
        parse.parse();
    }

    @Test
    public void ReadTestError1() throws IOException {
        try {
            LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/readTestError1.txt");
            parse.parse();
        }catch(Error ignored) {
            return;
        }
        fail();
    }
    @Test
    public void ReadTestError2() throws IOException {
        try {
            LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/readTestError2.txt");
            parse.parse();
        }catch (Error ignored) {
            return;
        }
        fail();
    }

    @Test
    public void SortTest1() throws IOException {
        LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/sortTest1.txt");
        parse.parse();
    }
    @Test
    public void SortTest2() throws IOException {
        LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/sortTest2.txt");
        parse.parse();
    }
    @Test
    public void SortTestError1() throws IOException {
        try {
            LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/sortTestError1.txt");
            parse.parse();
        }catch (Error ignored) {
            return;
        }
        fail();
    }
    @Test
    public void SortTestError2() throws IOException {
        try {
            LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/sortTestError2.txt");
            parse.parse();
        }catch (Error ignored) {
            return;
        }
        fail();
    }
    @Test
    public void GreatTest() throws IOException {
        LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/greatTest.txt");
        parse.parse();
        parse.println();
    }
}