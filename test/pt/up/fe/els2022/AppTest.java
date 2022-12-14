package pt.up.fe.els2022;

import org.junit.Test;
import pt.up.fe.els2022.dslParser.commands.FileType;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import static org.junit.Assert.*;

public class AppTest {
    @Test
    public void readWrite() throws IOException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testReadWrite.mydsl");
        app.run();
        assertEquals(
                "LUTs,FFs,DSPs,BRAMs\n" +
                        "145,64,1,0",
                new String(Files.readAllBytes(Paths.get("test/pt/up/fe/els2022/outFiles/outTestReadWrite.csv")))
        );
    }
    @Test
    public void readSortWrite() throws IOException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testReadSortWrite.mydsl");
        app.run();
        assertEquals(
                "LUTs,FFs,DSPs,BRAMs\n" +
                        "200,525,20,5\n" +
                        "145,64,1,0",
                new String(Files.readAllBytes(Paths.get("test/pt/up/fe/els2022/outFiles/outTestReadSortWrite.csv")))
        );
    }
    @Test
    public void readAddWrite() throws IOException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testReadAddWrite.mydsl");
        app.run();
        assertEquals(
                "LUTs,FFs,DSPs,BRAMs,TESTCOL\n" +
                        "145,64,1,0,TESTE",
                new String(Files.readAllBytes(Paths.get("test/pt/up/fe/els2022/outFiles/outTestReadAddWrite.csv")))
        );
    }
    @Test
    public void readRemoveWrite() throws IOException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testReadRemoveWrite.mydsl");
        app.run();
        assertEquals(
                "FFs,DSPs,BRAMs\n" +
                        "64,1,0",
                new String(Files.readAllBytes(Paths.get("test/pt/up/fe/els2022/outFiles/outTestReadRemoveWrite.csv")))
        );
    }
    @Test
    public void average() throws  IOException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testAverage.mydsl");
        app.run();
        assertEquals(
                "LUTs,FFs,DSPs,BRAMs\n" +
                        "145,64,1,0\n" +
                        "200,525,20,5\n" +
                        "172.5,294.5,10.5,2.5",
                new String(Files.readAllBytes(Paths.get("test/pt/up/fe/els2022/outFiles/outTestAverage.csv")))
        );
    }
    @Test
    public void sum() throws  IOException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testSum.mydsl");
        app.run();
        assertEquals(
                "LUTs,FFs,DSPs,BRAMs\n" +
                        "145,64,1,0\n" +
                        "200,525,20,5\n" +
                        "345.0,589.0,21.0,5.0",
                new String(Files.readAllBytes(Paths.get("test/pt/up/fe/els2022/outFiles/outTestSum.csv")))
        );
    }


    @Test
    public void full() throws IOException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testFull.mydsl");
        app.run();
        assertEquals(
                "File,LUTs,FFs,DSPs,BRAMs\n" +
                        "vitis-report_1.xml,145,64,1,0\n" +
                        "vitis-report_2.xml,200,525,20,5\n" +
                        "vitis-report_3.xml,524,1050,40,10",
                new String(Files.readAllBytes(Paths.get("test/pt/up/fe/els2022/outFiles/outTestFull.csv")))
        );
    }


    @Test
    public void great() throws IOException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testGreat.mydsl");
        app.run();
        assertEquals(
                "File,FF,BRAMs,Col\n" +
                        "vitis-report_3.xml,1050,10,Default\n" +
                        "vitis-report_2.xml,525,5,Default\n" +
                        ",64,0,Default\n" +
                        "vitis-report_1.xml,64,0,Default",
                new String(Files.readAllBytes(Paths.get("test/pt/up/fe/els2022/outFiles/outTestGreat.csv")))
        );
    }

    @Test
    public void json1() throws IOException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testJson1.mydsl");
        app.run();
        assertEquals(
                "max_features_,meta,n_classes_,n_features_,n_outputs_,ccp_alpha,criterion,min_impurity_decrease,min_samples_leaf,min_samples_split,min_weight_fraction_leaf,splitter\n" +
                        "16,decision-tree,2,16,1,0.0,gini,0.0,1,2,0.0,best",
                new String(Files.readAllBytes(Paths.get("test/pt/up/fe/els2022/outFiles/outTestJson1.csv")))
        );
    }

    @Test
    public void assignment2() throws IOException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testAssignment2.mydsl");
        app.run();

        assertEquals(
                "folder,DSP48E,FF,LUT,BRAM_18K,URAM,max_features_,meta,n_classes_,n_features_,n_outputs_,ccp_alpha,criterion,min_impurity_decrease,min_samples_leaf,min_samples_split,min_weight_fraction_leaf,splitter,% time,name\n" +
                        "files,1,64,145,0,0,16,decision-tree,2,16,1,0.0,gini,0.0,1,2,0.0,best,99.39,matrix_mulv3_tdtdptd",
                new String(Files.readAllBytes(Paths.get("test/pt/up/fe/els2022/outFiles/outTestAssignment2.csv")))
        );
    }

    @Test
    public void multipleRead() throws IOException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testMultipleRead.mydsl");
        app.run();
    }

    @Test
    public void extract() throws IOException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testReadExtract.mydsl");
        app.run();

        assertEquals(
                "DSP48E,BRAM_18K\n" +
                        "240,270",
                new String(Files.readAllBytes(Paths.get("test/pt/up/fe/els2022/outFiles/f3.csv")))
        );
    }

    @Test
    public void text() throws IOException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testText.mydsl");
        app.run();
    }

    @Test
    public void usReader() {
        App app = new App("test/pt/up/fe/els2022/configFiles/uaReader.mydsl");
        app.run();
    }


    @Test
    public void equivalent_json() throws IOException {
        App app = new App("test/pt/up/fe/els2022/configFiles/equivalent_json.mydsl");
        app.run();

        assertEquals(
                "nodes,functions\n" +
                        "300,30",
                new String(Files.readAllBytes(Paths.get("test/pt/up/fe/els2022/outFiles/outTestEquivalent.csv")))
        );
    }

    @Test
    public void api_run() {
        ( (BuilderExecutor) new BuilderExecutor()
                                .read()
                                    .setFilesPaths(Collections.singletonList("files/ass2/equivalent.json"))
                                    .setFileType(FileType.JSON)
                                    .setFilesIds(Collections.singletonList("f1"))
                                    .setParentElements(Collections.singletonList("total/results/static"))
                                .close()
                                .write()
                                    .setType("CSV")
                                    .setFileId("f1")
                                    .setFilePath("test/pt/up/fe/els2022/outFiles/outEquivalentAPI.csv")
                                .close())
                            .build()
                .run();
    }

    @Test
    public void readDir() throws InterruptedException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testReadDir.mydsl");
        app.run();
    }
}