import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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
        ((FunctionNode)node).addSubNode(leafNodes.get(0));
        TestUtils.assertNode(TestUtils.absOneNode,node);
    }


    @Test
    void generateFullTreeOfDepth1() {
        node = NodeUtils.generateFullTree(functionNodes.subList(0,1),leafNodes.subList(0,1),1);
        TestUtils.assertNodeSize(2,1,node);
        TestUtils.assertNode(TestUtils.absOneNode,node);
    }


    @Test
    void generateFullTreeOfDepth2() {
        node = NodeUtils.generateFullTree(functionNodes.subList(0,1),leafNodes.subList(0,1),2);
        TestUtils.assertNodeSize(3,2,node);
        TestUtils.assertNode(TestUtils.absabsOneNode,node);
    }


    @Test
    void generateFullTreeOfDepth3() {
        node = NodeUtils.generateFullTree(functionNodes.subList(0,1),leafNodes.subList(0,1),3);
        TestUtils.assertNodeSize(4,3,node);
        TestUtils.assertNode(TestUtils.absabsabsOneNode,node);
    }
}