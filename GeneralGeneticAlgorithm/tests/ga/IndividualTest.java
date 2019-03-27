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
        assertTrue(IntStream.range(0,emptyChromosome.size()).allMatch(gene ->
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

    // test Fitness

    @Test
    void testCompleteIndividualGetFitness(){
        completeIndividual.evaluateFitness(GAUtils.getMeanGeneFitness);
        assertEquals(1.0,completeIndividual.getFitness(), TOL);
    }




}
