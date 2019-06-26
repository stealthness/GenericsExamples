import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

class NodeTerminalTest {

    private static final double TOL = 0.000001;
    private double v0;
    private double v1;

    private Node<Double[], Double> t0;
    private Node<Double[], Double> t1;

    @BeforeEach
    void SetUp(){
        v0 = 1.2;
        v1 = -2.8;

        t0 = new NodeTerminal(v0);
        t1 = new NodeTerminal(v1);
    }

    @Test
    void getValueIfTerminal() {
        assertEquals(v0, t0.calculate(null),TOL);
        assertEquals(v1, t1.calculate(null),TOL);
    }

    @Test
    void printTerminal() {
        assertEquals(String.valueOf(v0), t0.toClojureString());
        assertEquals(String.valueOf(v1), t1.toClojureString());
        assertEquals("1.0",TestUtils.oneNode.toClojureString());
        assertEquals("2.0",TestUtils.twoNode.toClojureString());
    }


    // test Equality

    @Test
    void testEquality(){
        assertEquals(TestUtils.oneNode, new NodeTerminal(1.0));
        assertNotEquals(TestUtils.twoNode, TestUtils.oneNode);
    }

    @Test
    void testSizeAndDepth(){
        List<Node> testList = List.of(t0,t1,TestUtils.oneNode,TestUtils.twoNode);
        testList.forEach(this::assertNodeDimension);
    }

    private void assertNodeDimension(Node<Double[], Double> actNode) {
        assertEquals(0,actNode.getDepth());
        assertEquals(1,actNode.size());
    }

}