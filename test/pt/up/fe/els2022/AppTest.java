package pt.up.fe.els2022;

import org.junit.Test;

import static org.junit.Assert.*;

public class AppTest {
    @Test
    public void run() {
        App app = new App("test/pt/up/fe/els2022/languageParser/syntactic/greatTest.txt");
        app.run();
    }
}