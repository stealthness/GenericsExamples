import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TerminalNodeTest {

    private static final double TOL = 0.000001;
    private double v0;
    private double v1;

    private Node t0;
    private Node t1;

    @BeforeEach
    void SetUp(){
        v0 = 1.2;
        v1 = -2.8;

        t0 = new TerminalNode(v0);
        t1 = new TerminalNode(v1);
    }

    @Test
    void getValueIfTerminal() {
        assertEquals(v0, t0.calculate(null),TOL);
        assertEquals(v1, t1.calculate(null),TOL);
    }

    @Test
    void printTerminal() {
        assertEquals(String.valueOf(v0), t0.toTreeString());
        assertEquals(String.valueOf(v1), t1.toTreeString());
        assertEquals("1.0",TestUtils.oneNode.toTreeString());
        assertEquals("2.0",TestUtils.twoNode.toTreeString());
    }


    // test Equality

    @Test
    void testEquality(){
        assertEquals(TestUtils.oneNode, new TerminalNode(1.0));
        assertNotEquals(TestUtils.twoNode, TestUtils.oneNode);
        assertNotEquals(TestUtils.oneNode, TestUtils.xNode);
    }

    @Test
    void testSizeAndDepth(){
        List<Node> testList = List.of(t0,t1,TestUtils.oneNode,TestUtils.twoNode,TestUtils.xNode);
        testList.forEach(node -> assertNodeDimension(1,0,node));
    }

    private void assertNodeDimension(int expSize, int expDepth, Node actNode) {
        assertEquals(expDepth,actNode.getDepth());
        assertEquals(expSize,actNode.size());
    }

}