import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class FunctionNodeTest {


    private static final double TOL = 0.000001;
    private static final String TESTCASE_FILENAME = "testcases\\testGetSubtreeAt.txt";
    private Node node;

    private static void accept(Node node) {
        assertTrue(node.getSubtree(1).isEmpty());
    }


    @Test
    void testCreateFunctionNodeWithOneNode(){
        Node functionNode = new FunctionNode(new GPSingleFunction(GPUtils.identity, "ID"), Collections.singletonList(TestUtils.oneNode));
        assertEquals(FunctionNode.class,functionNode.getClass());
    }



    @Test
    void testGetSubtreeAt0(){
        var testList = Arrays.asList(TestUtils.oneNode, TestUtils.twoNode, TestUtils.threeNode, TestUtils.xNode);
        for (Node node1 : testList) {
            TestUtils.assertNode(node1, node1.getSubtree(0).get());
        }
        testList.forEach(FunctionNodeTest::accept);
    }


    @Test
    void testGetSubtreeAt(){
        testTestCase("testcase001");
        testTestCase("testcase002");
    }

    @Test
    void testGetSubtreeAtForSingleFunction(){
        testTestCase("single001");
        testTestCase("single002");
        testTestCase("single003");
        testTestCase("single004");
        testTestCase("single005");
        testTestCase("single006");
    }

    @Test
    void testGetSubtreeAtMultipleFunction(){
        testTestCase("multi001");
        testTestCase("multi002");
    }

    void testTestCase(String testCase){

        List<String> testCaseStrings = TestUtils.getTestCase(testCase,TESTCASE_FILENAME, Optional.of(3));
        assertEquals(3,testCaseStrings.size());
        var strings = Arrays.asList(testCaseStrings.get(0).split(","));
        node = NodeUtils.createNodeFromString(strings.get(1));
        if (strings.get(2).equals("all")){
            for (int i = 1; i< node.size();i++){
                TestUtils.assertNode(NodeUtils.createNodeFromString(strings.get(2).split(",")[i]),node.getSubtree(i).get());
            }
        } else{
            for (int i = 2; i< strings.get(2).length();i++){
                TestUtils.assertNode(NodeUtils.createNodeFromString(strings.get(2).split(",")[i]),node.getSubtree(i).get());
            }
        }
        TestUtils.assertNode(node,node.getSubtree(0).get());
        // any index equal or greater should return empty
        assertTrue(node.getSubtree(node.size()).isEmpty());

    }

}