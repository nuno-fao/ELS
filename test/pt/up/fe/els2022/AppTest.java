package pt.up.fe.els2022;

import org.junit.Test;
import pt.up.fe.specs.util.SpecsIo;

import java.util.Collections;
import java.util.List;

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

        assertEquals(
                "folder,DSP48E,FF,LUT,BRAM_18K,URAM,max_features_,meta,n_classes_,n_features_,n_outputs_,ccp_alpha,criterion,min_impurity_decrease,min_samples_leaf,min_samples_split,min_weight_fraction_leaf,splitter,% time,name\n" +
                        "files,1,64,145,0,0,16,decision-tree,2,16,1,0.0,gini,0.0,1,2,0.0,best,99.39,matrix_mulv3_tdtdptd",
                SpecsIo.getResource("pt/up/fe/els2022/outFiles/outTestAssignment2.csv")
        );
    }

    @Test
    public void multipleRead() throws InterruptedException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testMultipleRead.txt");
        app.run();
    }

    @Test
    public void extract() throws InterruptedException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testReadExtract.txt");
        app.run();

        assertEquals(
                "DSP48E,BRAM_18K\n" +
                        "240,270",
                SpecsIo.getResource("pt/up/fe/els2022/outFiles/f3.csv")
        );
    }

    @Test
    public void text() throws InterruptedException {
        App app = new App("test/pt/up/fe/els2022/configFiles/testText.txt");
        app.run();
    }

    @Test
    public void usReader() throws InterruptedException {
        App app = new App("test/pt/up/fe/els2022/configFiles/uaReader.txt");
        app.run();
    }


    @Test
    public void equivalent_json() throws InterruptedException {
        App app = new App("test/pt/up/fe/els2022/configFiles/equivalent_json.txt");
        app.run();

        assertEquals(
                "nodes,functions\n" +
                        "300,30",
                SpecsIo.getResource("pt/up/fe/els2022/outFiles/outTestEquivalent.csv")
        );
    }

    @Test
    public void api_run() throws InterruptedException {
        new    BuilderExecutor()
                                .read()
                                    .setFilesPaths(Collections.singletonList("checkpoint2/equivalent.json"))
                                    .setFilesIds(Collections.singletonList("f1"))
                                    .setParentElements(Collections.singletonList("total/results/static"))
                                .close()
                                .write()
                                    .setType("CSV")
                                    .setFileId("f1")
                                    .setFilePath("test/pt/up/fe/els2022/outFiles/outEquivalentAPI.csv")
                                .close()
                            .build()
                .run();
    }


}