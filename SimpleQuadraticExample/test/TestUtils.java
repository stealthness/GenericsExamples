import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class TestUtils {

    private static final double TOL =  0.0001;
    static Double[] range1to1 = new Double[]{-1.0,1.0};
    static Double[] range1to2 = new Double[]{1.0,2.0};
    static Double[] range0to1 = new Double[]{0.0,1.0};
    static Node eNode = new EphemeralNode(range1to1);

    static Node zeroNode = new TerminalNode(0.0);
    static Node oneNode = new TerminalNode(1.0);
    static Node twoNode = new TerminalNode(2.0);
    static Node threeNode = new TerminalNode(3.0);
    static Node fourNode = new TerminalNode(4.0);


    static Node xNode = new VariableNode(0);
    static Node x0Node = new VariableNode(0);
    static Node x1Node = new VariableNode(1);
    static Node addNode = new FunctionNode(new GPBiFunction(GPUtils.addBiFunction),Arrays.asList(oneNode,oneNode));
    static Node multiplyNode = new FunctionNode(new GPBiFunction(GPUtils.multiplyBiFunction),Arrays.asList(oneNode,oneNode));

    static Node absZeronNode = new FunctionNode(new GPSingleFunction(GPUtils.abs,"abs"), Collections.singletonList(zeroNode));
    static Node absOneNode = new FunctionNode(new GPSingleFunction(GPUtils.abs,"abs"), Collections.singletonList(oneNode));
    static Node absTwoNode = new FunctionNode(new GPSingleFunction(GPUtils.abs,"abs"), Collections.singletonList(twoNode));
    static Node absThreeNode = new FunctionNode(new GPSingleFunction(GPUtils.abs,"abs"), Collections.singletonList(threeNode));
    static Node absFourNode = new FunctionNode(new GPSingleFunction(GPUtils.abs,"abs"), Collections.singletonList(fourNode));
    static Node absX0Node = new FunctionNode(new GPSingleFunction(GPUtils.abs,"abs"), Collections.singletonList(x0Node));
    static Node absX1Node = new FunctionNode(new GPSingleFunction(GPUtils.abs,"abs"), Collections.singletonList(x1Node));

    static Node absX0OneNode = new FunctionNode(new GPSingleFunction(GPUtils.abs,"abs"), List.of(x0Node,oneNode));
    static Node absTwoX1Node = new FunctionNode(new GPSingleFunction(GPUtils.abs,"abs"), List.of(twoNode, x1Node));
    static Node absOneTwoNode = new FunctionNode(new GPSingleFunction(GPUtils.abs,"abs"), List.of(oneNode, twoNode));
    static Node absZeroOneNode = new FunctionNode(new GPSingleFunction(GPUtils.abs,"abs"), List.of(zeroNode, oneNode));

    static Node xPlusOne = new FunctionNode(new GPBiFunction(GPUtils.addBiFunction,"+"),Arrays.asList(xNode,oneNode));
    static Node twoPlusOne = new FunctionNode(new GPBiFunction(GPUtils.addBiFunction,"+"),Arrays.asList(twoNode,oneNode));
    static Node onePlusTwo = new FunctionNode(new GPBiFunction(GPUtils.addBiFunction,"+"),Arrays.asList(oneNode,twoNode));
    static Node xPlusTwo = new FunctionNode(new GPBiFunction(GPUtils.addBiFunction,"+"),Arrays.asList(xNode,twoNode));
    static Node xPlusX = new FunctionNode(new GPBiFunction(GPUtils.addBiFunction,"+"),Arrays.asList(xNode,xNode));
    static Node onePlusX = new FunctionNode(new GPBiFunction(GPUtils.addBiFunction,"+"),Arrays.asList(oneNode,xNode));
    static Node twoPlusX = new FunctionNode(new GPBiFunction(GPUtils.addBiFunction,"+"),Arrays.asList(twoNode,xNode));
    static Node oneDivideX = new FunctionNode(new GPBiFunction(GPUtils.protectedDivisionBiFunction,"/"),Arrays.asList(oneNode,xNode));


    static Node recipOneNode = new FunctionNode(new GPSingleFunction(GPUtils.reciprocal,"recip"), Collections.singletonList(oneNode));

    static Node addOneTwoThree = new FunctionNode(new GPMultiFunction(GPUtils.addBiFunction,"+"),Arrays.asList(oneNode,twoNode,threeNode));
    static Node addXTwoThree = new FunctionNode(new GPMultiFunction(GPUtils.addBiFunction,"+"),Arrays.asList(xNode,twoNode,threeNode));
    static Node addOneXThree = new FunctionNode(new GPMultiFunction(GPUtils.addBiFunction,"+"),Arrays.asList(oneNode,xNode,threeNode));
    static Node addOneTwoX = new FunctionNode(new GPMultiFunction(GPUtils.addBiFunction,"+"),Arrays.asList(oneNode,twoNode,xNode));

    static Node xSqrd = new FunctionNode(new GPMultiFunction(GPUtils.multiplyBiFunction,"*"),Arrays.asList(xNode,xNode));
    static Node xSqrdPlusOneDivideX= new FunctionNode(new GPMultiFunction(GPUtils.addBiFunction,"+"),Arrays.asList(xSqrd,oneDivideX));



    static Node absabsOneNode = new FunctionNode(new GPSingleFunction(GPUtils.abs,"abs"), Collections.singletonList(absOneNode));
    static Node absabsabsOneNode = new FunctionNode(new GPSingleFunction(GPUtils.abs,"abs"), Collections.singletonList(absabsOneNode));

    static Node xPlusOneDivideX = new FunctionNode(new GPSingleFunction(GPUtils.addBiFunction,"+"), Arrays.asList(xNode, oneDivideX));
    static Node addOneXPlusOneTwoThree = new FunctionNode(new GPMultiFunction(GPUtils.addBiFunction,"+"),Arrays.asList(oneNode,xPlusOne,twoNode,threeNode));
    static Node absAddOneXPlusOneTwoThree = new FunctionNode(new GPMultiFunction(GPUtils.abs,"abs"),Collections.singletonList(addOneXPlusOneTwoThree));

    static Node xPlusOneDivideXSubtructAbsOnePlusX = new FunctionNode(new GPSingleFunction(GPUtils.subtractBiFunction,"-"), Arrays.asList(xPlusOneDivideX, absAddOneXPlusOneTwoThree));

    /**
     * default range of [-1.0,1.0]
     */
    static Double[] createRandomInput(int size){
        return createRandomInput(size, range1to1);
    }

    private static Double[] createRandomInput(int size, Double[] range){
        Double[] inputs = new Double[size];
        inputs[0] = 0.67;
        IntStream.range(0,size).forEach(i -> inputs[i] = getRandomInRange(range[0],range[1]));
        return inputs;
    }

    private static void assertInRange(Double[] expRange, Node actNode, int size, Double[] inputs, Double[] inputRange){
        Double actResult = actNode.calculate(inputs);
        assertTrue(actResult  <= expRange[1], String.format("%f is not less than %f",actResult, expRange[1]));
        assertTrue(actResult  >= expRange[0], String.format("%f is not greater than %f",actResult, expRange[0]));
    }

//    public static void assertInRange(Double[] expRange,Node actNode, int size){
//        assertEquals(Double[].class, range1to1.getClass());
//        assertInRange(expRange, actNode, size, createRandomInput(size, range1to1));
//    }

    static void assertInRange(Double[] expRange, Node actNode, int size, Double[] inputRange){
        assertInRange(expRange, actNode, size, createRandomInput(size, inputRange),inputRange);
    }



    static void assertNode(Double expResult, Node actNode, Double[] inputs){
        Double actResult = actNode.calculate(inputs);
        assertEquals(expResult,actResult ,TOL ,String.format("\nfunction : %s\ninputs %s\n",
                actNode.print(), Arrays.asList(inputs).toString()));
    }

    static void assertNode(Double expResult, Node actNode) {
        Double actResult = actNode.calculate(createRandomInput(1));
        assertEquals(expResult, actResult, TOL, String.format("\nfunction : %s",
                actNode.print()));
    }

    static void assertNode(Node expNode, Node actNode) {
        final String msg = String.format("\nfunction : %s",actNode.print());
        assertEquals(expNode.getClass(), actNode.getClass(), msg);
        assertNodeSize(expNode.size(),expNode.getDepth(),actNode);
        assertEquals(expNode.print(),actNode.print(),msg);
        if (expNode.getClass()== VariableNode.class){
            assertEquals(((VariableNode)expNode).getIndex(),((VariableNode)actNode).getIndex(),msg);
        } else if (expNode.getClass()== TerminalNode.class){
            assertEquals(((TerminalNode)expNode).getValue(),((TerminalNode)actNode).getValue(),msg);
        }
    }

    static void assertNodeSize(int expSize, int expDepth, Node actNode){
        final String msg = String.format("\nfunction : %s",actNode.print());
        assertEquals(expSize, actNode.size(),msg);
        assertEquals(expDepth, actNode.getDepth(),msg);
    }


    private static Double getRandomInRange(Double start, Double end){
        return Math.random()*(start+end)-start;
    }
}
