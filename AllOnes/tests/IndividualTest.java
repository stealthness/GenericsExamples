import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Stephen West on 07/03/2019.
 */
class IndividualTest {

    private static final int CHROMOSOME_LENGTH = 8;
    private int[] emptyChromosome,evenChromosome,completeChromosome;
    private Individual emptyIndividual,evenIndividual,completeIndividual;

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

    @Test
    void testGetEmptyChromosome(){
        assertEquals(emptyChromosome,emptyIndividual.getChromosome());
    }

    @Test
    void testGetEvenChromosome(){
        assertEquals(evenChromosome,evenIndividual.getChromosome());
    }

    @Test
    void testGetCompleteChromosome(){
        assertEquals(completeChromosome,completeIndividual.getChromosome());
    }

    @Test
    void testGetGeneFromEmptyIndividual(){
        IntStream.range(0,CHROMOSOME_LENGTH).forEach(gene -> assertEquals(0,emptyIndividual.getGene(gene)));
    }

    @Test
    void testGetGeneFromCompleteIndividual(){
        IntStream.range(0,CHROMOSOME_LENGTH).forEach(gene -> assertEquals(1,completeIndividual.getGene(gene)));
    }

    @Test
    void testGetGeneFromEvenIndividual(){
        IntStream.range(0,CHROMOSOME_LENGTH).forEach(gene -> assertEquals(evenChromosome[gene],evenIndividual.getGene(gene)));
    }

    @Test
    void testSize(){
        assertEquals(CHROMOSOME_LENGTH,emptyIndividual.size());
    }

    @Test
    void testSetGeneToZero(){
        IntStream.range(0,CHROMOSOME_LENGTH).forEach(gene -> {
            completeIndividual.setGene(gene,0);
            assertEquals(emptyChromosome[gene],completeIndividual.getGene(gene));
        });
    }

    @Test
    void testSetGeneToOne(){
        IntStream.range(0,CHROMOSOME_LENGTH).forEach(gene -> {
            emptyIndividual.setGene(gene,1);
            assertEquals(completeChromosome[gene],emptyIndividual.getGene(gene));
        });
    }

    @Test
    void testCreateRandomChromosome(){
        Individual individual = new Individual(CHROMOSOME_LENGTH);
        assertTrue(Arrays.stream(individual.getChromosome()).allMatch(gene -> gene == 0 || gene == 1));
    }

    @Test
    void testToStrongEmptyIndividual(){
        assertEquals("00000000",emptyIndividual.toString());
    }

    @Test
    void testToStrongCompleteIndividual(){
        assertEquals("11111111",completeIndividual.toString());
    }

    @Test
    void testToStrongEvenIndividual(){
        assertEquals("11100001",evenIndividual.toString());
    }

    @Test
    void testGetFitnessForEmptyIndividual(){
        emptyIndividual.evaluateFitness();
        assertEquals(0,emptyIndividual.getFitness());
    }

    @Test
    void testGetFitnessForEvenIndividual(){
        evenIndividual.evaluateFitness();
        assertEquals(0,evenIndividual.getFitness());
    }

    @Test
    void testGetFitnessForCompleteIndividual(){
        completeIndividual.evaluateFitness();
        assertEquals(0,completeIndividual.getFitness());
    }
}