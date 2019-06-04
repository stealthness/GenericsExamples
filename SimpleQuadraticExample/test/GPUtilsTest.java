import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GPUtilsTest {

    Node node;
    private List<FunctionNode> functionNodes;
    private List<Node> leafNodes;

    @BeforeEach

    void setUp(){
        functionNodes = Arrays.asList(new FunctionNode(new GPSingleFunction(GPUtils.abs, "abs"),Arrays.asList()),
                                        new FunctionNode(new GPSingleFunction(GPUtils.reciprocal, "recip"),Arrays.asList()));
        leafNodes = Arrays.asList(TestUtils.oneNode, TestUtils.twoNode,TestUtils.xNode);
    }

    @Test
    void testSetUp(){
        node = functionNodes.get(0);
        ((FunctionNode)node).setSubNode(0,leafNodes.get(0));
        TestUtils.assertNode(TestUtils.absOneNode,node);
    }


    @Test
    void generateFullTreeOfDepth0() {

    }
}