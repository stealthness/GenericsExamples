package ga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Stephen West on 22/03/2019.
 */
class IndividualTest {//NOPMD

    private static final int CHROMOSOME_SIZE = 10;
    private Individual emptyIndividual;
    private int[] emptyChromosome;

    /**
     * set up
     */
    @BeforeEach
    void setUp(){
        emptyChromosome = new int[]{0,0,0,0,0,0,0,0,0,0};
        emptyIndividual = new Individual(new int[]{0,0,0,0,0,0,0,0,0,0});
    }

    @Test
    void testCreate(){
        assertEquals(Individual.class,emptyIndividual.getClass(),"is not an Individual class");

    }

    @Test
    void testSize(){
        assertEquals(CHROMOSOME_SIZE,emptyIndividual.size(),"size is not 10");
    }

    @Test
    void testGetEmptyChromosome(){
        assertEqualIndividuals(emptyChromosome,emptyIndividual);
    }

    private void assertEqualIndividuals(int[] actChromosome,Individual expIndividual){
        assertTrue(IntStream.range(0,actChromosome.length).allMatch(gene ->
                actChromosome[gene] == expIndividual.getGene(gene)));
    }

}
