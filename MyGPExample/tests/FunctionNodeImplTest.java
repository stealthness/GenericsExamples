import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class FunctionNodeImplTest {

    private static final double TOL = 0.0000001;
    FunctionNodeImpl addNode;
    TerminalNodeImpl oneNode = new TerminalNodeImpl(1.0);
    TerminalNodeImpl twoNode = new TerminalNodeImpl(2.0);

    @BeforeEach
    void setUp(){
        addNode = new FunctionNodeImpl(GPUtils.add, "+", Arrays.asList(oneNode,twoNode));
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