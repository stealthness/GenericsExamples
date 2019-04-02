package ga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.BiFunction;
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
    private static final int NO_OF_RUNS = 1000;
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

    @Test
    void testEvenPopulationFitness(){
        var population = createPopulationWith(POPULATION_SIZE,evenIndividual);
        var expectFitness = new Double[]{0.5,0.5,0.5,0.5,0.5,0.5};
        testFitnessOfPopulation(expectFitness, GAUtils.getMeanGeneFitness,population);
    }

    @Test
    void testMixedPopulationFitness(){
        var population = createPopulationWith(4,emptyIndividual);
        population.setIndividual(1,completeIndividual);
        population.setIndividual(2,new Individual(new ArrayList<Integer>(Arrays.asList(0,1,0,0,0,0,0,0,0,0))));
        population.setIndividual(3,evenIndividual);
        var expectFitness = new Double[]{0.0,1.0,0.1,0.5};
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
        population.setIndividual(5,completeIndividual);
        population.evaluate(GAUtils.getMeanGeneFitness);

        System.out.println(population);
        population.sort();
        System.out.println(population);
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

    // test selection
    @Test
    void testSelectionWithSillySelection0(){
        Population population = createPopulationWith(6,emptyIndividual);
        population.setIndividual(0,completeIndividual);
        assertEquals(completeIndividual,GAUtils.sillySelectFirstIndividual.apply(population));
    }

    @Test
    void testSelectionWithFlatRandomChance(){
        var population = createPopulation(1);
        population.evaluate(GAUtils.getMeanGeneFitness);
        population.sort();
        var score = new double[10];
        var count = 0;
        while (count++<1000){
            var fitness = GAUtils.equalWeightedIndividual.apply(population).getFitness();
            score[(int)(fitness*10)-1] +=1;
        }
        System.out.println(population);
        Arrays.stream(score).forEach(System.out::println);
    }



    @Test
    void testSelectionWithSillySelection(){
        Population population = createPopulationWith(6,emptyIndividual);
        population.setIndividual(0,completeIndividual);
        System.out.println(population);
        population.crossover(GAUtils.sillyFirstParentGeneCrossover,GAUtils.sillySelectFirstIndividual,0.95);
        System.out.println(population);

    }

    // test crossover

    @Test
    void testCrossoverWithSillyCrossoverFunction(){
        Population population = createPopulationWith(6,emptyIndividual);
        population.setIndividual(0,completeIndividual);
        System.out.println(population);
        population.crossover(GAUtils.sillyFirstParentGeneCrossover,GAUtils.sillySelectFirstIndividual,1.0);
        assertTrue(IntStream.range(1,6).allMatch(i -> population.getIndividual(i).toString().equals("0111111111")));
    }

    @Test
    void testCrossoverWithSillyCrossoverFunctionAvg(){
        var count = 0;
        var numberCrossed = 0;
        while (++count < NO_OF_RUNS){
            Population population = createPopulationWith(6,emptyIndividual);
            population.setIndividual(0,completeIndividual);
            population.crossover(GAUtils.sillyFirstParentGeneCrossover,GAUtils.sillySelectFirstIndividual,0.5);
            numberCrossed += IntStream.range(1,6).filter(i-> population.getIndividual(i).toString().equals("0111111111")).count();
        }
        // 100 based 2sd on 10000 coin flips
        assertTrue(Math.abs(NO_OF_RUNS*5/2 - numberCrossed)< 100);
    }

    @Test
    void testCrossoverWithSillyCrossoverAvg(){
        testCrossoverFunctionAvg(GAUtils.sillySelectFirstIndividual,GAUtils.sillyFirstParentGeneCrossover,0.5,100);
        testCrossoverFunctionAvg(GAUtils.sillySelectFirstIndividual,GAUtils.sillyFirstParentGeneCrossover,0.9,50);
        testCrossoverFunctionAvg(GAUtils.sillySelectFirstIndividual,GAUtils.sillyFirstParentGeneCrossover,1.0,10);
    }


    private void testCrossoverFunctionAvg(Function<Population,Individual> selectionFunction,
                                          BiFunction<Individual,Individual,Individual> crossoverFunction,
                                          Double crossoverRate, int expStandardDeviation){
        var count = 0;
        var numberCrossed = 0;
        while (++count < NO_OF_RUNS){
            Population population = createPopulationWith(6,emptyIndividual);
            population.setIndividual(0,completeIndividual);
            population.crossover(crossoverFunction,selectionFunction,crossoverRate);
            numberCrossed += IntStream.range(1,6).filter(i-> population.getIndividual(i).toString().equals("0111111111")).count();
        }
        // 100 based 2sd on 10000 coin flips

        assertTrue(Math.abs(NO_OF_RUNS*5*crossoverRate - numberCrossed)< expStandardDeviation);
    }


    // helper method
    private Population createPopulationWith(int size, Individual individual){
        Population population = new Population();
        IntStream.range(0,size).forEach(i -> population.addIndividual(individual));
        return population;
    }

    private Population createPopulation(int type){
        Population population = new Population();
        switch (type){
            case 1:
                population.addIndividual(new Individual(new ArrayList<>(Arrays.asList(0,0,1,0,0,0,0,0,0,0)))); //1
                population.addIndividual(new Individual(new ArrayList<>(Arrays.asList(0,0,1,1,0,1,0,1,0,0)))); //4
                population.addIndividual(new Individual(new ArrayList<>(Arrays.asList(0,0,0,1,0,0,1,0,0,0)))); //2
                population.addIndividual(new Individual(new ArrayList<>(Arrays.asList(0,0,1,0,1,1,1,1,1,1)))); //7
                population.addIndividual(new Individual(new ArrayList<>(Arrays.asList(0,1,0,1,0,0,1,0,0,0)))); //3
                population.addIndividual(new Individual(new ArrayList<>(Arrays.asList(1,1,1,0,1,1,1,1,1,1)))); //9
                population.addIndividual(new Individual(new ArrayList<>(Arrays.asList(1,0,1,1,1,1,0,1,1,1)))); //8
                population.addIndividual(new Individual(new ArrayList<>(Arrays.asList(1,1,1,1,1,1,1,1,1,1)))); //10
                population.addIndividual(new Individual(new ArrayList<>(Arrays.asList(1,0,0,0,1,1,1,1,1,0)))); //6
                population.addIndividual(new Individual(new ArrayList<>(Arrays.asList(1,1,0,0,1,1,1,0,0,0)))); //5
        }

        return population;
    }

}