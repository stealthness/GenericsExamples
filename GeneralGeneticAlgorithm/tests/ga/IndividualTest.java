package ga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Stephen West on 22/03/2019.
 */
class IndividualTest {

    private Individual emptyIndividual;

    @BeforeEach
    void setUp(){
        emptyIndividual = new Individual(new int[]{0,0,0,0,0,0,0,0,0,0});
    }

    @Test
    void testCreate(){
        assertEquals(Individual.class,emptyIndividual.getClass());
    }
}
