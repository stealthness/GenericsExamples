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
    void testCreatAddGPFunction(){
        assertCreateGPFunction(d0+d1,"add","+",
                new GPFunction(GPUtils.add,"add","+"));
    }

    void assertCreateGPFunction(Double expValue, String expFunctionName, String expClojureName, GPFunction function){
        assertEquals(expValue,function.apply(d0,d1));
        assertEquals("add", function.getFunctionName());
        assertEquals("+",function.getClojureString());
    }

}