
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeImplTest {

    private static final double TOL = 0.000000001;
    Node node;
    Node oneNode, twoNode;

    @BeforeEach
    void setUp(){
        node = new NodeImpl();
        oneNode = new TerminalNodeImpl(1.0);
        twoNode = new TerminalNodeImpl(2.0);
    }

    @Test
    void testCreate(){
        assertEquals(NodeImpl.class, node.getClass());
        assertEquals(TerminalNodeImpl.class, oneNode.getClass());
    }

    @Test
    void isConstantNodeIsATerminalNode() {
        assertFalse(node.isTerminalNode());
        assertTrue(oneNode.isTerminalNode());
        assertTrue(twoNode.isTerminalNode());
    }


    @Test
    void calculateAConstant() {
        assertEquals(1.0, oneNode.calculate(null), TOL);
        assertEquals(2.0, twoNode.calculate(null), TOL);
    }

    @Disabled
    @Test
    void toClojureString() {
        fail();
    }

    @Disabled
    @Test
    void testClone() {
        fail();
    }

    @Disabled
    @Test
    void getSubtree() {
        fail();
    }
}