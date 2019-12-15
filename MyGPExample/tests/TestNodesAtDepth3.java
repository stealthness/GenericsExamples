import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestNodesAtDepth3 {

    Double[] inputs = new Double[]{1.0,2.0, -1.5};

    @Test
    void testSubtract3from2(){
        Node actNode = TestUtils.getFunctionNode("-", Arrays.asList(TestUtils.getConstantNode(2.0),
                TestUtils.getConstantNode(3.0)));
        TestUtils.assertNode(Optional.of("(- 2.00 3.00)"),Optional.of(2),Optional.of(3),
                Optional.of(-1.0),Optional.of(inputs),actNode);

        assertNode(getTestCase(1), actNode);
    }
    @Test
    void testMulti2and3(){
        Node actNode = TestUtils.getFunctionNode("*", Arrays.asList(TestUtils.getConstantNode(2.0),
                TestUtils.getConstantNode(3.0)));
        TestUtils.assertNode(Optional.of("(* 2.00 3.00)"),Optional.of(2),Optional.of(3),
                Optional.of(6.0),Optional.of(inputs),actNode);

        assertNode(getTestCase(0), actNode);
    }

    void assertNode(String testcase, Node actNode) {
        String[] parts = testcase.split(",");
        TestUtils.assertNode(Optional.of(parts[0]),Optional.of(Integer.parseInt(parts[1])),
                Optional.of(Integer.parseInt(parts[2])), Optional.of(Double.parseDouble(parts[3])),
                Optional.of(inputs),actNode);

    }

    String getTestCase(int testCaseIndex){
        return TEST_CASES.split("\n")[testCaseIndex];
    }


    final static String TEST_CASES = """
            (* 2.00 3.00),2,3,6.0,[1.0;2.0;-1.5]
            (- 2.00 3.00),2,3,-1.0,[1.0;2.0;-1.5]
            (+ 2.00 3.00),2,3,5.0,[1.0;2.0;-1.5]""";

}
