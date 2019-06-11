import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class FunctionNodeTest {


    private static final double TOL = 0.000001;
    private static final String TESTCASE_FILENAME = "D:\\WS\\Java\\GeneticsAlgorithmsExamples\\SimpleQuadraticExample\\testcases\\testGetSubtreeAt.txt";
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
    void test1(){
        testTestCase("testcase001");
        testTestCase("testcase002");
        testTestCase("single001");
        testTestCase("single002");
        testTestCase("single003");
        testTestCase("single004");
        testTestCase("single005");
        testTestCase("single006");
    }

    void testTestCase(String testCase){

        List<String> testCaseStrings = getTestCase(testCase);
        assertEquals(3,testCaseStrings.size());
        var strings = Arrays.asList(testCaseStrings.get(0).split(","));
        node = GPUtils.createNodeFromString(strings.get(1));
        if (strings.get(2).equals("all")){
            for (int i = 1; i< node.size();i++){
                TestUtils.assertNode(GPUtils.createNodeFromString(strings.get(2).split(",")[i]),node.getSubtree(i).get());
            }
        } else{
            for (int i = 2; i< strings.get(2).length();i++){
                TestUtils.assertNode(GPUtils.createNodeFromString(strings.get(2).split(",")[i]),node.getSubtree(i).get());
            }
        }
        TestUtils.assertNode(node,node.getSubtree(0).get());
        // any index equal or greater should return empty
        assertTrue(node.getSubtree(node.size()).isEmpty());

    }


    List<String> getTestCase(String testcase){
        try {
            String[] testInfo = Files.lines(Path.of(TESTCASE_FILENAME))
                    .filter(line -> line.startsWith(testcase))
                    .findFirst()
                    .get()
                    .split(",");
            int testStart = Integer.valueOf(testInfo[1]);
            return Files.lines(Path.of(TESTCASE_FILENAME))
                    .skip(testStart-1)
                    .limit(3)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}