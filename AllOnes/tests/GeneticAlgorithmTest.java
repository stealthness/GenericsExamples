import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.stream.IntStream;

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

        emptyPopulation = new Population(POP_SIZE);
        evenPopulation = new Population(POP_SIZE);
        completePopulation = new Population(POP_SIZE);


        emptyPopulation.initialize(CHROMO_SIZE);
        evenPopulation.initialize(CHROMO_SIZE);
        completePopulation.initialize(CHROMO_SIZE);

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
        IndividualTest.assertEqualIndividuals(completeIndividual,ga.getFitessIndividual(0,population));
        IndividualTest.assertEqualIndividuals(evenIndividual,ga.getFitessIndividual(1,population));
        IndividualTest.assertEqualIndividuals(emptyIndividual,ga.getFitessIndividual(2,population));

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
        int count = 0;
        while (count++ < 100){
            Population population = ga.initPopulation(CHROMO_SIZE);
            Individual selectedIndividual = ga.selectParent(population);
            assertTrue(Arrays.asList(population.getIndividuals()).contains(selectedIndividual));
        }
    }

    @Test
    void testCrossover(){
    }

    @Test
    void testSolutionFoundIsFalseForEmptyPopulation(){
        assertFalse(ga.solutionFound(emptyPopulation));
    }

    @Test
    void testSolutionFoundIsTrueForOneCompleteIndividualInEmptyPopulation(){
        emptyPopulation.setIndividual(3,completeIndividual);
        emptyPopulation.evaluateFitness();
        assertTrue(ga.solutionFound(emptyPopulation));
    }

    @Test
    void testCrossoverPopulationOnEmptyPopulation(){
        Population crossoverPopulation;
        crossoverPopulation = ga.crossoverPopulation(emptyPopulation);
        assertTrue(IntStream.range(0,POP_SIZE).allMatch(i -> crossoverPopulation.getIndividuals()[i].equals(emptyPopulation.getIndividuals()[i])));
    }

    @Test
    void testCrossoverPopulationOnCompletePopulation(){
        Population crossoverPopulation;
        assertNotNull(completePopulation);
        crossoverPopulation = ga.crossoverPopulation(completePopulation);
        assertTrue(IntStream.range(0,POP_SIZE).allMatch(ind ->crossoverPopulation.getIndividuals()[ind].equals(completePopulation.getIndividuals()[ind])));
    }

    @Test
    void testCrossoverPopulationOnRandomPopulation(){
        Population crossoverPopulation;
        Population population = new Population(POP_SIZE);
        population.initialize(CHROMO_SIZE);
        crossoverPopulation = ga.crossoverPopulation(population);
        assertFalse(IntStream.range(0,POP_SIZE).allMatch(i -> crossoverPopulation.getIndividuals()[i].equals(population.getIndividuals()[i])));
    }

    @Test
    void testMutateGensEvenGenes(){

        // Intialise
        GeneticAlgorithm gaExtreme = new GeneticAlgorithm(POP_SIZE,0,1,0);
        Population population = new Population(POP_SIZE);
        population.initialize(CHROMO_SIZE);
        Arrays.stream(population.getIndividuals())
                .forEach(individual -> individual.setChromosome(new int[]{1,1,1,0,0,0,1,0}));

        // Mutate1,1,1,0,0,0,1,0
        Population resultPopulation = gaExtreme.mutatePopulation(population);
        assertTrue(IntStream.range(0,POP_SIZE)
                .allMatch(ind -> IntStream.range(0,CHROMO_SIZE)
                        .noneMatch(gene -> resultPopulation.getGene(ind,gene) == evenPopulation.getGene(ind,gene))));
        assertTrue(IntStream.range(0,POP_SIZE)
                .allMatch(ind -> {
                    return resultPopulation.getIndividuals()[ind].equals(new Individual(new int[]{0,0,0,1,1,1,0,1}));
                }));
    }

    @Test
    void testMutateFunctionOnlyFirstGeneOnEmptyPopulation(){
        ga = new GeneticAlgorithm(POP_SIZE,0.0,1.0,0);
        assertEquals(0,emptyPopulation.getGene(0,0));
        var newPopulation = ga.mutatePopulation(GAUtils.sillyFirstGeneMutateOnly,emptyPopulation);
        assertEquals(1,newPopulation.getGene(0,0));

        // 0.0 mutation rate no changes
        ga = new GeneticAlgorithm(POP_SIZE,0.0,0.0,0);
        assertEquals(0,emptyPopulation.getGene(0,0));
        newPopulation = ga.mutatePopulation(GAUtils.sillyFirstGeneMutateOnly,emptyPopulation);
        assertEquals(0,newPopulation.getGene(0,0));
    }

    @Test
    void testMutateFunctionOnlyFirstGeneOnCompletePopulation(){
        ga = new GeneticAlgorithm(POP_SIZE,0.0,1.0,0);
        assertEquals(1,completePopulation.getGene(0,0));
        var newPopulation = ga.mutatePopulation(GAUtils.sillyFirstGeneMutateOnly,completePopulation);
        assertEquals(0,newPopulation.getGene(0,0));


        ga = new GeneticAlgorithm(POP_SIZE,0.0,0.0,0);
        assertEquals(1,completePopulation.getGene(0,0));
        newPopulation = ga.mutatePopulation(GAUtils.sillyFirstGeneMutateOnly,completePopulation);
        assertEquals(1,newPopulation.getGene(0,0));
    }


}