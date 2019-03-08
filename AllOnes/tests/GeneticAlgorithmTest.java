import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Stephen West on 08/03/2019.
 */
class GeneticAlgorithmTest {

    static final int POP_SIZE = 10;
    static final int CHROMO_SIZE = 8;
    static final int ELITISM = 0;
    static final double CROSSOVER_RATE = 0.95;
    static final double MUTATION_RATE = 0.01;

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

        IntStream.range(0,POP_SIZE).forEach(i ->{
            Arrays.stream(emptyPopulation.getIndividuals()).forEach(individual -> individual = emptyIndividual);
            Arrays.stream(evenPopulation.getIndividuals()).forEach(individual -> individual = evenIndividual);
            Arrays.stream(completePopulation.getIndividuals()).forEach(individual -> individual = completeIndividual);
        });
    }

    @Test
    void testCreate(){
        assertNotNull(ga);
    }

    @Test
    void testSetPopulationToEmpty(){
        ga.setPopulatation(emptyPopulation);
        assertTrue(Arrays.stream(ga.getPopulation().getIndividuals())
                .allMatch(individual -> individual.toString().equals(emptyIndividual.toString())));
    }

    @Test
    void testSetPopulationToComplete(){
        ga.setPopulatation(completePopulation);
        assertTrue(Arrays.stream(ga.getPopulation().getIndividuals())
                .allMatch(individual -> individual.toString().equals(completeIndividual.toString())));
    }

}