import org.junit.jupiter.api.Test;

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
        String[] expStrings = TEST_CASES.split("\n");
        Node actNode = GPUtils.createNode(expStrings[0]);
        assertEquals(expStrings[0], actNode.toClojureString());
        assertEquals(expStrings[1], GPUtils.createNode(expStrings[1]).toClojureString());
    }


    final String TEST_CASES = """
    (1.00)
    (2.00)""";
}
