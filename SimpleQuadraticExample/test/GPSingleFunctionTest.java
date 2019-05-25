import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GPSingleFunctionTest {

    @Test
    void apply() {
    }

    @Test
    void testApplyAndGetIdentityOnOneNode() {
        Node identity = new FunctionNode(new GPSingleFunction(GPUtils.identity), new TerminalNode(1.0));
        assertEquals(1.0, ((FunctionNode)identity).get(TestUtils.createRandomInput(1)));
    }

    @Test
    void testApplyAndGetIdentityOnTwoNode() {
        Double expValue = 2.0;
        Node identity = new FunctionNode(new GPSingleFunction(GPUtils.identity), new TerminalNode(expValue ));
        assertEquals(expValue , ((FunctionNode)identity).get(TestUtils.createRandomInput(1)));
    }

    @Test
    void testApplyAndGetIdentityOnENode() {
        Double expValue = 2.0;
        Node identity = new FunctionNode(new GPSingleFunction(GPUtils.identity), TestUtils.eNode);
        TestUtils.assertInRange(1,identity,TestUtils.range1to1,TestUtils.range1to1);
    }

}