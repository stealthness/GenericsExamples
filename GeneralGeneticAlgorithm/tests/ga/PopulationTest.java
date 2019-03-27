package ga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Stephen West on 27/03/2019.
 */
class PopulationTest {

    private static final int CHROMOSOME_SIZE = 10;
    private static final double TOL = 0.00001;
    private static final int POPULATION_SIZE = 6;
    private Individual emptyIndividual;
    private Individual evenIndividual;
    private Individual completeIndividual;
    private ArrayList<Integer> emptyChromosome;
    private ArrayList<Integer>  evenChromosome;
    private ArrayList<Integer>  completeChromosome;
    private Population emptyPopulation;
    private Population evenPopulation;
    private Population completePopulation;

    @BeforeEach
    void setUp() {
        emptyChromosome = new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0));
        emptyIndividual = new Individual(emptyChromosome);
        evenChromosome = new ArrayList<>(Arrays.asList(1,0,1,1,0,0,0,1,1,0));
        evenIndividual = new Individual(evenChromosome);
        completeChromosome = new ArrayList<>(Arrays.asList(1,1,1,1,1,1,1,1,1));
        completeIndividual = new Individual(completeChromosome);

        emptyPopulation = new Population();
        IntStream.range(0,POPULATION_SIZE).forEach(i ->{
            emptyPopulation.getPopulation().add(emptyIndividual);
        });
        completePopulation = createPopulationWith(6,completeIndividual);
        evenPopulation = createPopulationWith(6,evenIndividual);
    }


    @Test
    void testCreateEmptyIndividual(){
        assertEquals(POPULATION_SIZE,emptyPopulation.getPopulation().size());
        assertEquals(CHROMOSOME_SIZE,emptyPopulation.getPopulation().get(0).size());
        assertTrue(emptyPopulation.getPopulation().stream()
                .allMatch(individual -> individual.equals(emptyIndividual)));
        // check that changing emptyIndividual has no side effects
        emptyIndividual.getChromosome().set(0,1);
        emptyIndividual.flip(2);
        assertTrue(emptyPopulation.getPopulation().stream()
                .allMatch(individual -> individual.equals(emptyIndividual)));
    }

    @Test
    void testCreateCompleteIndividual(){
        assertEquals(POPULATION_SIZE,completePopulation.getPopulation().size());
        assertTrue(completePopulation.getPopulation().stream()
                .allMatch(individual -> individual.equals(completeIndividual)));
        // check that changing emptyIndividual has no side effects
        completeIndividual.getChromosome().set(0,0);
        completeIndividual.flip(2);
        assertTrue(completePopulation.getPopulation().stream()
                .allMatch(individual -> individual.equals(completeIndividual)));
    }

    @Test
    void testMutateOnEmptyPopulationWithMutationRateOf1(){
        // mutation rate of 1.0 will flip all gens
        completePopulation.mutate(GAUtils.mutate,1.0);
        assertTrue(completePopulation.getPopulation().stream()
                .allMatch(individual -> individual.equals(emptyIndividual)));
    }

    @Test
    void testMutateOnEmptyPopulationWithMutationRateOf0(){
        // mutation rate of 1.0 will flip all gens
        completePopulation.mutate(GAUtils.mutate,0.0);
        assertTrue(completePopulation.getPopulation().stream()
                .allMatch(individual -> individual.equals(completeIndividual)));
    }

    @Test
    void testPrintPopulation(){
        var expString = "1011000110\n"+
                        "1011000110\n"+
                        "1011000110\n"+
                        "1011000110\n"+
                        "1011000110\n"+
                        "1011000110\n";
        assertEquals(expString,evenPopulation.toString());
    }



    // helper method
    private Population createPopulationWith(int size, Individual individual){
        Population population = new Population();
        IntStream.range(0,size).forEach(i -> population.getPopulation().add(individual));
        return population;
    }


}