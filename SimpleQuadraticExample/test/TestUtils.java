import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class TestUtils {

    private static final double TOL =  0.0001;
    public static Double [] range1to1 = new Double[]{-1.0,1.0};

    public static Node eNode = new EphemeralNode(range1to1);

    public static Node ozeroNode = new TerminalNode(0.0);
    public static Node oneNode = new TerminalNode(1.0);
    public static Node twoNode = new TerminalNode(2.0);
    public static Node threeNode = new TerminalNode(3.0);
    public static Node fourNode = new TerminalNode(4.0);


    public static Node xNode = new VariableNode(0);


    /**
     * default range of [-1.0,1.0]
     */
    public static Double[] createRandomInput(int size){
        return createRandomInput(size, range1to1);
    }

    public static Double[] createRandomInput(int size, Double[] range){
        Double[] inputs = new Double[size];
        IntStream.range(0,size).forEach(i -> inputs[i] = Math.random()*(range[0]+range[1])-range[0]);
        return inputs;
    }

    public static void assertInRange(int size,Node actNode, Double[] inputs, Double[] expRange){
        if( inputs == null){
            inputs = createRandomInput(size, expRange);
        }
        assertTrue(actNode.calculate(inputs) <= expRange[1]);
        assertTrue(actNode.calculate(inputs) >= expRange[0]);
    }

    public static void assertNode(Double expResult, Node actNode, Double[] inputs){
        Double actResult = actNode.calculate(inputs);
        assertEquals(expResult,actResult ,TOL ,String.format("\nfunction : %s\ninputs %s\n",
                actNode.print(), Arrays.asList(inputs).toString()));
    }

    public static void assertNode(Double expResult, Node actNode) {
        Double actResult = actNode.calculate(createRandomInput(1));
        assertEquals(expResult, actResult, TOL, String.format("\nfunction : %s",
                actNode.print()));
    }
}
