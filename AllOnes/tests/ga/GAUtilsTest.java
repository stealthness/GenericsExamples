package ga;

import ga.GeneticAlgorithm;
import ga.Individual;
import ga.Population;

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
//        emptyIndividual = new ga.Individual(emptyChromosome);
//        evenIndividual = new ga.Individual(evenChromosome);
//        completeIndividual = new ga.Individual(completeChromosome);
//        thirdIndividual = new ga.Individual(thirdChromosome);
//
//        population = new ga.Population(10);
//        population.initialize(8);
//        Arrays.stream(population.getIndividuals()).forEach(individual -> individual.setChromosome(thirdChromosome));
//        population.setIndividual(0,evenIndividual);
//        population.setIndividual(4,completeIndividual);
//        population.setIndividual(9,emptyIndividual);
//
//        ga = new ga.GeneticAlgorithm(8,1.0,0.0,0);
//    }
//
//    // Test using fitness function returns the last gene
//
//    @Test
//    void testSillyFirstGeneFitnessReturnsFirstGeneOfEmptyIndividual(){
//        assertEquals(0, ga.GAUtils.sillyFirstGeneFitness.apply(emptyIndividual),TOL);
//    }
//    @Test
//    void testSillyFirstGeneFitnessReturnsFirstGeneOfEvenIndividual(){
//        assertEquals(1, ga.GAUtils.sillyFirstGeneFitness.apply(evenIndividual),TOL);
//    }
//    @Test
//    void testSillyFirstGeneFitnessReturnsFirstGeneOfCompleteIndividual(){
//        assertEquals(1, ga.GAUtils.sillyFirstGeneFitness.apply(completeIndividual),TOL);
//    }
//
//    // Test using fitness function returns the last gene
//
//    @Test
//    void testSillyLastGeneFitnessReturnsFirstGeneOfEmptyIndividual(){
//        assertEquals(0, ga.GAUtils.sillyLastGeneFitness.apply(emptyIndividual),TOL);
//    }
//    @Test
//    void testSillyLastGeneFitnessReturnsFirstGeneOfEvenIndividual(){
//        assertEquals(0, ga.GAUtils.sillyLastGeneFitness.apply(evenIndividual),TOL);
//    }
//    @Test
//    void testSillyLastGeneFitnessReturnsFirstGeneOfCompleteIndividual(){
//        assertEquals(1, ga.GAUtils.sillyLastGeneFitness.apply(completeIndividual),TOL);
//    }
//
//    // Test using fitness function returns the mean gene fitness
//
//    @Test
//    void testGetMeanGeneFitnessReturnsFirstGeneOfEmptyIndividual(){
//        assertEquals(0.0, ga.GAUtils.getMeanGeneFitness.apply(emptyIndividual),TOL);
//    }
//    @Test
//    void testGetMeanGeneFitnessReturnsFirstGeneOfEvenIndividual(){
//        assertEquals(0.5, ga.GAUtils.getMeanGeneFitness.apply(evenIndividual),TOL);
//    }
//    @Test
//    void testGetMeanGeneFitnessReturnsFirstGeneOfCompleteIndividual(){
//        assertEquals(1, ga.GAUtils.getMeanGeneFitness.apply(completeIndividual),TOL);
//    }
//
//    // Test using selection functions that return a selected individual from a individuals
//
//    @Test
//    void testSillyFirstIndividualSelection(){
//        ga.evaluateFitness(population);
//        var actIndividual = ga.selectParent(ga.GAUtils.sillySelectFirstIndividual,population);
//        ga.IndividualTest.assertEqualIndividuals(evenIndividual,actIndividual);
//
//        actIndividual = ga.selectParent(ga.GAUtils.sillySelectFirstIndividual,population);
//        ga.IndividualTest.assertEqualIndividuals(evenIndividual,actIndividual);
//
//        population.setIndividual(0,completeIndividual);
//        actIndividual = ga.selectParent(ga.GAUtils.sillySelectFirstIndividual,population);
//        ga.IndividualTest.assertEqualIndividuals(completeIndividual,actIndividual);
//    }
//
//    @Test
//    void testSillyLastIndividualSelection(){
//        ga.evaluateFitness(population);
//        var actIndividual = ga.selectParent(ga.GAUtils.sillySelectLastIndividual,population);
//        ga.IndividualTest.assertEqualIndividuals(emptyIndividual,actIndividual);
//
//        actIndividual = ga.selectParent(ga.GAUtils.sillySelectLastIndividual,population);
//        ga.IndividualTest.assertEqualIndividuals(emptyIndividual,actIndividual);
//
//        population.setIndividual(9,emptyIndividual);
//        actIndividual = ga.selectParent(ga.GAUtils.sillySelectLastIndividual,population);
//        ga.IndividualTest.assertEqualIndividuals(emptyIndividual,actIndividual);
//    }
//
//    @Test
//    void testSelectWeightedWheelParentWithOneOptions() {
//        population = buildPopulation(emptyIndividual);
//        population.setIndividual(0, completeIndividual);
//        var actIndividual = ga.selectParent(ga.GAUtils.selectWeightedWheelParent, population);
//        ga.IndividualTest.assertEqualIndividuals(completeIndividual, actIndividual);
//
//        IntStream.range(0, 100).forEach(i -> {
//            population = buildPopulation(emptyIndividual);
//            var index = (int) (Math.random() * 9);
//            population.setIndividual(index, completeIndividual);
//            population.evaluateFitness();
//            ga.IndividualTest.assertEqualIndividuals(completeIndividual, ga.selectParent(ga.GAUtils.selectWeightedWheelParent, population));
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
//            ga.IndividualTest.assertEqualIndividuals(thirdChromosome,ga.selectParent(ga.GAUtils.selectWeightedWheelParent,population));
//
//        });
//    }
//
//    // private helper method
//
//    private ga.Population buildPopulation(ga.Individual individual){
//        population = new ga.Population(10);
//        population.initialize(8);
//
//        IntStream.range(0,population.size()).forEach(i -> population.setIndividual(i,individual));
//        population.evaluateFitness();
//        return population;
//    }

}