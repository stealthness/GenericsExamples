import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Stephen West on 07/03/2019.
 */
class IndividualTest {

    private static final int CHROMOSOME_LENGTH = 8;
    private static final double TOL = 0.01;
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

    private void assertEqualIndividuals(Individual actIndividual, Individual expIndividual){
        assertTrue(IntStream.range(0,expIndividual.size())
                .allMatch(i-> actIndividual.getGene(i) == expIndividual.getGene(i)));
    }

    private void assertEqualIndividuals(int[] actChromosome, Individual expIndividual){
        assertTrue(IntStream.range(0,expIndividual.size())
                .allMatch(i-> actChromosome[i] == expIndividual.getGene(i)));
    }

    @Test
    void testCreate(){
        assertEquals(Individual.class,emptyIndividual.getClass());
    }

    @Test
    void testGetEmptyChromosome(){
        assertEqualIndividuals(emptyChromosome,emptyIndividual);
    }

    @Test
    void testGetEmptyChromosomeAfterChangingEmptyIndividual(){
        assertEqualIndividuals(emptyChromosome,emptyIndividual);
        emptyChromosome[1] = 1;
        assertEqualIndividuals(new int[]{0,0,0,0,0,0,0,0,0,0},emptyIndividual);
    }

    @Test
    void testGetEvenChromosome(){
        assertEqualIndividuals(evenChromosome,evenIndividual);
    }

    @Test
    void testGetCompleteChromosome(){
        assertEqualIndividuals(completeChromosome, completeIndividual);
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
        assertEquals(0,emptyIndividual.getFitness(),TOL);
    }

    @Test
    void testGetFitnessForEvenIndividual(){
        evenIndividual.evaluateFitness();
        assertEquals(0.5,evenIndividual.getFitness(),TOL);
    }

    @Test
    void testGetFitnessForCompleteIndividual(){
        completeIndividual.evaluateFitness();
        assertEquals(1.0,completeIndividual.getFitness(),TOL);
    }

    @Test
    void testCompleteIndividualIsGreaterThanEmptyIndividual(){
        completeIndividual.evaluateFitness();
        emptyIndividual.evaluateFitness();
        assertEquals(-1,completeIndividual.compareTo(emptyIndividual));
    }
    @Test
    void testEvenIndividualIsGreaterThanEmptyIndividual(){
        evenIndividual.evaluateFitness();
        emptyIndividual.evaluateFitness();
        assertEquals(-1,evenIndividual.compareTo(emptyIndividual));
    }
}