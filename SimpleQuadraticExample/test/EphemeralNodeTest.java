import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class EphemeralNodeTest {

    private static final int MAX_RUNS = 100;

    @Test
    void testCreate(){
        Node ephemeralNode = new EphemeralNode(new double[]{-5.0,5.0});
        IntStream.range(0,MAX_RUNS).forEach(i ->assertEphemeralNodeCreation(ephemeralNode.clone(),((EphemeralNode)ephemeralNode).getRange()));


    }

    private void assertEphemeralNodeCreation(Node createdNode,double[] range) {
        assertEquals(TerminalNode.class, createdNode.getClass());
        assertTrue(createdNode.apply(new double[]{0.0}) <= range[1]);
        assertTrue(createdNode.apply(new double[]{0.0}) >= range[0]);
    }

    @Test
    void testPrintZero(){
        Node zeroEphemeralNode = new EphemeralNode(new double[]{0.0,0.0});
        Node createdNode = zeroEphemeralNode.clone();
        assertEquals("0.0",createdNode.print());
    }

    @Test
    void testPrintIsInRange(){
        double[] range = new double[]{-1.0,2.0};
        Node ephemeralNode = new EphemeralNode(range);
        Node createdNode = ephemeralNode.clone();
        IntStream.range(0,MAX_RUNS).forEach(i ->assertPrintIsInRange(range,createdNode.print()));
    }

    private void  assertPrintIsInRange(double[] expRange, String actString){
        assertTrue(Double.valueOf(actString) < expRange[1], String.format("%s < %f is false",actString,expRange[1]));
        assertTrue(Double.valueOf(actString) > expRange[0], String.format("%s > %f is false",actString,expRange[0]));
    }

}