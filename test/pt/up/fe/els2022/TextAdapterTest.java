package pt.up.fe.els2022;

import org.junit.Test;
import pt.up.fe.specs.util.SpecsIo;


import static org.junit.Assert.*;

public class TextAdapterTest {
    @Test
    public void wordLineNum() {

        assertEquals("0",TextAdapter.wordLineNum("./files/nas_ua.text",10,2));
        assertEquals("performed",TextAdapter.wordLineNum("./files/nas_ua.text",31,3));
    }

    @Test
    public void wordLineCol(){

        assertEquals("Step",TextAdapter.wordLineCol("./files/nas_ua.text",10,2));
        assertEquals("elements",TextAdapter.wordLineCol("./files/nas_ua.text",10,13));
    }
    @Test
    public void wordSwCol() {

        assertEquals("refined",TextAdapter.wordSwCol("./files/nas_ua.text","Step",25));
        assertEquals("performed",TextAdapter.wordSwCol("./files/nas_ua.text","Verification",22));
    }

    @Test
    public void wordSwNum() {

        assertEquals("epsilon",TextAdapter.wordSwNum("./files/nas_ua.text","accuracy",4));
        assertEquals("coll",TextAdapter.wordSwNum("./files/nas_ua.text","Operation",4));
    }


    @Test
    public void tableLineCol() {

        assertEquals("new_const_array_d2",TextAdapter.tableLineCol("./files/gprof.txt", 6, 2, 6, "name"));
        assertEquals("1.63",TextAdapter.tableLineCol("./files/gprof.txt", 6, 2, 2, "cumulative seconds"));
        assertEquals("99.39",TextAdapter.tableLineCol("./files/gprof.txt", 6, 2, 1, 1));
        assertEquals("",TextAdapter.tableLineCol("./files/gprof.txt", 6, 2,2, 5));
        assertEquals("19",TextAdapter.tableLineCol("./files/gprof.txt", 6, 2,3, 4));
    }

}