import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NodeSetANdGetTest {

    private static final String TESTCASE_FILENAME = "testcases//nodeReplaceTreeAt.txt";

    @Test
    void test1(){
        testTestcase("testcase001");
        testTestcase("testcase002");
    }

    @Test
    void test2(){
        testTestcase("testcase003");
    }

    @Test
    void test3(){
        testTestcase("testcase004");
    }

    @Test
    void test4(){
        testTestcase("testcase005");
        testTestcase("testcase006");
    }

    @Test
    void test5(){
        testTestcase("testcase007");
    }

    void testTestcase(String testCase){
        List<String> testCaseStrings = TestUtils.getTestCase(testCase,TESTCASE_FILENAME, Optional.of(4));
        testCaseStrings.forEach(System.out::println);
        assertEquals(4,testCaseStrings.size());
        var info = Arrays.asList(testCaseStrings.get(0).split(","));
        if (!info.get(2).equals("0")){
            Node testNode = GPUtils.createNodeFromString(testCaseStrings.get(1));
            List<Node> subNode = createNodesFromStrings(testCaseStrings, 2);
            List<Node> expNode = createNodesFromStrings(testCaseStrings, 3);
            for (int i = 0; i < expNode.size(); i++){
                Node actNode = testNode.clone();
                ((FunctionNode)actNode).replaceSubtreeAt(Integer.valueOf(info.get(2)),subNode.get(i));
                TestUtils.assertNode(expNode.get(i),actNode);
            }
        }else{
            System.out.println("Node can not change index 0");
        }
      }

    private List<Node> createNodesFromStrings(List<String> testCaseStrings, int i) {
        return Arrays.asList(testCaseStrings.get(i)
                .split(","))
                .stream()
                .map(GPUtils::createNodeFromString)
                .collect(Collectors.toList());
    }
}
