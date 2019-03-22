package ga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

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
        assertTrue(IntStream.range(0,emptyChromosome.length).allMatch(gene ->
                0 == emptyIndividual.getGene(gene)),
                "act: 0000000000 != exp: "+emptyIndividual.toString());
    }

    @Test
    void testToStringMethod(){
        assertTrue("0000000000".equals(emptyIndividual.toString()));
    }

    private void assertEqualIndividuals(Individual actIndividual,Individual expIndividual){

    }

}
