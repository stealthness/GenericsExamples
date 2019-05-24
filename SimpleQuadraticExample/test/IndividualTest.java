import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IndividualTest {

    private static final double TOL = 0.000001;
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
        assertEquals(1.0, individual.apply(TestUtils.createRandomInput(1)));
    }


    @Test
    void testCreateIndividualWitVariableNode(){
        individual = Individual.builder().root(TestUtils.xNode).build();
        assertEquals(VariableNode.class, individual.getRoot().getClass());
        double[] input= TestUtils.createRandomInput(1);
        assertEquals(input[0], individual.apply(input));
    }

    @Test
    void testCreateIndividualWitEphemeralNode(){
        individual = Individual.builder().root(TestUtils.eNode.clone()).build();
        assertEquals(TerminalNode.class, individual.getRoot().getClass());
        double[] input= TestUtils.createRandomInput(1);
        double[] expRange = TestUtils.range1to1;
        assertTrue(Double.valueOf(individual.apply(input)) <= expRange[1], String.format("%s < %f is false",individual.apply(input),expRange[1]));
        assertTrue(Double.valueOf(individual.apply(input)) >= expRange[0], String.format("%s > %f is false",individual.apply(input),expRange[0]));
    }
}