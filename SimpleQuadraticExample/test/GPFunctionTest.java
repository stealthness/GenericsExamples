import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GPFunctionTest {

    double d0,d1;

    @BeforeEach
    void setUp(){
        d0 = 1.2;
        d1 = -3.7;
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
    void testCreateMutiplyFunction(){
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

}