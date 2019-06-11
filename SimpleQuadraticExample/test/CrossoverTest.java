import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CrossoverTest {

    private static final String TESTCASE_FILENAME =  "D:\\WS\\Java\\GeneticsAlgorithmsExamples\\SimpleQuadraticExample\\testcases\\crossoverTestCases.txt";
    List<String> expChildrenList;
    List<String> parentList;

    @Test
    void testCrossoverOnSimpleTrees(){
        Node parentNode0 = TestUtils.onePlusX;
        Node parentNode1 = TestUtils.xNode;

        Node parentNode2= TestUtils.xPlusTwo;
        Node parentNode3 = TestUtils.oneNode;

        Node child0 = GPUtils.mutateIndex1.apply(Arrays.asList(parentNode0,parentNode1),1.0).clone();
        Node child1 = GPUtils.mutateIndex1.apply(Arrays.asList(parentNode2,parentNode3),1.0).clone();

        TestUtils.assertNode(TestUtils.xPlusX,child0);
        TestUtils.assertNode(TestUtils.onePlusTwo,child1);
    }


    @Test
    void testSimpleCrossoverATIndex1(){
        parentList = Arrays.asList("(+ 1.0 2.0)", "(- x0 1.0)");

        expChildrenList = Arrays.asList("(+ x0 2.0)", "(- 1.0 1.0)");
        testCrossOverAt(expChildrenList,parentList,new Integer[]{1,1},GPUtils.crossoverAt);

        expChildrenList = Arrays.asList("(+ 1.0 1.0))", "(- x0 2.0)");
        testCrossOverAt(expChildrenList,parentList,new Integer[]{2,2},GPUtils.crossoverAt);

        expChildrenList = Arrays.asList("(+ 1.0 2.0)", "(- x0 1.0)");
        testCrossOverAt(expChildrenList,parentList,new Integer[]{1,2},GPUtils.crossoverAt);

        expChildrenList = Arrays.asList("(+ 1.0 x0)", "(- 2.0 1.0)");
        testCrossOverAt(expChildrenList,parentList,new Integer[]{2,1},GPUtils.crossoverAt);
    }
    @Test
    void testSimpleCrossoverATIndex2(){
        parentList = Arrays.asList("(* 1.0 (* x0 2.0))", "(+ x0 3.0");
        expChildrenList = Arrays.asList("(* x0 (* x0 2.0))", "(+ 1.0 3.0");
        testCrossOverAt(expChildrenList,parentList,new Integer[]{1,1}, GPUtils.crossoverAt);

        expChildrenList = Arrays.asList("(* 1.0 3.0", "(+ x0 (* x0 2.0))");
        testCrossOverAt(expChildrenList,parentList,new Integer[]{2,2}, GPUtils.crossoverAt);

        expChildrenList = Arrays.asList("(* 1.0 (* x0 2.0))", "(+ x0 3.0");
        testCrossOverAt(expChildrenList,parentList,new Integer[]{3,1}, GPUtils.crossoverAt);

        expChildrenList = Arrays.asList("(* 1.0 (* 3.0 2.0))", "(+ x0 x0");
        testCrossOverAt(expChildrenList,parentList,new Integer[]{3,2}, GPUtils.crossoverAt);
    }

    @Test
    void testSimpleCrossoverATIndex3(){
        List<String> parentList = Arrays.asList("(+ 1.0 (* x0 2.0)", "(- (+ 2.0 (+ x0 1.0)) 1.0)");
        List<String> expChildrenList = Arrays.asList( "(+ 1.0 (* (+ x0 1.0) 2.0)","(- (+ 2.0 x0) 1.0)");
        Integer[] indexes = new Integer[]{3,3};
        testCrossOverAt(expChildrenList,parentList,indexes,GPUtils.crossoverAt);
    }

    private void testCrossOverAt(List<String> expChildrenList, List<String> parentList, Integer[] indexes, BiFunction<List<String>,Integer[], List<Node>> function){
        for (int i = 0; i<parentList.size(); i =+2){
            List<Node> children = function.apply(parentList, indexes);
            TestUtils.assertNode(GPUtils.createNodeFromString(expChildrenList.get(i)),children.get(0));
            TestUtils.assertNode(GPUtils.createNodeFromString(expChildrenList.get(i+1)),children.get(1));
        }
    }

    @Test
    void testGetTestCase(){
        List<String> testCase = getTestCase("testCase001");
        assertEquals(2,testCase.size());
        parentList = Arrays.asList(testCase.get(0).split(","));
        expChildrenList = Arrays.asList(testCase.get(1).split(","));
        testCrossOverAt(expChildrenList,parentList,new Integer[]{1,1},GPUtils.crossoverAt);
    }


    List<String> getTestCase(String testcase){
        try {
            Files.lines((Path.of(TESTCASE_FILENAME))).forEach(System.out::println);

            String[] testInfo = Files.lines(Path.of(TESTCASE_FILENAME))
                    .filter(line -> line.startsWith(testcase))
                    .findFirst()
                    .get()
                    .split(",");
            int testStart = Integer.valueOf(testInfo[1]);
            return Files.lines(Path.of(TESTCASE_FILENAME))
                    .skip(testStart)
                    .limit(2)
                    .collect(Collectors.toList());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
