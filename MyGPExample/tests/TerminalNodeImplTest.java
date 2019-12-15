
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TerminalNodeImplTest {

    private static final double TOL = 0.000000001;
    Node addNode;
    Node oneNode, twoNode;
    Node x0Node,x1Node;

    @BeforeEach
    void setUp(){
        addNode = new FunctionNodeImpl();
        oneNode = TestUtils.getConstantNode(1.0);
        twoNode = TestUtils.getConstantNode(2.0);
        x0Node = new VariableNodeImpl("x0",0);
        x1Node = new VariableNodeImpl("x1",1);
    }

    @Test
    void testCreate(){
        assertEquals(FunctionNodeImpl.class, addNode.getClass());
        assertEquals(TerminalNodeImpl.class, oneNode.getClass());
        assertEquals(VariableNodeImpl.class, x0Node.getClass());
    }

    @Test
    void isConstantNodeIsATerminalNode() {
        assertFalse(addNode.isTerminalNode());
        assertTrue(oneNode.isTerminalNode());
        assertTrue(x0Node.isTerminalNode());
    }


    @Test
    void calculateAConstant() {
        assertEquals(1.0, oneNode.calculate(null), TOL);
        assertEquals(2.0, twoNode.calculate(null), TOL);
    }

    @Test
    void calculateVariable() {
        assertEquals(1.0, x0Node.calculate(new Double[]{1.00, 2.00}), TOL);
        assertEquals(2.0, x1Node.calculate(new Double[]{1.00, 2.00}), TOL);
    }

    @Test
    void toClojureString() {
        assertEquals("(1.00)", oneNode.toClojureString());
        assertEquals("(2.00)", twoNode.toClojureString());
        assertEquals("(x0)", x0Node.toClojureString());
        assertEquals("(x1)", x1Node.toClojureString());
    }


    @Test
    void testCloneConstantNode() {
        Node cloneNode2 = oneNode.clone();
        assertEquals(oneNode.getClass(), cloneNode2.getClass());
        assertNotSame(cloneNode2, oneNode);
    }
    @Test
    void testCloneVariableNode() {
        Node cloneNode1 = x0Node.clone();
        assertEquals(x0Node.getClass(), cloneNode1.getClass());
        assertNotSame(cloneNode1, x0Node);
    }


    @Test
    void getSubtreeForConstantNode() {
       Node subNode = oneNode.getSubtree(0);
       assertEquals(subNode,oneNode);
       assertSame(subNode,oneNode);
       assertThrows(IndexOutOfBoundsException.class, () -> oneNode.getSubtree(1));
    }
    @Test
    void getSubtreeForVariableNode() {
       Node subNode = x0Node.getSubtree(0);
       assertEquals(subNode,x0Node);
       assertSame(subNode,x0Node);
       assertThrows(IndexOutOfBoundsException.class, () -> x0Node.getSubtree(1));
    }
}