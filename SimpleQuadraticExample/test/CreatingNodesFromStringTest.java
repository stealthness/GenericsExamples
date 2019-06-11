import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Purpose of this class is to test creating Nodes from strings
 */
public class CreatingNodesFromStringTest {


    @Test
    void testTreesOfSize1(){
        var actStrings = Arrays.asList("(0.0)","(1.0)","(2.0)","(3.0)","(4.0)","(x0)","(x1)");
        var expNodes = Arrays.asList(TestUtils.zeroNode,TestUtils.oneNode,TestUtils.twoNode,TestUtils.threeNode,
                TestUtils.fourNode,TestUtils.x0Node,TestUtils.x1Node);

        assertNodesFromStrings(actStrings, expNodes);
    }

    @Test
    void testTreesOfSize2(){
        var actStrings = Arrays.asList("(abs 0.0)","(abs 1.0)","(abs 2.0)","(abs 3.0)","(abs 4.0)","(abs x0)","(abs x1)");
        var expNodes = Arrays.asList(TestUtils.absZeronNode,TestUtils.absOneNode,TestUtils.absTwoNode,TestUtils.absThreeNode,
                TestUtils.absFourNode,TestUtils.absX0Node,TestUtils.absX1Node);
        assertNodesFromStrings(actStrings, expNodes);
    }

    @Test
    void testTreesOfSize3Depth1(){
        var actStrings = Arrays.asList("(abs 0.0 1.0)","(abs 1.0 2.0)","(abs x0 1.0)","(abs 2.0 x1)");
        var expNodes = Arrays.asList(TestUtils.absZeroOneNode,TestUtils.absOneTwoNode,TestUtils.absX0OneNode,TestUtils.absTwoX1Node);
        assertNodesFromStrings(actStrings, expNodes);
    }

    @Test
    void testTreesOfSize3Depth2(){
        var actStrings = Arrays.asList("(+ x0 1.0)","(/ 1.0 x0)","(* x0 x0)","(+ 2.0 x0)");
        var expNodes = Arrays.asList(TestUtils.xPlusOne,TestUtils.oneDivideX,TestUtils.xSqrd,TestUtils.twoPlusX);
        assertNodesFromStrings(actStrings, expNodes);
    }

    @Test
    void testTreesOfSize4Depth1(){
        var actStrings = Arrays.asList("(+ 1.0 2.0 3.0)","(+ 1.0 2.0 x0)");
        var expNodes = Arrays.asList(TestUtils.addOneTwoThree,TestUtils.addOneTwoX);
        assertNodesFromStrings(actStrings, expNodes);
    }

    @Test
    void testTreesOfSizeOneFunctionAtDepth1(){
        var actStrings = Arrays.asList("(+ 1.0 (+ x0 1.0) 2.0 3.0)","(+ x0 (/ 1.0 x0))");
        var expNodes = Arrays.asList(TestUtils.addOneXPlusOneTwoThree,TestUtils.xPlusOneDivideX);
        assertNodesFromStrings(actStrings, expNodes);
    }

    @Test
    void testTreesOfSize3Depth3(){
        var actStrings = Arrays.asList("(abs 1.0)", "(abs (abs 1.0))","(abs (abs (abs 1.0)))");
        var expNodes = Arrays.asList(TestUtils.absOneNode,TestUtils.absabsOneNode,TestUtils.absabsabsOneNode);
        assertNodesFromStrings(actStrings, expNodes);
    }

    @Test
    void testHardCase(){
        var actStrings = Arrays.asList("(- (+ x0 (/ 1.0 x0)) (abs (+ 1.0 (+ x0 1.0) 2.0 3.0)))");
        var expNodes = Arrays.asList(TestUtils.xPlusOneDivideXSubtructAbsOnePlusX);
        assertNodesFromStrings(actStrings, expNodes);
    }



    private void assertNodesFromStrings(List<String> actStrings, List<Node> expNodes) {
        for (int i = 0 ; i< actStrings.size() ; i++){
            if (expNodes.get(0).size() == 1){ // is TerminalNode or VariableNode
                assertEquals(expNodes.get(i).toClojureString(),actStrings.get(i).replace("("," ").replace(")"," ").strip());
            }else{  // Node is FunctionNode
                assertEquals(expNodes.get(i).toClojureString(),actStrings.get(i));
            }
        }
        for (int i = 0 ; i< actStrings.size() ; i++){
            Node actNode = NodeUtils.createNodeFromString(actStrings.get(i));
            //System.out.println(String.format("From String : %s  ActNode : %s    expNode : %s",actStrings.get(i),(actNode==null)?"null":actNode.toClojureString(), expNodes.get(i).toClojureString()));
            TestUtils.assertNode(expNodes.get(i),actNode);
            assertEquals(expNodes.get(i).toClojureString(),actNode.toClojureString());
        }
    }


}
