import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class IndividualTest {
//
//    private static final Double TOL = 0.000001;
//    private static final int MAX_RUNS = 100;
//    private static final String FULL = "full";
    private static final String INDIVIDUAL_CALCULATION_FILEPATH = "testcases//individualCalculationTestCases.txt";
    private static final String GET_SUBTREE_FILEPATH = "testcases//testGetSubtreeAt.txt";
    private static final String TESTCASE_FILENAME = "testcases//nodeReplaceTreeAt.txt";
    private static final String GET_FITNESS_FILENAME = "testcases//individualGetFitnessTestcases.txt";
    private static final double TOL = 0.0001;
    private Individual individual;



    // Test get SubNodes GET_SUBTREE_FILEPATH = "testcases//testGetSubtreeAt.txt";

    @Test
    void testGetNode1(){
        String[] tests = new String[]{"testcase001","testcase002"};
        Arrays.stream(tests).forEach(this::testGetNode);
    }

    @Test
    void testSingle(){

        String[] tests = new String[]{"single003","single004","single005","single006"};
        Arrays.stream(tests).forEach(this::testGetNode);
    }

    @Test
    void testMulti(){
        testGetNode("multi002");
        testGetNode("multi001");
    }

    @Test
    void testIndividualCalculation(){
        String[] tests = new String[]{"testcase001","testcase002","testcase003","testcase004"};
        Arrays.stream(tests).forEach(this::testIndividualCalculation);
    }

    @Test
    void testReplaceNodeAt(){
        String[] tests = new String[]{"testcase001","testcase002","testcase003","testcase004","testcase005","testcase006","testcase007"};
        Arrays.stream(tests).forEach(this::testReplaceSubNodeAt);
    }

    // GET_FITNESS_FILENAME = "testcases//individualCalculationTestCases.txt";

    @Test
    void testEvaluateDepth0(){
        String[] tests = new String[]{"testcase001","testcase002"};
        Arrays.stream(tests).forEach(this::testEvaluate);
    }

    @Test
    void testEvaluateDepth1(){
        String[] tests = new String[]{"testcase003","testcase004"};
        Arrays.stream(tests).forEach(this::testEvaluate);
    }

    // private assert Methods

    private void assertIndividualCalculation(Double expCalculation, Double[] inputs,Individual individual){
        assertEquals(expCalculation, individual.calculate(inputs),String.format("individual : %s", individual.toClojureString()));
    }

    // test method using testcase

    public void testIndividualCalculation(String testcase) {
        System.out.println(testcase);
        List<String> strings = TestUtils.getTestCase(testcase, INDIVIDUAL_CALCULATION_FILEPATH, Optional.of(4));
        individual = Individual.builder().root(NodeUtils.createNodeFromString(strings.get(1))).build();
        List<String> inputsStrings;
        double[] expCalculation;
        inputsStrings = Arrays.asList(strings.get(2).split(","));
        expCalculation = (Arrays.asList(strings.get(3)
                .split(","))).stream()
                .mapToDouble(s -> Double.valueOf(s))
                .toArray();

        for (int i = 0; i < expCalculation.length; i++) {
            assertIndividualCalculation(expCalculation[i],new Double[]{Double.valueOf(inputsStrings.get(i))},individual);
            assertEquals(expCalculation[i], individual.calculate(new Double[]{Double.valueOf(inputsStrings.get(i))}), String.format("individual : %s", individual.toClojureString()));
        }
    }

    public void testGetNode(String testcase) {
        List<String> testCaseStrings = TestUtils.getTestCase(testcase, GET_SUBTREE_FILEPATH, Optional.of(3));;
        assertEquals(3,testCaseStrings.size(),testcase);
        var info = Arrays.asList(testCaseStrings.get(0).split(","));

        Node root = NodeUtils.createNodeFromString(info.get(1));
        individual = Individual.builder().root(root).build();
        if (info.get(2).equals("all")){
            for (int i = 1; i< individual.size();i++){
                TestUtils.assertNode(NodeUtils.createNodeFromString(info.get(2).split(",")[i]),individual.getSubtree(i).get(),testcase);
            }
        } else{
            for (int i = 2; i< info.get(2).length();i++){
                TestUtils.assertNode(NodeUtils.createNodeFromString(info.get(2).split(",")[i]),individual.getSubtree(i).get(),testcase);
            }
        }
        TestUtils.assertNode(root,individual.getSubtree(0).get());
        // any index equal or greater should return empty
        assertTrue(individual.getSubtree(individual.size()).isEmpty());
    }

    void testReplaceSubNodeAt(String testCase){
        List<String> testCaseStrings = TestUtils.getTestCase(testCase,TESTCASE_FILENAME, Optional.of(4));
        assertEquals(4,testCaseStrings.size());
        var info = Arrays.asList(testCaseStrings.get(0).split(","));
            individual = Individual.builder().root(NodeUtils.createNodeFromString(testCaseStrings.get(1))).build();
            List<Node> subNode = createNodesFromStrings(testCaseStrings, 2);
            List<Node> expNode = createNodesFromStrings(testCaseStrings, 3);
            for (int i = 0; i < expNode.size(); i++){
                Individual actIndividual =  Individual.builder().root(individual.clone()).build();
                actIndividual.replaceSubtreeAt(Integer.valueOf(info.get(2)),subNode.get(i));
                TestUtils.assertNode(expNode.get(i),actIndividual.getRoot());
            }
    }

    void testEvaluate(String testCase){
        System.out.println("<1>"+testCase);
        List<String> testCaseStrings = TestUtils.getTestCase(testCase,GET_FITNESS_FILENAME, Optional.of(4));
        String msg = testCase;
        assertEquals(4,testCaseStrings.size(),msg + " - Testcase invalid");
        Node expSolutionNode = NodeUtils.createNodeFromString(testCaseStrings.get(1));

        List<Node> testExpression = createNodesFromStrings(testCaseStrings, 2);
        List<Double> expValue = getExpFitnessValues(testCaseStrings);
        for (int i = 0; i < expValue.size(); i++){
            var nodes = Arrays.asList(expSolutionNode,testExpression.get(i));
            double[] testRange = getTestRange(testCaseStrings.get(0));
            assertEquals(expValue.get(i),GPUtils.evaluateFitness(nodes,testRange),TOL,msg+" - input test:" + i);
        }
    }

    private List<Double> getExpFitnessValues(List<String> testCaseStrings) {
        return Arrays.stream(testCaseStrings.get(3).split(","))
                .map(Double::valueOf)
                .collect(Collectors.toList());
    }

    private double[] getTestRange(String string) {
        List<String> info = Arrays.asList(string.split(","));
        return new double[]{Double.valueOf(info.get(2)),Double.valueOf(info.get(3)),Double.valueOf(info.get(4))};
    }

    private List<Node> createNodesFromStrings(List<String> testCaseStrings, int i) {
        return Arrays.asList(testCaseStrings.get(i)
                .split(","))
                .stream()
                .map(NodeUtils::createNodeFromString)
                .collect(Collectors.toList());
    }

}