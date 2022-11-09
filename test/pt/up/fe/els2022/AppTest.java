package pt.up.fe.els2022;

import org.junit.Test;
import pt.up.fe.specs.util.SpecsIo;

import static java.lang.Thread.sleep;
import static org.junit.Assert.*;

public class AppTest {
    @Test
    public void readWrite() throws InterruptedException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testReadWrite.txt");
        app.run();
        assertEquals(
                "LUTs,FFs,DSPs,BRAMs\n" +
                        "145,64,1,0",
                SpecsIo.getResource("pt/up/fe/els2022/outFiles/outTestReadWrite.csv")
        );
    }
    @Test
    public void readSortWrite() throws InterruptedException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testReadSortWrite.txt");
        app.run();
        assertEquals(
                "LUTs,FFs,DSPs,BRAMs\n" +
                        "200,525,20,5\n" +
                        "145,64,1,0",
                SpecsIo.getResource("pt/up/fe/els2022/outFiles/outTestReadSortWrite.csv")
        );
    }
    @Test
    public void readAddWrite() throws InterruptedException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testReadAddWrite.txt");
        app.run();
        assertEquals(
                "LUTs,FFs,DSPs,BRAMs,TESTCOL\n" +
                        "145,64,1,0,TESTE",
                SpecsIo.getResource("pt/up/fe/els2022/outFiles/outTestReadAddWrite.csv")
        );
    }
    @Test
    public void readRemoveWrite() throws InterruptedException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testReadRemoveWrite.txt");
        app.run();
        assertEquals(
                "FFs,DSPs,BRAMs\n" +
                        "64,1,0",
                SpecsIo.getResource("pt/up/fe/els2022/outFiles/outTestReadRemoveWrite.csv")
        );
    }


    @Test
    public void full() throws InterruptedException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testFull.txt");
        app.run();
        assertEquals(
                "File,LUTs,FFs,DSPs,BRAMs\n" +
                        "vitis-report_1.xml,145,64,1,0\n" +
                        "vitis-report_2.xml,200,525,20,5\n" +
                        "vitis-report_3.xml,524,1050,40,10",
                SpecsIo.getResource("pt/up/fe/els2022/outFiles/outTestFull.csv")
        );
    }


    @Test
    public void great() throws InterruptedException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testGreat.txt");
        app.run();
        assertEquals(
                "File,FF,BRAMs,Col\n" +
                        "vitis-report_3.xml,1050,10,Default\n" +
                        "vitis-report_2.xml,525,5,Default\n" +
                        ",64,0,Default\n" +
                        "vitis-report_1.xml,64,0,Default",
                SpecsIo.getResource("pt/up/fe/els2022/outFiles/outTestGreat.csv")
        );
    }

    @Test
    public void json1() throws InterruptedException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testJson1.txt");
        app.run();
        /*assertEquals(
                "File,FF,BRAMs,Col\n" +
                        "vitis-report_3.xml,1050,10,Default\n" +
                        "vitis-report_2.xml,525,5,Default\n" +
                        ",64,0,Default\n" +
                        "vitis-report_1.xml,64,0,Default",
                SpecsIo.getResource("pt/up/fe/els2022/outFiles/outTestGreat.csv")
        );*/
    }

    @Test
    public void assignment2() throws InterruptedException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testAssignment2.txt");
        app.run();
    }

    @Test
    public void multipleRead() throws InterruptedException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testMultipleRead.txt");
        app.run();
    }
}