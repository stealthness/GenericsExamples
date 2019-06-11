import org.junit.jupiter.api.BeforeEach;
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
    private Individual individual;

    @BeforeEach
    void setUP(){

    }

    @Test
    void testCreateIndividualWitTerminalNode(){
        individual = Individual.builder().root(TestUtils.oneNode).build();
        assertEquals(TerminalNode.class, individual.getRoot().getClass());
        assertEquals(1.0, individual.calculate(TestUtils.createRandomInput(1)));

        individual = Individual.builder().root(new TerminalNode(1.0)).build();
        assertEquals(TerminalNode.class, individual.getRoot().getClass());
        assertEquals(1.0, individual.calculate(TestUtils.createRandomInput(1)));
        assertEquals("(1.0)",individual.toClojureString());
    }


    @Test
    void testCreateIndividualWitVariableNode(){
        individual = Individual.builder().root(TestUtils.xNode).build();
        assertEquals(VariableNode.class, individual.getRoot().getClass());
        Double[] input= TestUtils.createRandomInput(1);
        assertEquals(input[0], individual.calculate(input));
    }

    @Test
    void testCreateIndividualWitEphemeralNode(){
        individual = Individual.generate(Arrays.asList(TestUtils.eNode),null,"grow",0);
        assertEquals(TerminalNode.class, individual.getRoot().getClass());
        Double[] input= TestUtils.createRandomInput(1);
        Double[] expRange = TestUtils.range1to1;
        assertTrue(individual.calculate(input) <= expRange[1], String.format("%s < %f is false",individual.calculate(input),expRange[1]));
        assertTrue(individual.calculate(input) >= expRange[0], String.format("%s > %f is false",individual.calculate(input),expRange[0]));
    }

    // Test get SubNodes

    @Test
    void test1(){
        testGetNode("testcase001");
        testGetNode("testcase002");
    }

    @Test
    void testSingle(){
        testGetNode("single004");
        testGetNode("single005");
        testGetNode("single006");
        testGetNode("single003");
    }

    @Test
    void testMulti(){
        testGetNode("multi002");
        testGetNode("multi001");
    }

    @Test
    void testIndividualCalculation(){
        testIndividualCalculation("testcase001");
        testIndividualCalculation("testcase002");
        testIndividualCalculation("testcase003");
        testIndividualCalculation("testcase004");
    }

    @Test
    void testReplaceNodeAt(){
        testTestcase("testcase001");
        testTestcase("testcase002");
        testTestcase("testcase003");
        testTestcase("testcase004");
        testTestcase("testcase005");
        testTestcase("testcase006");
        testTestcase("testcase007");
    }

    // private assert Methods

    private void assertIndividualCalculation(Double expCalculation, Double[] inputs,Individual individual){
        assertEquals(expCalculation, individual.calculate(inputs),String.format("individual : %s", individual.toClojureString()));
    }

    // test method using testcase

    public void testIndividualCalculation(String testcase) {
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
        assertEquals(3,testCaseStrings.size());
        var info = Arrays.asList(testCaseStrings.get(0).split(","));

        Node root = NodeUtils.createNodeFromString(info.get(1));
        individual = Individual.builder().root(root).build();
        if (info.get(2).equals("all")){
            for (int i = 1; i< individual.size();i++){
                TestUtils.assertNode(NodeUtils.createNodeFromString(info.get(2).split(",")[i]),individual.getSubtree(i).get());
            }
        } else{
            for (int i = 2; i< info.get(2).length();i++){
                TestUtils.assertNode(NodeUtils.createNodeFromString(info.get(2).split(",")[i]),individual.getSubtree(i).get());
            }
        }
        TestUtils.assertNode(root,individual.getSubtree(0).get());
        // any index equal or greater should return empty
        assertTrue(individual.getSubtree(individual.size()).isEmpty());
    }

    void testTestcase(String testCase){
        List<String> testCaseStrings = TestUtils.getTestCase(testCase,TESTCASE_FILENAME, Optional.of(4));
        testCaseStrings.forEach(System.out::println);
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

    private List<Node> createNodesFromStrings(List<String> testCaseStrings, int i) {
        return Arrays.asList(testCaseStrings.get(i)
                .split(","))
                .stream()
                .map(NodeUtils::createNodeFromString)
                .collect(Collectors.toList());
    }

//    private double[] createRandomInputs(int size, int lowerBound, int upperBound) {
//        double[] inputs= new double[size];
//        for (int i = 0; i< size;i++){
//            inputs[i] = new Random().nextDouble()*(lowerBound+upperBound)-lowerBound;
//        }
//        return inputs;
//    }
}