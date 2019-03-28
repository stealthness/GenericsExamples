package ga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;
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
        // Chromosomes
        emptyChromosome = new ArrayList<>(Arrays.asList(0,0,0,0,0,0,0,0,0,0));
        evenChromosome = new ArrayList<>(Arrays.asList(1,0,1,1,0,0,0,1,1,0));
        completeChromosome = new ArrayList<>(Arrays.asList(1,1,1,1,1,1,1,1,1,1));
        // Individuals
        emptyIndividual = new Individual(emptyChromosome);
        evenIndividual = new Individual(evenChromosome);
        completeIndividual = new Individual(completeChromosome);
        // Populations
        emptyPopulation = createPopulationWith(POPULATION_SIZE,emptyIndividual);
        evenPopulation = createPopulationWith(POPULATION_SIZE,evenIndividual);
        completePopulation = createPopulationWith(POPULATION_SIZE,completeIndividual);
    }

    // Test Create

    @Test
    void testCreateEmptyIndividual(){
        testCreate(emptyIndividual,emptyChromosome,0);
    }

    @Test
    void testCreateCompleteIndividual(){
        testCreate(completeIndividual,completeChromosome,0);
    }

    @Test
    void testCreateWithEvenIndividual(){
        testCreate(evenIndividual,evenChromosome,0);
    }

    private void testCreate(Individual expIndividual, ArrayList<Integer> expChromosome, int flipValue){
        Population population = createPopulationWith(POPULATION_SIZE,expIndividual);
        assertNotNull(population);
        assertTrue( population.getPopulation().stream()
                .allMatch(individual -> individual.equals(expIndividual)));
        // check for side effects
        expIndividual.getChromosome().set(0,flipValue);
        expIndividual.flip(3);
        assertNotEquals(new Individual(expChromosome),expIndividual);
        assertTrue( population.getPopulation().stream()
                .allMatch(individual -> individual.equals(new Individual(expChromosome))));

    }

    // test Override Method

    @Test
    void testPrintCompletePopulation(){
        testToString(completeIndividual,completePopulation);
    }

    @Test
    void testPrintEmptyPopulation(){
        testToString(emptyIndividual,emptyPopulation);
    }

    @Test
    void testPrintEvenPopulation(){
        testToString(evenIndividual,evenPopulation);
    }

    private void testToString(Individual individual,Population population){
        var sb = new StringBuilder();
        IntStream.range(0,POPULATION_SIZE).forEach(line ->{
            sb.append(individual.toString()+System.lineSeparator());
        });
        assertEquals(sb.toString(), population.toString());
    }

    // test changing individuals

    @Test
    void testChangeEmptyPopulationWithCompleteIndividual(){
        testChangeIndividualInPopulation(emptyIndividual,completeIndividual,3);
    }

    @Test
    void testChangeEvenPopulationWithEmptyIndividual(){
        testChangeIndividualInPopulation(evenIndividual,emptyIndividual,2);
    }
    private void testChangeIndividualInPopulation(Individual initialIndividual, Individual newIndividual, int index){
        Population population = createPopulationWith(POPULATION_SIZE,initialIndividual);
        assertTrue(population.getPopulation().stream()
                .allMatch(individual -> individual.equals(initialIndividual)));
        population.setIndividual(index,newIndividual);
        assertTrue( IntStream.range(0,POPULATION_SIZE)
                .filter(i -> i !=index)
                .allMatch(i -> population.getIndividual(i).equals(initialIndividual)));
        assertEquals(population.getIndividual(index),newIndividual);
    }

    // test fitness

    @Test
    void testCompletePopulationFitness(){
        var population = createPopulationWith(POPULATION_SIZE,completeIndividual);
        var expectFitness = new Double[]{1.0,1.0,1.0,1.0,1.0,1.0};
        testFitnessOfPopulation(expectFitness, GAUtils.getMeanGeneFitness,population);
    }

    private void testFitnessOfPopulation(Double[] expFitness, Function<Individual,Double> fitnessFunction, Population population){
        population.evaluate(fitnessFunction);
        assertTrue(IntStream.range(0,expFitness.length)
                .allMatch(i -> population.getFitness(i) - expFitness[i] < TOL));
    }

    // test Sort

    @Test
    void testSort(){
        Population population = emptyPopulation;
        population.setIndividual(2,evenIndividual);
        population.setIndividual(4,evenIndividual);
        population.setIndividual(4,completeIndividual);
        population.evaluate(GAUtils.getMeanGeneFitness);
        population.sort();
        assertEquals(completeIndividual,population.getIndividual(0));
        assertEquals(evenIndividual,population.getIndividual(1));
        assertEquals(evenIndividual,population.getIndividual(2));
        assertEquals(emptyIndividual,population.getIndividual(3));
    }

    // test Mutation

    @Test
    void testMutationHasNoSideEffects(){
        Population population = createPopulationWith(4,evenIndividual);
        population.getPopulation().get(1).flip(1);
        Arrays.stream(new int[]{0, 2, 3}).forEach(i -> {
            assertEquals(evenIndividual,population.getIndividual(i));
        });
        assertNotEquals(evenIndividual,population.getIndividual(1));
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





    // helper method
    private Population createPopulationWith(int size, Individual individual){
        Population population = new Population();
        IntStream.range(0,size).forEach(i -> population.addIndividual(individual));
        return population;
    }


}