import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FunctionNodeImplTest {

    private static final double TOL = 0.0000001;
    FunctionNodeImpl addNode;
    TerminalNodeImpl oneNode = TestUtils.oneNode;
    TerminalNodeImpl twoNode = TestUtils.twoNode;

    @BeforeEach
    void setUp(){
        addNode = TestUtils.addNode1Plu2;
    }

    @Test
    void isTerminalNode() {
        assertFalse(addNode.isTerminal());
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
}