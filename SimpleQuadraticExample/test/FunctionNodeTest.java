import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class FunctionNodeTest {


    private static final double TOL = 0.000001;
    private Node node;


    @Test
    void testCreateFunctionNodeWithOneNode(){
        Node functionNode = new FunctionNode(new GPSingleFunction(GPUtils.identity, "ID"), TestUtils.oneNode);
        assertEquals(FunctionNode.class,functionNode.getClass());
    }

    @Test
    void testCreateFunctionNodeWithENode(){
        Node functionNode = new FunctionNode(new GPSingleFunction(GPUtils.identity,"ID"), TestUtils.eNode);
        assertEquals(FunctionNode.class,functionNode.getClass());
        assertEquals(TerminalNode.class,((FunctionNode)functionNode).getSubNode(0).get().getClass());
    }

    @Test
    void testGetSubtreeAt0(){
        var testList = Arrays.asList(TestUtils.oneNode, TestUtils.twoNode, TestUtils.threeNode, TestUtils.xNode);
        testList.stream().forEach(node -> TestUtils.assertNode(node,node.getSubtree(0).get()));
        testList.stream().forEach(node -> assertTrue(node.getSubtree(1).isEmpty()));

    }

    @Test
    void testGetSubtreeAt1(){
        var testList = Arrays.asList(TestUtils.xPlusOne, TestUtils.xPlusTwo, TestUtils.onePlusX, TestUtils.twoPlusX);
        var expList = Arrays.asList(TestUtils.xNode, TestUtils.xNode, TestUtils.oneNode, TestUtils.twoNode);
        IntStream.range(0,testList.size()).forEach(i ->{
            TestUtils.assertNodeSize(3,1,testList.get(i));
            TestUtils.assertNode(expList.get(i),testList.get(i).getSubtree(1).get());
        });
    }

    @Test
    void testGetSubtreeAt1ForSingleFunction(){

        var testList = Arrays.asList(TestUtils.absOneNode,TestUtils.recipOneNode);
        var expList = Arrays.asList(TestUtils.oneNode, TestUtils.oneNode);
        IntStream.range(0,testList.size()).forEach(i ->{
            TestUtils.assertNodeSize(2,1,testList.get(i));
            TestUtils.assertNode(testList.get(i),testList.get(i).getSubtree(0).get());
            TestUtils.assertNode(expList.get(i),testList.get(i).getSubtree(1).get());
            assertTrue(TestUtils.absOneNode.getSubtree(3).isEmpty());

        });
    }



    @Test
    void testAddSubNode(){

    }


    @Test
    void setSubNode() {
    }
}