package pt.up.fe.els2022;

import org.junit.Test;

import static org.junit.Assert.*;

public class AppTest {
    @Test
    public void readWrite(){
        App app = new App("test/pt/up/fe/els2022/configFiles/testReadWrite.txt");
        app.run();
    }
    @Test
    public void readSortWrite(){
        App app = new App("test/pt/up/fe/els2022/configFiles/testReadSortWrite.txt");
        app.run();
    }
    @Test
    public void readAddWrite(){
        App app = new App("test/pt/up/fe/els2022/configFiles/testReadAddWrite.txt");
        app.run();
    }
    @Test
    public void readRemoveWrite(){
        App app = new App("test/pt/up/fe/els2022/configFiles/testReadRemoveWrite.txt");
        app.run();
    }


    @Test
    public void full(){
        App app = new App("test/pt/up/fe/els2022/configFiles/testFull.txt");
        app.run();
    }
}