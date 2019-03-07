import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Stephen West on 07/03/2019.
 */
class IndividualTest {

    int[] emptyChromosome,evenChromosome,completeChromosome;
    Individual emptyIndividual,evenIndividual,completeIndividual;

    @BeforeEach
    void setUp(){
        emptyChromosome = new int[]{0,0,0,0,0,0,0,0};
        evenChromosome = new int[]{1,1,1,0,0,0,0,1};
        completeChromosome = new int[]{1,1,1,1,1,1,1,1};
        emptyIndividual = new Individual(emptyChromosome);
        evenIndividual = new Individual(evenChromosome);
        completeIndividual = new Individual(completeChromosome);
    }

    @Test
    void testCreate(){
        assertEquals(Individual.class,emptyIndividual.getClass());
    }

}