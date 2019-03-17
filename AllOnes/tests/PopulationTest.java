import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

class PopulationTest {

    private static final int POP_SIZE = 20;
    private static final int CHROMO_SIZE = 8;
    private static final double TOL = 0.01;
    private Population population;
    private Individual emptyIndividual,evenIndividual,completeIndividual;
    private int[] emptyChromosome,evenChromosome,completeChromosome;

    @BeforeEach
    void setUp(){
        emptyChromosome = new int[]{0,0,0,0,0,0,0,0};
        evenChromosome = new int[]{1,1,1,0,0,0,1,0};
        completeChromosome = new int[]{1,1,1,1,1,1,1,1};

        emptyIndividual = new Individual(emptyChromosome);
        evenIndividual = new Individual(evenChromosome);
        completeIndividual = new Individual(completeChromosome);

        population = new Population(POP_SIZE);
    }

    @Test
    void testSize(){
        assertEquals(POP_SIZE,population.size());
    }

    @Test
    void testInitializePopulation(){
        population.initialize(CHROMO_SIZE);
        Arrays.stream(population.getIndividuals()).forEach(individual -> {
            assertEquals(CHROMO_SIZE,individual.size());
            assertTrue(Arrays.stream(individual.getChromosome())
                    .allMatch(gene -> gene == 0 || gene == 1));
        });
    }

    @Test
    void testSetPopulationAllToEmpty(){
        setAllIndividualsInPopulationTo(emptyIndividual);

        assertTrue(Arrays.stream(population.getIndividuals())
                .allMatch(individual -> emptyIndividual.toString().equals(individual.toString())));
    }


    @Test
    void testSetPopulationAllToComplete(){
        setAllIndividualsInPopulationTo(completeIndividual);

        assertTrue(Arrays.stream(population.getIndividuals())
                .allMatch(individual -> completeIndividual.toString().equals(individual.toString())));
    }

    @Test
    void testGetFitnessForCompleteIndividuals(){
        setAllIndividualsInPopulationTo(completeIndividual);
        population.evaluateFitness();
        assertEquals(1.0, population.getFitness(),TOL);
    }

    @Test
    void testGetFitnessForEvenIndividuals(){
        setAllIndividualsInPopulationTo(evenIndividual);
        population.evaluateFitness();
        assertEquals(0.5, population.getFitness(),TOL);
    }
    @Test
    void testGetFitnessForEmptyIndividuals(){
        setAllIndividualsInPopulationTo(emptyIndividual);
        population.evaluateFitness();
        assertEquals(0.0, population.getFitness(),TOL);
    }

    // helper methods

    private void setAllIndividualsInPopulationTo(Individual individual){
        population.initialize(CHROMO_SIZE);
        IntStream.range(0,POP_SIZE).forEach(i -> population.setIndividual(i,individual));
    }


    @Test
    void testSetGeneToOneForEmptyPopulation(){
        setAllIndividualsInPopulationTo(emptyIndividual);
        assertEquals(0,population.getGene(0,0));
        assertTrue(IntStream.range(0,POP_SIZE)
                .allMatch(ind-> IntStream.range(0,CHROMO_SIZE)
                        .allMatch(gene -> (0==population.getGene(ind,gene)))));
        population.setGene(0,0,1);
        assertFalse(IntStream.range(0,POP_SIZE)
                .allMatch(ind-> IntStream.range(0,CHROMO_SIZE)
                        .allMatch(gene -> (0==population.getGene(ind,gene)))));
        assertEquals(1,population.getGene(0,0));
    }




}