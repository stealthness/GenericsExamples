import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class GPUtilsTest {

    private static final double TOL = 0.000001;
    private static final int MAX_RUNS = 100;
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
    void simpleMultiplyLambdaTest(){
        assertEquals(v0*v1, GPUtils.multiply.apply(v0,v1),TOL);
    }

    @Test
    void simpleProtectedDivisionLambdaTest(){
        assertEquals(v0/v1, GPUtils.protectedDivision.apply(v0,v1),TOL);
        v1 = 0.0;
        assertEquals(1.0, GPUtils.protectedDivision.apply(v0,v1),TOL);
    }

    @Test
    void runTestMultipleTimes(){
        IntStream.range(0,MAX_RUNS).forEach(i -> {
            v0 = Math.random()*10 - 5.0;
            v1 = Math.random()*10 - 5.0;
            simpleAddLambdaTest();
            simpleMultiplyLambdaTest();
            simpleAddLambdaTest();
            simpleProtectedDivisionLambdaTest();
        });
    }

    @Test
    void testGPDefault1(){


    }

    @Test
    void testGPUtils(){
        FunctionNode testNode = new FunctionNode(GPUtils.add,new TerminalNode(1.0),new VariableNode(0));
        System.out.println(testNode.print());

        FunctionNode testNode2 = new FunctionNode(GPUtils.protectedDivision,testNode,new VariableNode(1));
        System.out.println(testNode2.print());
        
    }



}