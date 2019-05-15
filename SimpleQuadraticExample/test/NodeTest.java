import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    private static final double TOL = 0.000001;
    BiFunction<Double,Double,Double> add = (a,b)-> a+b;
    BiFunction<Double,Double,Double> subtract = (a,b)-> a-b;
    BiFunction<Double,Double,Double> multiply = (a,b)-> a*b;
    BiFunction<Double,Double,Double> protectedDivision = (a,b)-> (b==0)?1.0:a/b;
    private double v0;
    private double v1;

    Node t0;
    Node t1;

    VariableNode x0;
    VariableNode x1;


    @BeforeEach
    void SetUp(){
        v0 = 1.2;
        v1 = -2.8;

        t0 = new TerminalNode(v0);
        t1 = new TerminalNode(v1);

        x0 = new VariableNode(0);
        x1 = new VariableNode(1);

    }

    @Test
    void getValueIfTerminal() {
        assertEquals(v0, t0.get(null),TOL);
        assertEquals(v1, t1.get(null),TOL);
    }

    @Test
    void printTerminal() {
        assertEquals(String.valueOf(v0), t0.print());
        assertEquals(String.valueOf(v1), t1.print());
    }


    @Test
    void simpleAddLambdaTest(){
        assertEquals(1.0, add.apply(0.4,0.6),TOL);
    }

    @Test
    void simpleSubtractLambdaTest(){
        assertEquals(-0.2, subtract.apply(0.4,0.6),TOL);
    }

    @Test
    void simpleMultiplayLambdaTest(){
        assertEquals(0.7, multiply.apply(0.5,1.4),TOL);
    }

    @Test
    void simpleProtectedDivisionLambdaTest(){
        assertEquals(1.0, protectedDivision.apply(3.3,0.0),TOL);
        assertEquals(1.2, protectedDivision.apply(3.6,3.0),TOL);
    }


    @Test
    void testNodeWithLambdaAndTwoTerminalNode(){
        var f0 = new FunctionNode(add,"+",t0,t1);
        assertEquals(v0+v1,f0.get(null),TOL);
    }

    @Test
    void testPrintNodeWithLambdaAndTwoTerminalNode(){
        var f0 = new FunctionNode(add,"+",t0,t1);
        var expStr = "( " + String.valueOf(v0) + " "+ f0.getFunctionString() + " " +String.valueOf(v1) + " )";
        assertEquals(expStr,f0.print());
        assertTrue(expStr.equals(f0.print()));
    }

    @Test
    void testNodeThatIsVariableNode(){
        var variables = new double[]{v0,v1};
        assertEquals(v0,x0.get(variables),TOL);
        assertEquals(v1,x1.get(variables),TOL);
    }

    @Test
    void testPrintVariableNode(){
        assertEquals("x0",x0.print());
        assertEquals("x1",x1.print());
    }

    @Test
    void testPrintAddFunctionNode(){
        var f0 = new FunctionNode(add,"+");
        assertEquals("( null + null )",f0.print());
    }

}