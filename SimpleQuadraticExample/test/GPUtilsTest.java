import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GPUtilsTest {

    Node node;
    /**
     * list of Function Nodes that will drawn from in generating trees
     */
    private List<FunctionNode> functionNodes;
    /**
     * The list of terminal Nodes that will drawn from in generating trees
     */
    private List<Node> leafNodes;
    private List<GPFunction> functionList;

    @BeforeEach

    void setUp(){

        var absFunction = new FunctionNode(new GPSingleFunction(GPUtils.abs, "abs"),Arrays.asList());
        var reciprocalFunction =new FunctionNode(new GPSingleFunction(GPUtils.reciprocal, "recip"),Arrays.asList());
        functionList = Arrays.asList(new GPSingleFunction(GPUtils.abs, "abs"),new GPSingleFunction(GPUtils.reciprocal, "recip"));
        functionNodes = Arrays.asList(absFunction, reciprocalFunction);
        leafNodes = Arrays.asList(TestUtils.oneNode, TestUtils.twoNode, TestUtils.xNode);
    }

    @Test
    void testSetUp(){
        node = functionNodes.get(0);
        System.out.println(node.toClojureString());
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


    @Test
    void generateFullTreeOfDepth3ver2() {
        node = NodeUtils.generateNode(leafNodes.subList(0,1),functionList.subList(0,1),"full",3);
        TestUtils.assertNodeSize(4,3,node);
        TestUtils.assertNode(TestUtils.absabsabsOneNode,node);
    }

    @Test
    void generateGrowTreeOfDepth2(){

        node = NodeUtils.generateFullTree(functionNodes,leafNodes,3);
        TestUtils.assertNodeSize(4,3,node);
        List<String> possibleSolutions = Arrays.asList("(recip (recip (recip x)))","(recip (recip (abs x)))",
                "(recip (abs (recip x)))","(abs (recip (recip x)))","(recip (abs (abs x)))",
                "(abs (recip (abs x)))","(abs (abs (recip x)))","(abs (abs (abs x)))");
        System.out.println(node.toClojureString());
        assertTrue(possibleSolutions.stream().anyMatch(possibleSolution -> {
            return Arrays.asList("1.0", "2.0", "x0").stream().anyMatch(terminal -> {
                return possibleSolution.replace("x",terminal).equals(node.toClojureString());
            });
        }));

    }
}