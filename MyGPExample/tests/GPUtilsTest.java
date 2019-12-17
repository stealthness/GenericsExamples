import org.junit.jupiter.api.Test;

import java.text.DecimalFormat;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class GPUtilsTest {


    @Test
    void testTerminalNode1(){
        String expString = "(1.00)";
        Node actNode = GPUtils.createNode(expString);
        assertEquals(expString, actNode.toClojureString());
    }

    @Test
    void testTerminalNodes(){
        String[] expStrings = TERMINAL_TEST_CASES.split("\n");
        for (String expString : expStrings){
            Node actNode = GPUtils.createNode(expString);
            double expResult = Double.parseDouble(expString.replaceAll("[()]",""));
            assertEquals( String.format("(%.2f)", expResult), actNode.toClojureString() ,"testcase :" +expString);
        }
    }

    @Test
    void testVariableNodeX0(){
        String expString = "(x0)";
        Node actNode = GPUtils.createNode(expString);
        assertEquals(expString, actNode.toClojureString());
    }

    @Test
    void testVariableNodes(){
        String[] expStrings = VARIABLE_TEST_CASES.split("\n");
        for (String expString : expStrings){
            Node actNode = GPUtils.createNode(expString);
            assertEquals(expString, actNode.toClojureString() ,"testcase :" +expString);
        }
    }

    @Test
    void testSimplePlus1And1(){
        String expString = "(+ 1.00 2.00)";
        Node actNode = GPUtils.createNode("(+ 1.0 2.0)");
        assertEquals(expString, actNode.toClojureString());
    }

    @Test
    void testFunctionNodes(){
        testStrings(FUNCTION_TEST_CASES.split("\n"));
    }

    @Test
    void testNestFunctionNodesAtDepth2(){
        testStrings(NESTED_FUNCTION_TEST_CASES_1.split("\n"));
    }

    @Test
    void testNestFunctionNodesAtDepth3(){
        testStrings(NESTED_FUNCTION_TEST_CASES_2.split("\n"));
    }


    private void testStrings(String[] strings) {
        for (String string : strings) {
            String genString = string.split(";")[0];
            String expString = string.split(";")[1];
            Node actNode = GPUtils.createNode(genString);
            assertEquals(expString, actNode.toClojureString(), "testcase :" + expString);
        }
    }

    final String TERMINAL_TEST_CASES = """
    (1.00)
    (2.00)
    (3.00)
    (0.123)
    (12.765)
    (345.0)
    (-1452.9)
    (-0.00034)
    (-3.333)""";

    final String VARIABLE_TEST_CASES = """
    (x0)
    (x1)
    (x3)
    (var3)
    (t2)""";

    final String FUNCTION_TEST_CASES = """
            (+ 1.0 2.0);(+ 1.00 2.00)
            (+ 1.0 -2.0;(+ 1.00 -2.00)
            (- 1.0 2.0);(- 1.00 2.00)
            (- 1.0 1.897);(- 1.00 1.90)
            (- 1.234 2.0);(- 1.23 2.00)
            (* 1.0 2.0);(* 1.00 2.00)
            (/ 1.0 2.0);(/ 1.00 2.00)""";

    final String NESTED_FUNCTION_TEST_CASES_1 = """
            (+ 1.0 (+ 1.0 2.0));(+ 1.00 (+ 1.00 2.00))
            (+ 1.0 (- 1.0 2.0));(+ 1.00 (- 1.00 2.00))
            (+ (- 1.0 2.0) 1.0);(+ (- 1.00 2.00) 1.00)""";

    final String NESTED_FUNCTION_TEST_CASES_2 = """
            (+ 1.0 (+ 1.0 (* 1.0 2.0)));(+ 1.00 (+ 1.00 (* 1.00 2.00)))
            (+ (- 1.0 2.0) (- 1.0 2.0));(+ (- 1.00 2.00) (- 1.00 2.00))
            (+ (- 1.0 (/ 1.0 -2.0)) 1.0);(+ (- 1.00 (/ 1.00 -2.00)) 1.00)""";
}
