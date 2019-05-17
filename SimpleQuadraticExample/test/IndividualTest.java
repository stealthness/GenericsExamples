import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class IndividualTest {

    private static final double TOL = 0.000001;
    private static final int MAX_RUNS = 100;
    private Individual individual;

    Node xTree;
    Node oneTree;
    Node twoTree;
    Node xPlus1Tree;
    Node xSqrdTree;
    Node xSqrdPlus1Tree;
    Node xSqrdPlus1TwiceTree;

    @BeforeEach
    void setUP(){
        individual = Individual.generate();
        individual.setRoot(new FunctionNode(GPUtils.add, new VariableNode(0),new TerminalNode(1.0)));
        // Terminal Nodes
        oneTree = new TerminalNode(1.0);
        twoTree = new TerminalNode(2.0);
        xTree = new VariableNode(0);
        // Function Nodes
        xPlus1Tree = new FunctionNode(GPUtils.add, xTree, oneTree);
        xSqrdTree = new FunctionNode(GPUtils.multiply, xTree, xTree);
        xSqrdPlus1Tree = new FunctionNode(GPUtils.add, xSqrdTree, oneTree);
        xSqrdPlus1TwiceTree = new FunctionNode(GPUtils.multiply, twoTree, xSqrdPlus1Tree);
    }

    @Test
    void print() {
        assertEquals("(+ x0 1.0)", individual.print()) ;
    }


    @Test
    void getRootOnIndividualWithXPlus1() {
        Node root = individual.getRoot();
        assertEquals("(+ x0 1.0)",root.print());
        if (root.getClass() == FunctionNode.class){
            Node node1 = ((FunctionNode)root).getSubtree(0);
            Node node2 = ((FunctionNode)root).getSubtree(1);

            assertEquals("x0",node1.print());
            assertEquals("1.0",node2.print());
        }

    }

    @Test
    void getFitnessOnExpressXEqual2() {
        individual.setRoot(new TerminalNode(2.0));
        individual.evaluate();
        assertEquals(2.0,individual.get(new double[]{0.0}),TOL);
        assertEquals(2.0,individual.get(new double[]{1.0}),TOL);
        assertEquals(2.0,individual.get(new double[]{-1.0}),TOL);
        assertEquals(17.98, individual.getFitness(),TOL);
    }

    @Test
    void getFitnessOnExpressXEqualXSquaredPlus1() {

        Node subtree = new FunctionNode(GPUtils.multiply, new VariableNode(0),new VariableNode(0)) ;
        individual.setRoot(new FunctionNode(GPUtils.add,new TerminalNode(1.0),subtree));
        individual.evaluate();
        assertEquals(1.0,individual.get(new double[]{0.0}),TOL);
        assertEquals(2.0,individual.get(new double[]{1.0}),TOL);
        assertEquals(2.0,individual.get(new double[]{-1.0}),TOL);
        assertEquals(11.0, individual.getFitness(),TOL);
    }

    @Test
    void testChangeRootNodeOnIndividual(){

        individual.setRoot(new FunctionNode(GPUtils.add, new VariableNode(0),new TerminalNode(1.0)));
        individual.evaluate();

        assertEquals(1.0,individual.get(new double[]{0.0}),TOL);
        assertEquals(2.0,individual.get(new double[]{1.0}),TOL);
        assertEquals(0.0,individual.get(new double[]{-1.0}),TOL);

        assertEquals(7.7, individual.getFitness(),TOL);
    }

    // test generating nodes

    @Test
    void testGeneratingTerminal(){

        // stand deviation
        double sd = Math.sqrt(MAX_RUNS/4);

        List<Node> nodes = new ArrayList<>();
        IntStream.range(0,MAX_RUNS).forEach(i -> nodes.add(individual.generatingTerminal()));
        int count = (int)nodes.stream().filter(n -> n.getClass()==TerminalNode.class).count();
        // test that number Terminal Nodes is within 3 standard deviation
        assertTrue(Math.abs(MAX_RUNS/2 - count) < 3*sd, MAX_RUNS + " " +count + " " + 3*sd);
    }

    @Test
    void testGenerateFunctionOfDepth1(){
        List<Node> nodes = new ArrayList<>();
        IntStream.range(0,MAX_RUNS).forEach(i -> nodes.add(individual.generatingFunction(1)));
        assertTrue(nodes.stream().allMatch(node ->{
            return 1 == node.getDepth();
        }));
        assertTrue(nodes.stream().allMatch(node ->{
            return 3 == node.size();
        }));
    }

    @Test
    void testGenerateFunctionOfDepth2(){
        List<Node> nodes = new ArrayList<>();
        IntStream.range(0,MAX_RUNS).forEach(i -> nodes.add(individual.generatingFunction(1)));
    }


    @Test
    void testGenerate() {
        int maxDepth = 2;
        int maxTreeSize = 6;
        IntStream.range(0, MAX_RUNS).forEach(i -> {
            individual = Individual.generate();
            String errMsg = individual.print();
            // to remove later
            System.out.println(errMsg);
            assertTrue(maxDepth >= individual.getMaxDepth(),errMsg);
            assertTrue(maxTreeSize >= individual.size(),errMsg);
        });
    }


    // Test getDepth need for generating tree up N depth

    @Test
    void testDepthOf0(){
        individual.setRoot(xTree);
        assertEquals(0,individual.getDepth());
        individual.setRoot(oneTree);
        assertEquals(0,individual.getDepth());
    }

    @Test
    void testDepthOf1(){
        individual.setRoot(xPlus1Tree);
        assertEquals(1, individual.getDepth());
    }

    @Test
    void testDepthOf2(){
        individual.setRoot(xSqrdPlus1Tree);
        assertEquals(2, individual.getDepth());
    }

    @Test
    void testDepthOf3(){
        individual.setRoot(xSqrdPlus1TwiceTree);
        assertEquals(3, individual.getDepth());
    }

    // Testing getSize, need for checking that generate tree are of correct size

    @Test
    void testIndividualsOfSize1(){
        // Only Terminal trees can be of size 1
        assertIndividualSize(1, oneTree);
        assertIndividualSize(1, xTree);
    }

    @Test
    void testIndividualsOfSize3(){
        assertIndividualSize(3, xPlus1Tree);
        assertIndividualSize(3, xSqrdTree);
    }

    @Test
    void testIndividualsOfSize5(){
        assertIndividualSize(5, xSqrdPlus1Tree);
    }

    @Test
    void testIndividualsOfSize7(){
        assertIndividualSize(7,new FunctionNode(GPUtils.subtract, xPlus1Tree, xPlus1Tree));
    }

    @Test
    void testIndividualsOfSize9(){
        assertIndividualSize(9, new FunctionNode(GPUtils.subtract, xSqrdPlus1Tree, xPlus1Tree));
    }

    void assertIndividualSize(int expResult, Node node){
        individual.setRoot(node);
        assertEquals(expResult, individual.size());
    }

}