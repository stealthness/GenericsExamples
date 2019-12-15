
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class NodeImplTest {

    Node node;
    Node oneNode, twoNode;

    @BeforeEach
    void setUp(){
        node = new NodeImpl();
        oneNode = new NodeImpl(true);
        twoNode = new NodeImpl(true);
    }

    @Test
    void testCreate(){
        assertEquals(NodeImpl.class, node.getClass());
        assertEquals(NodeImpl.class, oneNode.getClass());
    }

    @Test
    void isConstantNodeIsATerminalNode() {
        assertFalse(node.isTerminalNode());
        assertTrue(oneNode.isTerminalNode());
        assertTrue(twoNode.isTerminalNode());
    }

    @Disabled
    @Test
    void calculate() {
        fail();
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