package ga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Stephen West on 22/03/2019.
 */
class IndividualTest {//NOPMD

    private static final int CHROMOSOME_SIZE = 10;
    private static final double TOL = 0.00001;
    private Individual emptyIndividual;
    private Individual evenIndividual;
    private Individual completeIndividual;
    private ArrayList<Integer> emptyChromosome;
    private ArrayList<Integer>  evenChromosome;
    private ArrayList<Integer>  completeChromosome;

    @BeforeEach
    void setUp(){
        emptyChromosome = new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0));
        emptyIndividual = new Individual(emptyChromosome);
        evenChromosome = new ArrayList<>(Arrays.asList(1,0,1,1,0,0,0,1,1,0));
        evenIndividual = new Individual(evenChromosome);
        completeChromosome = new ArrayList<>(Arrays.asList(1,1,1,1,1,1,1,1,1));
        completeIndividual = new Individual(completeChromosome);

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
        assertTrue(IntStream.range(0,emptyChromosome.size()).allMatch(gene ->
                0 == emptyIndividual.getGene(gene)),
                "act: 0000000000 != exp: "+emptyIndividual.toString());
    }

    @Test
    void testEvenChromosome(){
        assertTrue(IntStream.range(0,evenChromosome.size()).allMatch(gene ->
                        evenChromosome.get(gene) == evenIndividual.getGene(gene)),
                "act: 1011000110 != exp: "+evenIndividual.toString());
    }



    @Test
    void testToStringMethodOnEmptyIndividual(){
        assertTrue("0000000000".equals(emptyIndividual.toString()),
                "act 00000000000 != act: "+emptyIndividual.toString());
    }

    @Test
    void testToStringMethodOnEvenIndividual(){
        assertTrue("1011000110".equals(evenIndividual.toString()),
                "act 1011000110 != act: "+evenIndividual.toString());
    }

    @Test
    void testFlip(){
        evenIndividual.flip(0);
        evenIndividual.flip(1);

        evenIndividual.flip(2);
        assertTrue("0101000110".equals(evenIndividual.toString()),
                "act 0101000110 != act: "+evenIndividual.toString());
    }

    // test Fitness

    @Test
    void testCompleteIndividualGetFitness(){
        completeIndividual.evaluateFitness(GAUtils.getMeanGeneFitness);
        assertEquals(1.0,completeIndividual.getFitness(), TOL);
    }

    @Test
    void testSillyMutateFirstGeneOnCompleteIndividual(){
        Double mutationRate = 1.0;
        completeIndividual.mutate(GAUtils.sillyMutateFirst,mutationRate);
        completeIndividual.evaluateFitness(GAUtils.getMeanGeneFitness);
        assertEquals(0, completeIndividual.getGene(0));
    }

    @Test
    void testMutateAllGenes(){
        completeIndividual.mutate(GAUtils.mutate, 1.0);
        assertTrue(IntStream.range(0,completeChromosome.size()).allMatch(gene ->
                        0 == emptyIndividual.getGene(gene)),
                "act: 0000000000 != exp: "+emptyIndividual.toString());

        completeIndividual.mutate(GAUtils.mutate, 0.0);
        assertTrue(IntStream.range(0,completeChromosome.size()).allMatch(gene ->
                        0 == emptyIndividual.getGene(gene)),
                "act: 0000000000 != exp: "+emptyIndividual.toString());
    }

    @Test
    void testNoVariableReferencePassed(){
        evenChromosome.set(0,0);
        evenChromosome.set(8,1);
        // should not change values of evenIndividual
        assertTrue("1011000110".equals(evenIndividual.toString()),
                "act 0101000110 != act: "+evenIndividual.toString());

    }

    @Test
    void testCreateRandom(){
        Individual individual = new Individual(50);
        int zerocount = (int)individual.getChromosome().stream().filter(gene -> gene == 0).count();
        int onescount = (int)individual.getChromosome().stream().filter(gene -> gene == 1).count();
        System.out.println(individual);
        // that all gene 1s or 0s should counted to 50
        assertEquals(50,zerocount+onescount);
        // that the difference should be less than 10
        assertTrue( Math.abs(zerocount - onescount) < 10);
    }


    @Test
    void testEquality(){
        // same as evenIndividual
        Individual anotherChromosome = new Individual(new ArrayList<>(Arrays.asList(1,0,1,1,0,0,0,1,1,0)));
        assertTrue(evenIndividual.equals(anotherChromosome));
    }

    @Test
    void testCompareTo(){
        emptyIndividual.evaluateFitness(GAUtils.getMeanGeneFitness);
        evenIndividual.evaluateFitness(GAUtils.getMeanGeneFitness);
        completeIndividual.evaluateFitness(GAUtils.getMeanGeneFitness);

        assertEquals(1,emptyIndividual.compareTo(evenIndividual));
        assertEquals(-1,completeIndividual.compareTo(evenIndividual));
        assertEquals(1,emptyIndividual.compareTo(completeIndividual));
    }

}
