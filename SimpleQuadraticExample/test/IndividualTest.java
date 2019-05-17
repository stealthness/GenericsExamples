import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class IndividualTest {

    private static final double TOL = 0.000001;
    private static final int MAX_RUNS = 100;
    private Individual individual;

    @BeforeEach
    void setUP(){
        individual = Individual.generate();
        individual.setRoot(new FunctionNode(GPUtils.add,"+", new VariableNode(0),new TerminalNode(1.0)));
    }

    @Test
    void print() {
        assertEquals("(+ x0 1.0)", individual.print()) ;
    }

    @Test
    void testGenerate() {
        int maxDepth = 2;
        int maxTreeSize = 6;
        IntStream.range(0, MAX_RUNS).forEach(i -> {
            individual = Individual.generate();
            String errMsg = individual.print();
            System.out.println(errMsg);
            assertTrue(maxDepth >= individual.getMaxDepth(),errMsg);
            assertTrue(maxTreeSize >= individual.size(),errMsg);

        });
    }

    @Test
    void getRoot() {
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
    void testEvaluation(){
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

        Node subtree = new FunctionNode(GPUtils.multiply, "*", new VariableNode(0),new VariableNode(0)) ;
        individual.setRoot(new FunctionNode(GPUtils.add,"+", new TerminalNode(1.0),subtree));
        individual.evaluate();
        assertEquals(1.0,individual.get(new double[]{0.0}),TOL);
        assertEquals(2.0,individual.get(new double[]{1.0}),TOL);
        assertEquals(2.0,individual.get(new double[]{-1.0}),TOL);
        assertEquals(11.0, individual.getFitness(),TOL);
    }

    @Test
    void testChangeRootNodeOnIndividual(){

        individual.setRoot(new FunctionNode(GPUtils.add,"+", new VariableNode(0),new TerminalNode(1.0)));
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
        IntStream.range(0,MAX_RUNS).forEach(i ->{
            nodes.add(individual.generatingTerminal());
        });
        int count = (int)nodes.stream().filter(n -> n.getClass()==TerminalNode.class).count();
        assertTrue(Math.abs(MAX_RUNS/2 - count) < sd, MAX_RUNS + " " +count + " " + sd);
    }
}