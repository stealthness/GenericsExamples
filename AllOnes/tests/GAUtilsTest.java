

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Stephen West on 20/03/2019.
 */
class GAUtilsTest {

    private static final double TOL = 0.001;
    private int[] emptyChromosome,evenChromosome,completeChromosome,thirdChromosome;
    private Individual emptyIndividual,evenIndividual,completeIndividual,thirdIndividual;
    private Population population;
    private GeneticAlgorithm ga;
//    @BeforeEach
//    void SetUp(){
//        emptyChromosome = new int[]{0,0,0,0,0,0,0,0};
//        evenChromosome = new int[]{1,1,1,0,0,0,1,0};
//        completeChromosome = new int[]{1,1,1,1,1,1,1,1};
//        thirdChromosome = new int[]{1,1,0,1,0,0,0,0};
//
//        emptyIndividual = new Individual(emptyChromosome);
//        evenIndividual = new Individual(evenChromosome);
//        completeIndividual = new Individual(completeChromosome);
//        thirdIndividual = new Individual(thirdChromosome);
//
//        population = new Population(10);
//        population.initialize(8);
//        Arrays.stream(population.getIndividuals()).forEach(individual -> individual.setChromosome(thirdChromosome));
//        population.setIndividual(0,evenIndividual);
//        population.setIndividual(4,completeIndividual);
//        population.setIndividual(9,emptyIndividual);
//
//        ga = new GeneticAlgorithm(8,1.0,0.0,0);
//    }
//
//    // Test using fitness function returns the last gene
//
//    @Test
//    void testSillyFirstGeneFitnessReturnsFirstGeneOfEmptyIndividual(){
//        assertEquals(0, GAUtils.sillyFirstGeneFitness.apply(emptyIndividual),TOL);
//    }
//    @Test
//    void testSillyFirstGeneFitnessReturnsFirstGeneOfEvenIndividual(){
//        assertEquals(1, GAUtils.sillyFirstGeneFitness.apply(evenIndividual),TOL);
//    }
//    @Test
//    void testSillyFirstGeneFitnessReturnsFirstGeneOfCompleteIndividual(){
//        assertEquals(1, GAUtils.sillyFirstGeneFitness.apply(completeIndividual),TOL);
//    }
//
//    // Test using fitness function returns the last gene
//
//    @Test
//    void testSillyLastGeneFitnessReturnsFirstGeneOfEmptyIndividual(){
//        assertEquals(0, GAUtils.sillyLastGeneFitness.apply(emptyIndividual),TOL);
//    }
//    @Test
//    void testSillyLastGeneFitnessReturnsFirstGeneOfEvenIndividual(){
//        assertEquals(0, GAUtils.sillyLastGeneFitness.apply(evenIndividual),TOL);
//    }
//    @Test
//    void testSillyLastGeneFitnessReturnsFirstGeneOfCompleteIndividual(){
//        assertEquals(1, GAUtils.sillyLastGeneFitness.apply(completeIndividual),TOL);
//    }
//
//    // Test using fitness function returns the mean gene fitness
//
//    @Test
//    void testGetMeanGeneFitnessReturnsFirstGeneOfEmptyIndividual(){
//        assertEquals(0.0, GAUtils.getMeanGeneFitness.apply(emptyIndividual),TOL);
//    }
//    @Test
//    void testGetMeanGeneFitnessReturnsFirstGeneOfEvenIndividual(){
//        assertEquals(0.5, GAUtils.getMeanGeneFitness.apply(evenIndividual),TOL);
//    }
//    @Test
//    void testGetMeanGeneFitnessReturnsFirstGeneOfCompleteIndividual(){
//        assertEquals(1, GAUtils.getMeanGeneFitness.apply(completeIndividual),TOL);
//    }
//
//    // Test using selection functions that return a selected individual from a individuals
//
//    @Test
//    void testSillyFirstIndividualSelection(){
//        ga.evaluateFitness(population);
//        var actIndividual = ga.selectParent(GAUtils.sillySelectFirstIndividual,population);
//        IndividualTest.assertEqualIndividuals(evenIndividual,actIndividual);
//
//        actIndividual = ga.selectParent(GAUtils.sillySelectFirstIndividual,population);
//        IndividualTest.assertEqualIndividuals(evenIndividual,actIndividual);
//
//        population.setIndividual(0,completeIndividual);
//        actIndividual = ga.selectParent(GAUtils.sillySelectFirstIndividual,population);
//        IndividualTest.assertEqualIndividuals(completeIndividual,actIndividual);
//    }
//
//    @Test
//    void testSillyLastIndividualSelection(){
//        ga.evaluateFitness(population);
//        var actIndividual = ga.selectParent(GAUtils.sillySelectLastIndividual,population);
//        IndividualTest.assertEqualIndividuals(emptyIndividual,actIndividual);
//
//        actIndividual = ga.selectParent(GAUtils.sillySelectLastIndividual,population);
//        IndividualTest.assertEqualIndividuals(emptyIndividual,actIndividual);
//
//        population.setIndividual(9,emptyIndividual);
//        actIndividual = ga.selectParent(GAUtils.sillySelectLastIndividual,population);
//        IndividualTest.assertEqualIndividuals(emptyIndividual,actIndividual);
//    }
//
//    @Test
//    void testSelectWeightedWheelParentWithOneOptions() {
//        population = buildPopulation(emptyIndividual);
//        population.setIndividual(0, completeIndividual);
//        var actIndividual = ga.selectParent(GAUtils.selectWeightedWheelParent, population);
//        IndividualTest.assertEqualIndividuals(completeIndividual, actIndividual);
//
//        IntStream.range(0, 100).forEach(i -> {
//            population = buildPopulation(emptyIndividual);
//            var index = (int) (Math.random() * 9);
//            population.setIndividual(index, completeIndividual);
//            population.evaluateFitness();
//            IndividualTest.assertEqualIndividuals(completeIndividual, ga.selectParent(GAUtils.selectWeightedWheelParent, population));
//
//        });
//    }
//
//    @Test
//    void testSelectWeightedWheelParentWithThreeOptions(){
//
//        IntStream.range(0,100).forEach(i-> {
//            population = buildPopulation(emptyIndividual);
//            IntStream.range(0,3).forEach(j-> population.setIndividual((int)(Math.random()*9),thirdIndividual));
//
//            population.evaluateFitness();
//            IndividualTest.assertEqualIndividuals(thirdChromosome,ga.selectParent(GAUtils.selectWeightedWheelParent,population));
//
//        });
//    }
//
//    // private helper method
//
//    private Population buildPopulation(Individual individual){
//        population = new Population(10);
//        population.initialize(8);
//
//        IntStream.range(0,population.size()).forEach(i -> population.setIndividual(i,individual));
//        population.evaluateFitness();
//        return population;
//    }

}