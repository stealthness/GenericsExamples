import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestNodesAtDepth3 {

    Double[] input = new Double[]{1.0,2.0, -1.5};

    @Test
    void testSubtract3from2(){
        Node actNode = TestUtils.getFunctionNode("-", Arrays.asList(TestUtils.getConstantNode(1.0),TestUtils.getConstantNode(3.0)));
        double expResult = -2.0;
        String expString = "(- 1.00 3.00)";
        assertEquals(expResult, actNode.calculate(input));
        assertEquals(expString, actNode.toClojureString());
        assertEquals(2, actNode.getDepth());
        assertEquals(3, actNode.size());


    }

}
