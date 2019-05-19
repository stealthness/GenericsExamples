import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class GPFunctionTest {

    private static final int MAX_RUNS = 100;
    private double d0,d1;

    @BeforeEach
    void setUp(){
        d0 = 1.2;
        d1 = -3.7;
    }

    @Test
    void testRandomValues(){

        d0 = Math.random()*10 - 5.0;
        d1 = Math.random()*10 - 5.0;

        IntStream.range(0, MAX_RUNS).forEach(i-> {
            testCreateAddGPFunction();
            testCreateSubtractFunction();
            testCreateMultiplyFunction();
            testCreateProtectedDivisionFunction();
        });
    }

    @Test
    void testCreateAddGPFunction(){
        assertCreateGPFunction(d0+d1,"add","+",
                new GPFunction(GPUtils.add,"add","+"));
    }

    @Test
    void testCreateSubtractFunction(){
        assertCreateGPFunction(d0-d1,"subtract","-",
                new GPFunction(GPUtils.subtract,"subtract","-"));
    }

    @Test
    void testCreateMultiplyFunction(){
        assertCreateGPFunction(d0*d1,"multiply","*",
                new GPFunction(GPUtils.multiply,"multiply","*"));
    }

    @Test
    void testCreateProtectedDivisionFunction(){
        assertCreateGPFunction((d1==0)?1.0:d0/d1,"division","/",
                new GPFunction(GPUtils.protectedDivision,"division","/"));
    }

    void assertCreateGPFunction(Double expValue, String expFunctionName, String expClojureName, GPFunction function){
        assertEquals(expValue,function.apply(d0,d1));
        assertEquals(expFunctionName, function.getFunctionName());
        assertEquals(expClojureName,function.getClojureString());
    }



    // test select random node

    @Test
    void testSelectNode0(){

    }

}