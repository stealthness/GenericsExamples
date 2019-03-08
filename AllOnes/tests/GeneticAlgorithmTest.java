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
    private static final double TOL = 0.01;

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
    void testGetFitessIndividual(){
        Population population = emptyPopulation;
        population.setIndividual(3,completeIndividual);
        population.setIndividual(5,evenIndividual);
        ga.evaluateFitness(population);
        assertTrue(ga.getFitness(population)>0.0);
        assertEquals(completeIndividual.toString(), ga.getFitessIndividual(0,population).toString());
        assertEquals(evenIndividual.toString(), ga.getFitessIndividual(1,population).toString());
        assertEquals(emptyIndividual.toString(), ga.getFitessIndividual(2,population).toString());
    }

    @Test
    void testEvaluateEmptyPopulation(){
        testEvaluatePopulation(0.0,emptyPopulation);
    }

    @Test
    void testEvaluateEvenPopulation(){
        testEvaluatePopulation(0.5,evenPopulation);
    }

    @Test
    void testEvaluateCompletePopulation(){
        testEvaluatePopulation(1.0,completePopulation);
    }

    void testEvaluatePopulation(double expFitness, Population population){
        ga.evaluateFitness(population);
        assertEquals(expFitness,ga.getFitness(population),TOL);
    }

    @Test
    void testSelectParent(){
        Population population = ga.initPopulation(CHROMO_SIZE);
        Individual selectedIndividual = ga.selectParent(population);
        assertTrue(Arrays.stream(population.getIndividuals()).anyMatch(individual -> individual.equals(selectedIndividual)));
    }

}