package pt.up.fe.els2022.languageParser;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class SyntacticLanguageParserTest {

    @Test
    public void ReadTest() throws IOException {
        LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/syntactic/readTest.txt");
        parse.parse();
    }

    @Test
    public void ReadTestError1() throws IOException {
        try {
            LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/syntactic/readTestError1.txt");
            parse.parse();
        }catch(Error ignored) {
            return;
        }
        fail();
    }
    @Test
    public void ReadTestError2() throws IOException {
        try {
            LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/syntactic/readTestError2.txt");
            parse.parse();
        }catch (Error ignored) {
            return;
        }
        fail();
    }

    @Test
    public void SortTest1() throws IOException {
        LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/syntactic/sortTest1.txt");
        parse.parse();
    }
    @Test
    public void SortTest2() throws IOException {
        LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/syntactic/sortTest2.txt");
        parse.parse();
    }
    @Test
    public void SortTestError1() throws IOException {
        try {
            LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/syntactic/sortTestError1.txt");
            parse.parse();
        }catch (Error ignored) {
            return;
        }
        fail();
    }
    @Test
    public void SortTestError2() throws IOException {
        try {
            LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/syntactic/sortTestError2.txt");
            parse.parse();
        }catch (Error ignored) {
            return;
        }
        fail();
    }

    @Test
    public void MergeTest1() throws IOException {
        LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/syntactic/mergeTest1.txt");
        parse.parse();
    }
    @Test
    public void MergeTest2() throws IOException {
        LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/syntactic/mergeTest2.txt");
        parse.parse();
    }
    @Test
    public void MergeTestError1() throws IOException {
        try {
            LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/syntactic/mergeTestError1.txt");
            parse.parse();
        }catch (Error ignored) {
            return;
        }
        fail();
    }
    @Test
    public void MergeTestError2() throws IOException {
        try {
            LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/syntactic/mergeTestError2.txt");
            parse.parse();
        }catch (Error ignored) {
            return;
        }
        fail();
    }

    @Test
    public void WriteTest1() throws IOException {
        LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/syntactic/writeTest1.txt");
        parse.parse();
    }
    @Test
    public void WriteTestError1() throws IOException {
        try {
            LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/syntactic/writeTestError1.txt");
            parse.parse();
        }catch (Error ignored) {
            return;
        }
        fail();
    }

    @Test
    public void GreatTest() throws IOException {
        LanguageParser parse = new LanguageParser("test/pt/up/fe/els2022/languageParser/syntactic/greatTest.txt");
        parse.parse();
        parse.println();
    }
}