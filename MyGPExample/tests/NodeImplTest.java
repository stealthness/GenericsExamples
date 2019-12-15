
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeImplTest {

    private static final double TOL = 0.000000001;
    Node addNode;
    Node oneNode, twoNode;

    @BeforeEach
    void setUp(){
        addNode = new FunctionNodeImpl();
        oneNode = new TerminalNodeImpl(1.0);
        twoNode = new TerminalNodeImpl(2.0);
    }

    @Test
    void testCreate(){
        assertEquals(FunctionNodeImpl.class, addNode.getClass());
        assertEquals(TerminalNodeImpl.class, oneNode.getClass());
    }

    @Test
    void isConstantNodeIsATerminalNode() {
        assertFalse(addNode.isTerminalNode());
        assertTrue(oneNode.isTerminalNode());
        assertTrue(twoNode.isTerminalNode());
    }


    @Test
    void calculateAConstant() {
        assertEquals(1.0, oneNode.calculate(null), TOL);
        assertEquals(2.0, twoNode.calculate(null), TOL);
    }

    @Test
    void toClojureString() {

        assertEquals("(1.00)", oneNode.toClojureString());
        assertEquals("(2.00)", twoNode.toClojureString());
    }


    @Test
    void testClone() {
        Node cloneNode1 = addNode.clone();
        assertEquals(addNode.getClass(), cloneNode1.getClass());
        assertNotSame(cloneNode1, addNode);
        Node cloneNode2 = oneNode.clone();
        assertEquals(oneNode.getClass(), cloneNode2.getClass());
        assertNotSame(cloneNode2, oneNode);
    }


    @Test
    void getSubtree() {
       Node subNode = oneNode.getSubtree(0);
       assertEquals(subNode,oneNode);
       assertSame(subNode,oneNode);
       assertThrows(IndexOutOfBoundsException.class, () -> oneNode.getSubtree(1));
    }
}