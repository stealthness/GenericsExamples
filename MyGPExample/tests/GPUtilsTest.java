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
    void testVariableNodes(){
        String[] expStrings = VARIABLE_TEST_CASES.split("\n");
        for (String expString : expStrings){
            Node actNode = GPUtils.createNode(expString);
            assertEquals(expString, actNode.toClojureString() ,"testcase :" +expString);
        }
    }


    final String TERMINAL_TEST_CASES = """
    (1.00)
    (2.00)
    (3.00)
    (0.123)
    (-3.333)""";

    final String VARIABLE_TEST_CASES = """
    (x0)
    (x1)
    (var3)
    (t2)""";
}
