package pt.up.fe.els2022.dslParser;

import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

public class SyntacticParserTest {

    @Test
    public void ReadTest() throws IOException {
        Parser parse = new Parser("test/pt/up/fe/els2022/dslParser/syntactic/readTest.txt");
        parse.parse();
    }

    @Test
    public void ReadTestError1() throws IOException {
        try {
            Parser parse = new Parser("test/pt/up/fe/els2022/dslParser/syntactic/readTestError1.txt");
            parse.parse();
        }catch(Error ignored) {
            return;
        }
        fail();
    }
    @Test
    public void ReadTestError2() throws IOException {
        try {
            Parser parse = new Parser("test/pt/up/fe/els2022/dslParser/syntactic/readTestError2.txt");
            parse.parse();
        }catch (Error ignored) {
            return;
        }
        fail();
    }
    @Test
    public void ReadTestError3() throws IOException {
        try {
            Parser parse = new Parser("test/pt/up/fe/els2022/dslParser/syntactic/readTestError3.txt");
            parse.parse();
        }catch (Error ignored) {
            return;
        }
        fail();
    }

    @Test
    public void SortTest1() throws IOException {
        Parser parse = new Parser("test/pt/up/fe/els2022/dslParser/syntactic/sortTest1.txt");
        parse.parse();
    }
    @Test
    public void SortTest2() throws IOException {
        Parser parse = new Parser("test/pt/up/fe/els2022/dslParser/syntactic/sortTest2.txt");
        parse.parse();
    }
    @Test
    public void SortTestError1() throws IOException {
        try {
            Parser parse = new Parser("test/pt/up/fe/els2022/dslParser/syntactic/sortTestError1.txt");
            parse.parse();
        }catch (Error ignored) {
            return;
        }
        fail();
    }
    @Test
    public void SortTestError2() throws IOException {
        try {
            Parser parse = new Parser("test/pt/up/fe/els2022/dslParser/syntactic/sortTestError2.txt");
            parse.parse();
        }catch (Error ignored) {
            return;
        }
        fail();
    }

    @Test
    public void MergeTest1() throws IOException {
        Parser parse = new Parser("test/pt/up/fe/els2022/dslParser/syntactic/mergeTest1.txt");
        parse.parse();
    }
    @Test
    public void MergeTest2() throws IOException {
        Parser parse = new Parser("test/pt/up/fe/els2022/dslParser/syntactic/mergeTest2.txt");
        parse.parse();
    }
    @Test
    public void MergeTestError1() throws IOException {
        try {
            Parser parse = new Parser("test/pt/up/fe/els2022/dslParser/syntactic/mergeTestError1.txt");
            parse.parse();
        }catch (Error ignored) {
            return;
        }
        fail();
    }
    @Test
    public void MergeTestError2() throws IOException {
        try {
            Parser parse = new Parser("test/pt/up/fe/els2022/dslParser/syntactic/mergeTestError2.txt");
            parse.parse();
        }catch (Error ignored) {
            return;
        }
        fail();
    }

    @Test
    public void WriteTest1() throws IOException {
        Parser parse = new Parser("test/pt/up/fe/els2022/dslParser/syntactic/writeTest1.txt");
        parse.parse();
    }
    @Test
    public void WriteTestError1() throws IOException {
        try {
            Parser parse = new Parser("test/pt/up/fe/els2022/dslParser/syntactic/writeTestError1.txt");
            parse.parse();
        }catch (Error ignored) {
            return;
        }
        fail();
    }

    @Test
    public void GreatTest() throws IOException {
        Parser parse = new Parser("test/pt/up/fe/els2022/dslParser/syntactic/greatTest.txt");
        parse.parse();
        parse.println();
    }
}