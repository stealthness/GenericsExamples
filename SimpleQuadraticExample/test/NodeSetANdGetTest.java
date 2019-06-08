import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

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



    void testSimpleChangeNodeAt(List<Node> expNodeAtIndex, List<Node> expChangeNodes,Node testNode,Node changeNode){
        IndividualTest.testGetNode(expNodeAtIndex,testNode);
        for (int i = 0; i < testNode.size()-1;i++){
            System.out.println("i : "+i);
            Node newNode = testNode.clone();
            ((FunctionNode)newNode).setSubNodeAt(i+1,changeNode);
            TestUtils.assertNode(expChangeNodes.get(i),newNode);
        }
    }
}
