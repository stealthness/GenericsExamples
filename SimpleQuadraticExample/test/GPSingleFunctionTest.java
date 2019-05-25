import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GPSingleFunctionTest {

    @Test
    void apply() {
    }

    @Test
    void testApplyAndGetIdentityOnOneNode() {
        Node identity = new FunctionNode(new GPSingleFunction(GPUtils.identity), new TerminalNode(1.0));
        assertEquals(1.0, ((FunctionNode)identity).calculate(TestUtils.createRandomInput(1)));
    }

    @Test
    void testApplyAndGetIdentityOnTwoNode() {
        Double expValue = 2.0;
        Node identityNode = new FunctionNode(new GPSingleFunction(GPUtils.identity), new TerminalNode(expValue ));
        TestUtils.assertNode(expValue,identityNode,TestUtils.createRandomInput(1));
    }

    @Test
    void testApplyAndGetIdentityOnENode() {
        Double expValue = 2.0;
        Node identityNode = new FunctionNode(new GPSingleFunction(GPUtils.identity), TestUtils.eNode);
        TestUtils.assertInRange(1,identityNode,TestUtils.range1to1,TestUtils.range1to1);
    }



    @Test
    void testApplyAndGetOnAbsFunctionalNode(){
        Double expValue = 1.0;
        Node absNode = new FunctionNode(new GPSingleFunction(GPUtils.abs),new TerminalNode(-expValue));
        TestUtils.assertNode(expValue,absNode);


        absNode = new FunctionNode(new GPSingleFunction(GPUtils.abs),new VariableNode(0));
        Double[] inputs = new Double[]{-expValue};
        TestUtils.assertNode(expValue,absNode,inputs);
    }


    @Test
    void testApplyAndGetOnProtectedReciprocalFunctionalNode(){
        Double expValue = 0.5;
        Node absNode = new FunctionNode(new GPSingleFunction(GPUtils.reciprocal), new TerminalNode(2.0));
        TestUtils.assertNode(expValue, absNode);

        absNode = new FunctionNode(new GPSingleFunction(GPUtils.reciprocal),new VariableNode(0));
        Double[] inputs = new Double[]{1/expValue};
        TestUtils.assertNode(expValue,absNode,inputs);
    }


    @Test
    void testApplyAndGetOnSinFunctionalNode(){
        Double expValue = 0.0;
        Node sinNode = new FunctionNode(new GPSingleFunction(GPUtils.sin), new TerminalNode(Math.PI));
        TestUtils.assertNode(expValue, sinNode);

        sinNode = new FunctionNode(new GPSingleFunction(GPUtils.sin),new VariableNode(0));
        Double[] inputs = new Double[]{Math.PI};
        TestUtils.assertNode(expValue,sinNode,inputs);
    }




}