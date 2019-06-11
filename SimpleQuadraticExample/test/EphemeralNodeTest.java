import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class EphemeralNodeTest {

    private static final int MAX_RUNS = 100;
    private static final Double TOL = 0.000001;

    @Test
    void testCreate(){
        var expRange = new Double[]{-5.0,5.0};
        Node ephemeralNode = new EphemeralNode(expRange);
        IntStream.range(0,MAX_RUNS).forEach(i ->assertEphemeralNodeCreation(expRange,ephemeralNode.clone(), ((EphemeralNode)ephemeralNode).getRange()));


    }

    @Test
    void testPrintZero(){
        Node zeroEphemeralNode = new EphemeralNode(new Double[]{0.0,0.0});
        Node createdNode = zeroEphemeralNode.clone();
        assertEquals("0.0",createdNode.toClojureString());
    }

    @Test
    void testPrintIsInRange(){
        Double[] range = new Double[]{-1.0,2.0};
        Node ephemeralNode = new EphemeralNode(range);
        IntStream.range(0,MAX_RUNS).forEach(i ->{
            Node createdNode = ephemeralNode.clone();
            assertPrintIsInRange(range,createdNode.toClojureString());
        });
    }


    private void assertEphemeralNodeCreation(Double[] expRange, Node actNode, Double[] actRange) {
        assertEquals(TerminalNode.class, actNode.getClass());
        assertPrintIsInRange(expRange,actNode.toClojureString());
        assertEphemeralRange(expRange,actRange);
        assertEphemeralCalculation(expRange,actNode);
    }

    private void  assertPrintIsInRange(Double[] expRange, String actString){
        assertTrue(Double.valueOf(actString) <= expRange[1], String.format("%s < %f is false",actString,expRange[1]));
        assertTrue(Double.valueOf(actString) >= expRange[0], String.format("%s > %f is false",actString,expRange[0]));
    }


    private void assertEphemeralRange(Double[] expRange, Double[] actRange){
        assertEquals(expRange[0],actRange[0],TOL);
        assertEquals(expRange[1],actRange[1],TOL);
    }

    private void assertEphemeralCalculation(Double[] expRange,  Node actNode){
        assertTrue(actNode.calculate(null) <= expRange[1]);
        assertTrue(actNode.calculate(null) >= expRange[0]);
    }
}