import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        individual = Individual.builder().root(TestUtils.eNode.clone()).build();
        assertEquals(TerminalNode.class, individual.getRoot().getClass());
        Double[] input= TestUtils.createRandomInput(1);
        Double[] expRange = TestUtils.range1to1;
        assertTrue(Double.valueOf(individual.calculate(input)) <= expRange[1], String.format("%s < %f is false",individual.calculate(input),expRange[1]));
        assertTrue(Double.valueOf(individual.calculate(input)) >= expRange[0], String.format("%s > %f is false",individual.calculate(input),expRange[0]));
    }


    @Test
    void testIndividualJustTerminalNodes(){
        individual = Individual.builder()
                .root(TestUtils.oneNode)
                .build();
        assertIndividualSize(1,0,individual);
        individual = Individual.builder()
                .root(TestUtils.xNode)
                .build();
        assertIndividualSize(1,0,individual);
    }

    @Test
    void testIndividualJustGPBiFunctionNodes(){
        individual = Individual.builder()
                .root(TestUtils.addNode)
                .build();
        assertIndividualSize(3,1,individual);
        individual = Individual.builder()
                .root(TestUtils.multiplyNode)
                .build();
        assertIndividualSize(3,1,individual);
    }



    private void assertIndividualSize(int expSize, int expDepth, Individual individual){
        assertEquals(expSize, individual.size(),String.format("individual : %s", individual.print()));
        assertEquals(expDepth, individual.maxDepth(),String.format("individual : %s", individual.print()));
    }
}