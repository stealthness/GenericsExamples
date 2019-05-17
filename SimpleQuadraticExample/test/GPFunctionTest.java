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
        GPFunction add = new GPFunction(GPUtils.add,"add","+");
        assertEquals(d0+d1,add.apply(d0,d1));
        assertEquals("add", add.getFunctionName());
        assertEquals("+",add.getClojureString());
    }

}