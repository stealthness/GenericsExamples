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

    static void assertEqualIndividuals(Individual actIndividual, Individual expIndividual){
        assertTrue(IntStream.range(0,expIndividual.size())
                .allMatch(i-> actIndividual.getGene(i) == expIndividual.getGene(i)));
    }

    static void assertEqualIndividuals(int[] actChromosome, Individual expIndividual){
        assertEqualIndividuals(new Individual(actChromosome),expIndividual);
    }

    static boolean isEqualIndividuals(Individual actIndividual, Individual expIndividual){
        return IntStream.range(0,expIndividual.size())
                .allMatch(i-> actIndividual.getGene(i) == expIndividual.getGene(i));
    }

    static boolean isEqualIndividuals(int[] actChromosome, Individual expIndividual){
        return isEqualIndividuals(new Individual(actChromosome),expIndividual);
    }



    @BeforeEach
    void setUp(){
        emptyChromosome = new int[]{0,0,0,0,0,0,0,0};
        evenChromosome = new int[]{1,1,1,0,0,0,1,0};
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
        assertEqualIndividuals(emptyChromosome,emptyIndividual);
    }

    @Test
    void testGetEmptyChromosomeAfterChangingEmptyIndividual(){
        IndividualTest.assertEqualIndividuals(emptyChromosome,emptyIndividual);
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
        // switch an complete chromosome to a empty chromosome
        IntStream.range(0,CHROMOSOME_LENGTH).forEach(gene -> completeIndividual.setGene(gene,0));
        assertEqualIndividuals(emptyChromosome,completeIndividual);
    }

    @Test
    void testSetGeneToOne(){
        // switch an empty chromosome to a complete chromosome
        IntStream.range(0,CHROMOSOME_LENGTH).forEach(gene -> emptyIndividual.setGene(gene,1));
        assertEqualIndividuals(completeChromosome,emptyIndividual);
    }

    @Test
    void testCreateRandomChromosome(){
        Individual individual = new Individual(CHROMOSOME_LENGTH);
        assertTrue(Arrays.stream(individual.getChromosome()).allMatch(gene -> gene == 0 || gene == 1));
    }

    @Test
    void testToStringEmptyIndividual(){
        assertEquals("00000000",emptyIndividual.toString());
    }

    @Test
    void testToStringCompleteIndividual(){
        assertEquals("11111111",completeIndividual.toString());
    }

    @Test
    void testToStringEvenIndividual(){
        assertEquals("11100010",evenIndividual.toString());
    }

    // Test Fitness

    @Test
    void testGetFitnessForEmptyIndividual(){
        emptyIndividual.evaluateFitness();
        assertEquals(0.0,emptyIndividual.getFitness(),TOL);
        emptyIndividual.evaluateFitness(GAUtils.getMeanGeneFitness);
        assertEquals(0.0,emptyIndividual.getFitness(),TOL);
        emptyIndividual.evaluateFitness(GAUtils.sillyFirstGeneFitness);
        assertEquals(0.0,emptyIndividual.getFitness(),TOL);
        emptyIndividual.evaluateFitness(GAUtils.sillyLastGeneFitness);
        assertEquals(0.0,emptyIndividual.getFitness(),TOL);
    }

    @Test
    void testGetFitnessForEvenIndividual(){
        evenIndividual.evaluateFitness();
        assertEquals(0.5,evenIndividual.getFitness(),TOL);
        evenIndividual.evaluateFitness(GAUtils.getMeanGeneFitness);
        assertEquals(0.5,evenIndividual.getFitness(),TOL);
        // {1,1,1,0,0,0,1,0}
        evenIndividual.evaluateFitness(GAUtils.sillyLastGeneFitness);
        assertEquals(0.0,evenIndividual.getFitness(),TOL);
        evenIndividual.evaluateFitness(GAUtils.sillyFirstGeneFitness);
        assertEquals(1.0,evenIndividual.getFitness(),TOL);

    }

    @Test
    void testGetFitnessForCompleteIndividual(){
        completeIndividual.evaluateFitness();
        assertEquals(1.0,completeIndividual.getFitness(),TOL);
        completeIndividual.evaluateFitness(GAUtils.getMeanGeneFitness);
        assertEquals(1.0,completeIndividual.getFitness(),TOL);
        completeIndividual.evaluateFitness(GAUtils.sillyLastGeneFitness);
        assertEquals(1.0,completeIndividual.getFitness(),TOL);
        completeIndividual.evaluateFitness(GAUtils.sillyFirstGeneFitness);
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