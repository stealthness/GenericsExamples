import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.DoubleStream;

import static org.junit.jupiter.api.Assertions.*;

class IndividualTest {

    private static final double TOL = 0.000001;
    private Individual individual;

    @BeforeEach
    void setUP(){
        individual = Individual.generate();
    }

    @Test
    void print() {
        assertEquals("(+ x0 1.0)", individual.print()) ;
    }

    @Test
    void generate() {
        // to do
        fail();
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
        assertEquals(1.0,individual.get(new double[]{0.0}),TOL);
        assertEquals(2.0,individual.get(new double[]{1.0}),TOL);
        assertEquals(0.0,individual.get(new double[]{-1.0}),TOL);
    }



    @Test
    void getFitness() {

        individual.evaluate();

        assertEquals(7.7, individual.getFitness(),TOL);
    }
}