import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NodeSetANdGetTest {


    @Test
    void testSimpleDepth1Tree(){

        testSimpleChangeNodeAt(
                Arrays.asList(TestUtils.xNode,TestUtils.oneNode),
                Arrays.asList(TestUtils.xPlusOne,TestUtils.xPlusX),
                TestUtils.xPlusOne,TestUtils.xNode);

        testSimpleChangeNodeAt(
                Arrays.asList(TestUtils.oneNode,TestUtils.twoNode,TestUtils.threeNode),
                Arrays.asList(TestUtils.addXTwoThree,TestUtils.addOneXThree,TestUtils.addOneTwoX),
                TestUtils.addOneTwoThree,TestUtils.xNode);

        testSimpleChangeNodeAt(
                Arrays.asList(TestUtils.oneNode,TestUtils.twoNode,TestUtils.threeNode),
                Arrays.asList(TestUtils.addXTwoThree,TestUtils.addOneXThree,TestUtils.addOneTwoX),
                TestUtils.addOneTwoThree,TestUtils.xNode);
    }

    @Test
    void testTreeDepth2(){
        Node testNode = TestUtils.xSqrdPlusOneDivideX;
        List<Node> expNodeAtIndex = Arrays.asList(TestUtils.xSqrd,TestUtils.xNode,TestUtils.xNode,TestUtils.oneDivideX,TestUtils.oneNode,TestUtils.xNode);
        IndividualTest.testGetNode(expNodeAtIndex,testNode);
        assertEquals(2.0,testNode.calculate(new Double[]{1.0}),0.0001);
        assertEquals(0.0,testNode.calculate(new Double[]{-1.0}),0.0001);
        assertEquals(2.25,testNode.calculate(new Double[]{0.5}),0.0001);
    }

    void testSimpleChangeNodeAt(List<Node> expNodeAtIndex, List<Node> expChangeNodes,Node testNode,Node changeNode){
        IndividualTest.testGetNode(expNodeAtIndex,testNode);
        for (int i = 0; i < testNode.size()-1;i++){
            System.out.println("i : "+i);
            Node newNode = testNode.clone();
            ((FunctionNode)newNode).replaceSubtreeAt(i+1,changeNode);
            TestUtils.assertNode(expChangeNodes.get(i),newNode);
        }
    }
}
