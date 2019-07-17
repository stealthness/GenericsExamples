import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class NodeSetANdGetTest {

    private final boolean isLogging = true;

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
        assertEquals(4,testCaseStrings.size());
        if (isLogging) {
            testCaseStrings.stream().forEach(string -> System.out.print(string + ", "));
            System.out.println();
        }
        var info = Arrays.asList(testCaseStrings.get(0).split(","));
        if (!info.get(2).equals("0")){
            Node testNode = NodeUtils.createNodeFromString(testCaseStrings.get(1));
            List<Node> subNode = createNodesFromStrings(testCaseStrings, 2);
            List<Node> expNode = createNodesFromStrings(testCaseStrings, 3);
            if (isLogging){
                subNode.stream().forEach(node -> System.out.print(node.toClojureString() + " "));
                System.out.println();
                expNode.stream().forEach(node -> System.out.println(node.toClojureString() + " "));
                System.out.println();
            }
            for (int i = 0; i < expNode.size(); i++){
                Node actNode = testNode.clone();
                System.out.println(String.format("subNode %s",subNode.toString()));
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
                .map(NodeUtils::createNodeFromString)
                .collect(Collectors.toList());
    }
}
