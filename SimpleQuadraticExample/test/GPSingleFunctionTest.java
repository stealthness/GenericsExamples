import org.junit.jupiter.api.Test;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class GPSingleFunctionTest {

    @Test
    void apply() {
    }

    @Test
    void testApplyAndGetIdentityOnOneNode() {
        Node identity = new FunctionNode(new GPSingleFunction(GPUtils.identity,"ID"), Collections.singletonList(new TerminalNode(1.0)));
        assertEquals(1.0, ((FunctionNode)identity).calculate(TestUtils.createRandomInput(1)));
    }

    @Test
    void testApplyAndGetIdentityOnTwoNode() {
        Double expValue = 2.0;
        Node identityNode = new FunctionNode(new GPSingleFunction(GPUtils.identity,"ID"), Collections.singletonList(new TerminalNode(expValue)));
        TestUtils.assertNode(expValue,identityNode,TestUtils.createRandomInput(1));
    }

    @Test
    void testApplyAndGetIdentityOnENode() {
        Double[] expRange = TestUtils.range1to1;
        Node identityNode = new FunctionNode(new GPSingleFunction(GPUtils.identity,"ID"), Collections.singletonList(TestUtils.eNode));
        TestUtils.assertInRange(expRange,identityNode,1,TestUtils.range1to1);
    }



    @Test
    void testApplyAndGetOnAbsFunctionalNode(){
        Double expValue = 1.0;
        Node absNode = new FunctionNode(new GPSingleFunction(GPUtils.abs,"ABS"), Collections.singletonList(new TerminalNode(-expValue)));
        TestUtils.assertNode(expValue,absNode);


        absNode = new FunctionNode(new GPSingleFunction(GPUtils.abs,"ABS"), Collections.singletonList(new VariableNode(0)));
        Double[] inputs = new Double[]{-expValue};
        TestUtils.assertNode(expValue,absNode,inputs);

    }


    @Test
    void testApplyAndGetOnProtectedReciprocalFunctionalNode(){
        Double expValue = 0.5;
        Node reciprocalNode = new FunctionNode(new GPSingleFunction(GPUtils.reciprocal, "1/x"), Collections.singletonList(new TerminalNode(2.0)));
        TestUtils.assertNode(expValue, reciprocalNode);

        reciprocalNode = new FunctionNode(new GPSingleFunction(GPUtils.reciprocal, "1/x"), Collections.singletonList(new VariableNode(0)));
        Double[] inputs = new Double[]{1/expValue};
        TestUtils.assertNode(expValue,reciprocalNode,inputs);

    }


    @Test
    void testApplyAndGetOnSinFunctionalNode(){
        Double expValue = 0.0;
        Node sinNode = new FunctionNode(new GPSingleFunction(GPUtils.sin,"sin"), Collections.singletonList(new TerminalNode(Math.PI)));
        TestUtils.assertNode(expValue, sinNode);

        sinNode = new FunctionNode(new GPSingleFunction(GPUtils.sin,"sin"), Collections.singletonList(new VariableNode(0)));
        Double[] inputs = new Double[]{Math.PI};
        TestUtils.assertNode(expValue,sinNode,inputs);
    }



}