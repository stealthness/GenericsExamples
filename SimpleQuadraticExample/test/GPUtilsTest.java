import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GPUtilsTest {

    private static final double TOL = 0.000001;
    private double v0;
    private double v1;

    @BeforeEach
    void setUp(){
        v0 = 0.6;
        v1 = -2.3;
    }


    @Test
    void simpleAddLambdaTest(){
        assertEquals(v0+v1, GPUtils.add.apply(v0,v1),TOL);
    }

    @Test
    void simpleSubtractLambdaTest(){
        assertEquals(v0-v1, GPUtils.subtract.apply(v0,v1),TOL);
    }

    @Test
    void simpleMultiplayLambdaTest(){
        assertEquals(v0*v1, GPUtils.multiply.apply(v0,v1),TOL);
    }

    @Test
    void simpleProtectedDivisionLambdaTest(){
        assertEquals(1.0, GPUtils.protectedDivision.apply(3.3,0.0),TOL);
        v1 = 0.0;
        assertEquals(1.2, GPUtils.protectedDivision.apply(3.6,3.0),TOL);
    }

}