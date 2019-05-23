import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertEquals;

public class EditingNodesTest {

    @Test
    void testDivedByZero(){
        Node testNode = TestUtils.oneDivideByZeroTree.clone();
        Node expNode = new TerminalNode(1.0);

        assertEquals(expNode.print(),testNode.print());
    }
}
