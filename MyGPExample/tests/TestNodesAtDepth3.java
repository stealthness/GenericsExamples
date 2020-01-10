import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.Optional;


public class TestNodesAtDepth3 {

    //Double[] inputs = new Double[]{1.0,2.0, -1.5};

    @Test
    void testMulti2and3(){
        Node actNode = TestUtils.getFunctionNode("*", Arrays.asList(TestUtils.getConstantNode(2.0),
                TestUtils.getConstantNode(3.0)));

        assertNode(getTestCase(0), actNode);
    }

    @Test
    void testSubtract3from2(){
        Node actNode = TestUtils.getFunctionNode("-", Arrays.asList(TestUtils.getConstantNode(2.0),
                TestUtils.getConstantNode(3.0)));

        assertNode(getTestCase(1), actNode);
    }

    @Test
    void testAdd2and3(){
        Node actNode = TestUtils.getFunctionNode("+", Arrays.asList(TestUtils.getConstantNode(2.0),
                TestUtils.getConstantNode(3.0)));

        assertNode(getTestCase(2), actNode);
    }

    @Test
    void testDivide3from2(){
        Node actNode = TestUtils.getFunctionNode("/", Arrays.asList(TestUtils.getConstantNode(2.0),
                TestUtils.getConstantNode(3.0)));

        assertNode(getTestCase(3), actNode);
    }

    @Test
    void runAllTestCases(){
        String[] testcases = TEST_CASES.split("\n");
        for (String testcase : testcases){
            System.out.println(testcase.split(",")[0]);
            Node actNode = GPUtils.createNode(testcase.split(",")[0]);
            assertNode(testcase, actNode);
        }

    }



    void assertNode(String testcase, Node actNode) {
        System.out.println(testcase);
        String[] parts = testcase.split(",");
        Double[] inputs = Arrays.stream(parts[4]
                .replaceAll("[\\[\\]]", "").split(";"))
                .map(Double::parseDouble)
                .toArray(Double[]::new);
        TestUtils.assertNode(Optional.of(parts[0]),Optional.of(Integer.parseInt(parts[1])),
                Optional.of(Integer.parseInt(parts[2])), Optional.of(Double.parseDouble(parts[3])),
                Optional.of(inputs),actNode);

    }

    String getTestCase(int testCaseIndex){
        return TEST_CASES.split("\n")[testCaseIndex];
    }


    // TEST_CASES in the form
    // (<expression>), expDepth, expSize, expCalculation, input
    final static String TEST_CASES = """
            (* 2.00 3.00),2,3,6.0,[1.0;2.0;-1.5]
            (- 2.00 3.00),2,3,-1.0,[1.0;2.0;-1.5]
            (+ 2.00 3.00),2,3,5.0,[1.0;2.0;-1.5]
            (/ 2.00 3.00),2,3,0.6666666666666666,[1.0;2.0;-1.5]
            (abs -2.00), 2, 2, 2.00, [1.0;2.0;-1.5]
            (abs 2.00), 2, 2, 2.00, [1.0;2.0;-1.5]""";

}
