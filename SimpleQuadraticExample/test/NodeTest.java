import org.junit.jupiter.api.Test;

import java.util.function.BiFunction;

import static org.junit.jupiter.api.Assertions.*;

class NodeTest {

    private static final double TOL = 0.000001;

    @Test
    void getValueIfTerminal() {

        Node n0 = new Node(1.0);
        assertEquals(1.0, n0.get());
        Node n1 = new Node(-2.0);
        assertEquals(-2.0, n1.get());

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
    void testNodeWithLambdAndTwoTerminalNode(){

    }

    @Test
    void testNodeThatIsInputNode(){
        Node t0 = new Node(0);
        Node t1 = new Node(1);

        var inputs = new double[]{2.3,-1.9};
    }

}