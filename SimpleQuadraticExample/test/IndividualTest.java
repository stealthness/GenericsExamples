import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class IndividualTest {

    private static final Double TOL = 0.000001;
    private static final int MAX_RUNS = 100;
    private static final String FULL = "full";
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
        assertEquals("(1.0)",individual.print());
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


    @Test
    void testIndividualJustTerminalNodes(){
        var testList = Arrays.asList(TestUtils.oneNode,TestUtils.twoNode,TestUtils.threeNode,TestUtils.eNode,TestUtils.xNode);
        testIndividualFunctionNodes(1,0,testList);
    }

    @Test
    void testIndividualJustGPBiFunctionNodes(){
        var testList = Arrays.asList(TestUtils.addNode,TestUtils.multiplyNode, TestUtils.xPlusOne,TestUtils.xPlusTwo,
                TestUtils.onePlusX,TestUtils.twoPlusX);
        testIndividualFunctionNodes(3,1,testList);
    }

    @Test
    void testIndividualJustGPMultiFunctionNodes(){
        var testList = Arrays.asList(TestUtils.addOneTwoThree);
        var expCalculation = Arrays.asList(6.0);
        individual = Individual.builder().root(testList.get(0)).build();

        Double[] inputs = new Double[]{0.0,1.0,-2.0};
        assertIndividualCalculation(6.0, inputs,individual);

        testIndividualFunctionNodes(4,1,testList);
        testIndividualCalculations(expCalculation,inputs,testList);
    }


    @Test
    void testGetNodeAt0(){
        List<Node> testList = Arrays.asList(TestUtils.onePlusX);
        List<Node> expNode = Arrays.asList(TestUtils.oneNode,TestUtils.xNode);

        individual = Individual.builder().root(testList.get(0)).build();
        TestUtils.assertNode(testList.get(0),individual.getRoot());
        assertEquals(testList.get(0).size(),individual.size(),"Individual's Size not equal to root Node's size");
        System.out.println(individual.print());
        IntStream.range(1,individual.size()).forEach(i->{
            System.out.println(i);
            TestUtils.assertNode(expNode.get(i-1),individual.getSubtree(i).get());
        });

    }


    // private helper method

    private void testIndividualFunctionNodes(int expSize, int expDepth, List<Node> testList){
        testList.stream().forEach(node -> {
            individual = Individual.builder()
                    .root(node)
                    .build();
            assertIndividualSize(expSize,expDepth,individual);
        });
    }

    private void testIndividualCalculations(List<Double> expCalculations, Double[] inputs, List<Node> testList){
        IntStream.range(0,testList.size()).forEach(i -> {
            individual = Individual.builder()
                    .root(testList.get(i))
                    .build();
            assertIndividualCalculation(expCalculations.get(i),inputs,individual);
        });
    }


    private void assertIndividualCalculation(Double expCalculation, Double[] inputs,Individual individual){
        assertEquals(expCalculation, individual.calculate(inputs),String.format("individual : %s", individual.print()));
    }

    private void assertIndividualSize(int expSize, int expDepth, Individual individual){
        assertEquals(expSize, individual.size(),String.format("individual : %s", individual.print()));
        assertEquals(expDepth, individual.maxDepth(),String.format("individual : %s", individual.print()));
    }
}