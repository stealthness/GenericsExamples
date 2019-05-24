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
    void testCreateTerminalNode(){
        individual = Individual.builder().root(TestUtils.oneNode).build();
        assertEquals(TerminalNode.class, individual.getRoot().getClass());
        assertEquals(1.0, individual.apply(TestUtils.createRandomInput(1)));
    }

}