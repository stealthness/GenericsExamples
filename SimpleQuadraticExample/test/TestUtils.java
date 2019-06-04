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
    public static Node addNode = new FunctionNode(new GPBiFunction(GPUtils.addBiFunction),Arrays.asList(oneNode,oneNode));
    public static Node multiplyNode = new FunctionNode(new GPBiFunction(GPUtils.multiplyBiFunction),Arrays.asList(oneNode,oneNode));


    public static Node xPlusOne = new FunctionNode(new GPBiFunction(GPUtils.addBiFunction),Arrays.asList(xNode,oneNode));
    public static Node xPlusTwo = new FunctionNode(new GPBiFunction(GPUtils.addBiFunction),Arrays.asList(xNode,twoNode));
    public static Node onePlusX = new FunctionNode(new GPBiFunction(GPUtils.addBiFunction),Arrays.asList(oneNode,xNode));
    public static Node twoPlusX = new FunctionNode(new GPBiFunction(GPUtils.addBiFunction),Arrays.asList(twoNode,xNode));


    public static Node absOneNode = new FunctionNode(new GPSingleFunction(GPUtils.abs,"abs"),Arrays.asList(oneNode));
    public static Node recipOneNode = new FunctionNode(new GPSingleFunction(GPUtils.reciprocal,"recip"),Arrays.asList(oneNode));


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
        return inputs;
    }

    public static void assertInRange(Double[] expRange, Node actNode, int size, Double[] inputs, Double[] inputRange){
        Double actResult = actNode.calculate(inputs);
        assertTrue(actResult  <= expRange[1], String.format("%f is not less than %f",actResult, expRange[1]));
        assertTrue(actResult  >= expRange[0], String.format("%f is not greater than %f",actResult, expRange[0]));
    }

    public static void assertInRange(Double[] expRange,Node actNode, int size){
        assertEquals(Double[].class, range1to1.getClass());
        assertInRange(expRange, actNode, size, createRandomInput(size, range1to1));
    }

    public static void assertInRange(Double[] expRange, Node actNode, int size, Double[] inputRange){
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

    public static void assertNode(Node expNode, Node actNode) {
        assertEquals(expNode.getClass(), actNode.getClass(), String.format("\nfunction : %s",
                actNode.print()));
        assertEquals(expNode.size(),actNode.size());
        assertEquals(expNode.getDepth(),actNode.getDepth());
        if (expNode.getClass()== VariableNode.class){
            assertEquals(((VariableNode)expNode).getIndex(),((VariableNode)actNode).getIndex());
        } else if (expNode.getClass()== TerminalNode.class){
            assertEquals(((TerminalNode)expNode).getValue(),((TerminalNode)actNode).getValue());
        }
    }

    public static void assertNodeSize(int expSize, int expDepth, Node actNode){
        assertEquals(expSize, actNode.size(),String.format("individual : %s", actNode.print()));
        assertEquals(expDepth, actNode.getDepth(),String.format("individual : %s", actNode.print()));
    }


    static Double getRandomInRange(Double start, Double end){
        return Math.random()*(start+end)-start;
    }
}
