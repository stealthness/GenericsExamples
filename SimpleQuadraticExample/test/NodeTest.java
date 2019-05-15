import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    private static final double TOL = 0.000001;

    @Test
    void getValueIfTerminal() {

        Node n0 = new ExNode(1.0);
        assertEquals(1.0, ((ExNode)n0).get());
        Node n1 = new ExNode(-2.0);
        assertEquals(-2.0, ((ExNode)n1).get());

    }


    @Test
    void simpleAddLambdaTest(){
        BiFunction<Double,Double,Double> add = (a,b)-> a+b;
        assertEquals(1.0, add.apply(0.4,0.6),TOL);
    }

    @Test
    void simpleSubtractLambdaTest(){
        BiFunction<Double,Double,Double> subtract = (a,b)-> a-b;
        assertEquals(-0.2, subtract.apply(0.4,0.6),TOL);
    }

    @Test
    void simpleMultiplayLambdaTest(){
        BiFunction<Double,Double,Double> multiply = (a,b)-> a*b;
        assertEquals(0.7, multiply.apply(0.5,1.4),TOL);
    }

    @Test
    void simpleProtectedDivisionLambdaTest(){
        BiFunction<Double,Double,Double> protectedDivision = (a,b)-> (b==0)?1.0:a/b;
        assertEquals(1.0, protectedDivision.apply(3.3,0.0),TOL);
        assertEquals(1.2, protectedDivision.apply(3.6,3.0),TOL);
    }


    @Test
    void testNodeWithLambdaAndTwoTerminalNode(){

        BiFunction<Double,Double,Double> add = (a,b)-> a+b;

        var v0 = 2.3;
        var v1 = -1.8;
        Node t0 = new ExNode(v0);
        Node t1 = new ExNode(v1);
        var f0 = new ExNode(add, t0,t1);

        assertEquals(v0+v1,f0.get(),TOL);

    }

    @Test
    void testNodeThatIsInputNode(){
        Node t0 = new ExNode(0);
        Node t1 = new ExNode(1);

        var v0 = 2.3;
        var v1 = -1.8;

        var inputs = new double[]{v0,v1};

        assertEquals(v0,inputs[0]);
        assertEquals(0,((ExNode)t0).getIndexOfInput());

        assertEquals(v0,t0.get(inputs),TOL);
        assertEquals(v1,t1.get(inputs),TOL);
    }

}