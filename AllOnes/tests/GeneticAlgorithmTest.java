import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Stephen West on 08/03/2019.
 */
class GeneticAlgorithmTest {

    private static final int POP_SIZE = 10;
    private static final int CHROMO_SIZE = 8;
    private static final int ELITISM = 0;
    private static final double CROSSOVER_RATE = 0.95;
    private static final double MUTATION_RATE = 0.01;

    private Population emptyPopulation,evenPopulation,completePopulation;
    private Individual emptyIndividual,evenIndividual,completeIndividual;
    private int[] emptyChromosome,evenChromosome,completeChromosome;

    private GeneticAlgorithm ga;

    @BeforeEach
    void setUp(){
        ga = new GeneticAlgorithm(POP_SIZE,CROSSOVER_RATE,MUTATION_RATE,ELITISM);


        emptyChromosome = new int[]{0,0,0,0,0,0,0,0};
        evenChromosome = new int[]{1,1,1,0,0,0,1,0};
        completeChromosome = new int[]{1,1,1,1,1,1,1,1};

        emptyIndividual = new Individual(emptyChromosome);
        evenIndividual = new Individual(evenChromosome);
        completeIndividual = new Individual(completeChromosome);

        emptyPopulation = new Population(CHROMO_SIZE);
        evenPopulation = new Population(CHROMO_SIZE);
        completePopulation = new Population(CHROMO_SIZE);


        emptyPopulation.initialize(POP_SIZE);
        evenPopulation.initialize(POP_SIZE);
        completePopulation.initialize(POP_SIZE);

        Arrays.stream(emptyPopulation.getIndividuals()).forEach(individual -> individual.setChromosome(emptyChromosome));
        Arrays.stream(evenPopulation.getIndividuals()).forEach(individual -> individual.setChromosome(evenChromosome));
        Arrays.stream(completePopulation.getIndividuals()).forEach(individual -> individual.setChromosome(completeChromosome));
    }


    @Test
    void testCreate(){
        assertNotNull(ga);
        assertNotNull(emptyChromosome);
        assertNotNull(emptyIndividual);
        assertNotNull(emptyPopulation);
    }

    @Test
    void testSetPopulationToEmpty(){
        ga.setPopulation(emptyPopulation);
        assertEquals(emptyIndividual.toString(),emptyPopulation.getIndividuals()[0].toString());
        assertTrue(Arrays.stream(ga.getPopulation().getIndividuals())
                .allMatch(individual -> individual.toString().equals(emptyIndividual.toString())));
    }

    @Test
    void testSetPopulationToComplete(){
        ga.setPopulation(completePopulation);
        assertTrue(Arrays.stream(ga.getPopulation().getIndividuals())
                .allMatch(individual -> individual.toString().equals(completeIndividual.toString())));
    }

    @Test
    void testGetPopulationSetToEmpty(){
        ga.setPopulation(emptyPopulation);
        Population population = ga.getPopulation();
        assertTrue(Arrays.stream(population.getIndividuals())
                .allMatch(individual -> individual.toString().equals(emptyIndividual.toString())));
    }

    @Test
    void testGetPopulationSetToComplete(){
        ga.setPopulation(completePopulation);
        Population population = ga.getPopulation();
        assertTrue(Arrays.stream(population.getIndividuals())
                .allMatch(individual -> individual.toString().equals(completeIndividual.toString())));
    }

    @Test
    void testGetFitess(){
        ga.setPopulation(emptyPopulation);
        ga.getPopulation().setIndividual(3,completeIndividual);
        ga.evaluateFitness();
        assertTrue(ga.getPopulation().getFitness()>0.0);
        assertEquals("11111111", ga.getFitess(0).toString());
        assertEquals("00000000", ga.getFitess(1).toString());
    }

}