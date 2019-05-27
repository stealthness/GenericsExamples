import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class TestUtils {

    private static final double TOL =  0.0001;
    public static Double[] range1to1 = new Double[]{-1.0,1.0};
    public static Double[] range1to2 = new Double[]{1.0,2.0};
    public static Double[] range0to1 = new Double[]{0.0,1.0};
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
        inputs[0] = 0.67;
        IntStream.range(0,size).forEach(i -> inputs[i] = getRandomInRange(range[0],range[1]));

        System.out.println("<input0> "+inputs[0]);
        return inputs;
    }

    public static void assertInRange(Double[] expRange, Node actNode, int size, Double[] inputs, Double[] inputRange){
        System.out.println("<AinR4> node"+actNode.print());
        System.out.println(inputRange[0]);
        System.out.println(inputRange[1]);
        Double actResult = actNode.calculate(inputs);
        assertTrue(actResult  <= expRange[1], String.format("%f is not less than %f",actResult, expRange[1]));
        assertTrue(actResult  >= expRange[0], String.format("%f is not greater than %f",actResult, expRange[0]));
    }

    public static void assertInRange(Double[] expRange,Node actNode, int size){
        System.out.println("<2>"+actNode.print());
        System.out.println(range1to1[0]);
        System.out.println(range1to1[1]);
        System.out.println(size);
        assertEquals(Double[].class, range1to1.getClass());
        assertInRange(expRange, actNode, size, createRandomInput(size, range1to1));
    }

    public static void assertInRange(Double[] expRange, Node actNode, int size, Double[] inputRange){
        System.out.println("<AinR> node : "+ actNode.print());
        assertInRange(expRange, actNode, size, createRandomInput(size, inputRange),inputRange);
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


    static Double getRandomInRange(Double start, Double end){
        return Math.random()*(start+end)-start;
    }
}
