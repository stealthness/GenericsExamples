import lombok.NonNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FunctionNodeImplTest {

    private static final double TOL = TestUtils.TOL;
    Node addNode;
    final Node oneNode = TestUtils.getConstantNode(1.0);
    final Node twoNode = TestUtils.getConstantNode(2.0);

    @BeforeEach
    void setUp(){
        addNode = TestUtils.getFunctionNode("+", Arrays.asList(oneNode,twoNode));
    }

    @Test
    void testCreate(){
        assertEquals(FunctionNodeImpl.class, addNode.getClass());
    }

    @Test
    void isTerminalNode() {
        assertFalse(addNode.isTerminalNode());
    }

    @Test
    void calculate() {
        assertEquals(3.0, addNode.calculate(null), TOL);
    }

    @Test
    void toClojureString() {
        assertEquals("(+ 1.00 2.00)", addNode.toClojureString());
    }

    @Test
    void testClone() {
        Node clone = addNode.clone();
        assertNotSame(clone,addNode);
    }

    @Test
    void getSubtree() {
        assertEquals(addNode, addNode.getSubtree(0));
        assertEquals(oneNode.toClojureString(), addNode.getSubtree(1).toClojureString());
        assertEquals(oneNode, addNode.getSubtree(1));
        assertEquals(twoNode.toClojureString(), addNode.getSubtree(2).toClojureString());
        assertEquals(twoNode, addNode.getSubtree(2));
    }

    @Test
    void testCalculateOnFunctionOfDepth2WithAdd(){
        testCalculationStrings(TEST_CASE_ADD_DEPTH_2.split("\n"));
    }

    @Test
    void testCalculateOnFunctionOfDepth2WithMultiple(){
        testCalculationStrings(TEST_CASE_MULTI_DEPTH_2.split("\n"));
    }

    @Test
    void testCalculateOnFunctionOfDepth2WithDivide(){
        testCalculationStrings(TEST_CASE_MULTI_DEPTH_3.split("\n"));
    }

    @Test
    void testCalculateOnFunctionOfDepth2WithSubtract(){
        testCalculationStrings(TEST_CASE_MULTI_DEPTH_4.split("\n"));
    }

    @NonNull
    protected void testCalculationStrings(String[] strings) {
        for (String string : strings) {
            String[] stringParts = string.split(";");
            if (!stringParts[2].equals("none")){
                // to do
            }
            Double[] inputs = new Double[]{1.0,2.0,-0.5};
            Node actNode = GPUtils.createNode(stringParts[0]);
            assertEquals(Double.parseDouble(stringParts[1]), actNode.calculate(inputs), TestUtils.TOL, "testcase :" + string);
        }
    }


    private static final String TEST_CASE_ADD_DEPTH_2 = """
            (+ 1.0 2.0);3.0;none
            (+ x0 2.0);3.0;none
            (+ 1.0 x1);3.0;none
            (+ x0 x1);3.0;none
            (+ -1.0 -2.0);-3.0;none""";
    
    private static final String TEST_CASE_MULTI_DEPTH_2 = """
            (* 1.0 2.0);2.0;none
            (* x0 2.0);2.0;none
            (* 1.0 x1);2.0;none
            (* x0 x1);2.0;none
            (* -1.0 -2.0);2.0;none""";
    
    private static final String TEST_CASE_MULTI_DEPTH_3 = """
            (/ 1.0 2.0);0.5;none
            (/ x0 2.0);0.5;none
            (/ 1.0 x1);0.5;none
            (/ x0 x1);0.5;none
            (/ -1.0 -2.0);0.5;none""";
    
    private static final String TEST_CASE_MULTI_DEPTH_4 = """
            (- 1.0 2.0);-1.0;none
            (- x0 2.0);-1.0;none
            (- 1.0 x1);-1.0;none
            (- x0 x1);-1.0;none
            (- -1.0 -2.0);1.0;none""";
}