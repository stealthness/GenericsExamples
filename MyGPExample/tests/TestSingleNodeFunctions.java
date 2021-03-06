import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This Test class is for testing single node function like abs, sin,...
 */
public class TestSingleNodeFunctions {

    private static final String NONE = "none";
    Node actNode;


    @Test
    void testIdentity(){
        testNode("(id 3.0);(id 3.00);2;2;3.0;(1.0,2.0,-0.5)");
    }

    @Test
    void testAbs(){
        testNode("(abs -3.0);(abs -3.00);2;2;3.0;(1.0,2.0,-0.5)");
    }

    @Test
    void testSin(){
        // testcase "(sin 0.0);(sin 0.00);2;2;0.0;(1.0,2.0,-0.5)"
        testNode(getTestCase(1,TEST_CASE_0));
    }

    @Test
    void testCos(){
        testNode("(cos 0.0);(cos 0.00);2;2;1.0;(1.0,2.0,-0.5)");
    }

    @Test
    void testReciprocal(){
        testNode("(reciprocal 2.0);(reciprocal 2.00);2;2;0.5;(1.0,2.0,-0.5)");
    }


    @Test
    void testAllTestCase(){
        Arrays.stream(TEST_CASE_0.split("/n")).forEach(testcase ->{
            testNode(testcase);
        });
    }


    private void testNode(String testcase){
        String[] parts = testcase.split(";");
        actNode = GPUtils.createNode(parts[0]);
        if (!parts[1].equals(NONE)){ // test toClojure String
            assertEquals(parts[1],actNode.toClojureString());
        }
        if (!parts[2].equals(NONE)){ // test depth
            assertEquals(Integer.parseInt(parts[2]),actNode.getDepth());
        }
        if (!parts[3].equals(NONE)){ // test size
            assertEquals(Integer.parseInt(parts[3]),actNode.size());
        }
        if (!parts[4].equals(NONE)){ // test calculate
            Double[] input = new Double[]{1.0,2.0,-0.5};
            assertEquals(Double.parseDouble(parts[4]),actNode.calculate(input),TestUtils.TOL);
        }
    }

    private String getTestCase(int n, String testCases){
        return testCases.split("\n")[n];
    }

    // TEST_CASE format
    // (<expr>); expClojureString; expDepth; expSize; expCaluation; input
    // 'none' to ignore or for default value

    final static String TEST_CASE_0 = """
            (abs 1.0);(abs 1.00);none;none;1.0;none
            (sin 0.0);(sin 0.00);2;2;0.0;(1.0,2.0,-0.5)
            (abs -3.0);(abs -3.00);2;2;3.0;(1.0,2.0,-0.5)""";
}
