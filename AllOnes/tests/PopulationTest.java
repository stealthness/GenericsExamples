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


    @Test
    void testSetGeneToOneForEmptyPopulation(){

        int count = 0;
        // repeat test a numebr of times
        while(count++<100){
            // create an empty individuals
            setAllIndividualsInPopulationTo(emptyIndividual);
            // select a random gene in individuals
            var randomIndividualIndex = (int)(Math.random()*POP_SIZE);
            var randomGeneIndex = (int)(Math.random()*CHROMO_SIZE);
            // change random gene in individuals to 1
            population.setGene(randomIndividualIndex,randomGeneIndex,1);
            // assert all but the random gene is 0
            assertTrue(IntStream.range(0,POP_SIZE)
                    .allMatch(i -> IntStream.range(0,CHROMO_SIZE)
                            .filter(gene -> i != randomIndividualIndex && gene != randomGeneIndex)
                            .allMatch(gene -> population.getGene(i,gene) == 0)));
            // assert that the random gene is 1
            assertEquals(1,population.getGene(randomIndividualIndex,randomGeneIndex));
        }

    }


    @Test
    void testEvenPopulationWithSillyLastFitnessFunctions(){
        setAllIndividualsInPopulationTo(evenIndividual);
        population.evaluateFitness();
        assertEquals(0.5,population.getFitness(),TOL);

        population.evaluateFitness(GAUtils.sillyLastGeneFitness);
        assertEquals(0.0,population.getFitness(),TOL);
    }

    @Test
    void testEvenPopulationWithSillyFirstFitnessFunctions(){

        setAllIndividualsInPopulationTo(evenIndividual);
        population.evaluateFitness();
        assertEquals(0.5,population.getFitness(),TOL);

        population.evaluateFitness(GAUtils.sillyFirstGeneFitness);
        assertEquals(1.0,population.getFitness(),TOL);
    }

    @Test
    void testPopulationWithIncreasingIndividualsGetMeanFitnessReturnsCorrectIndividual(){
        population = setMixedIndividualPopulation();
        population.evaluateFitness(GAUtils.getMeanGeneFitness);
        assertEquals(0.0,population.getIndividuals()[1].getFitness());
        assertEquals(1.0,population.getIndividuals()[7].getFitness());
        assertEquals(0.1,population.getIndividuals()[0].getFitness());
        assertEquals(0.5,population.getIndividuals()[9].getFitness());
        assertEquals(0.9,population.getIndividuals()[5].getFitness());
    }


    @Test
    void testClone(){
        setAllIndividualsInPopulationTo(emptyIndividual);
        var newPopulation = population.clone();
        population.setGene(0,0,1);
        // after changing individuals, no side affect on newPopulation
        assertTrue(Arrays.stream(newPopulation.getIndividuals()).allMatch(individual -> emptyIndividual.equals(individual)));
    }


    // helper methods

    private void setAllIndividualsInPopulationTo(Individual individual){
        population.initialize(CHROMO_SIZE);
        IntStream.range(0,POP_SIZE).forEach(i -> population.setIndividual(i,individual));
    }

    static public Population setMixedIndividualPopulation(){
        Population pop = new Population(10);
        pop.initialize(10);
        pop.setIndividual(0,new Individual(new int[]{0,0,1,0,0,0,0,0,0,0})); //1
        pop.setIndividual(1,new Individual(new int[]{0,0,0,0,0,0,0,0,0,0})); //0
        pop.setIndividual(2,new Individual(new int[]{0,0,0,1,0,0,1,0,0,0})); //2
        pop.setIndividual(3,new Individual(new int[]{0,0,1,0,1,1,1,1,1,1})); //7
        pop.setIndividual(4,new Individual(new int[]{0,1,0,1,0,0,1,0,0,0})); //3
        pop.setIndividual(5,new Individual(new int[]{1,1,1,0,1,1,1,1,1,1})); //9
        pop.setIndividual(6,new Individual(new int[]{1,0,1,1,1,1,0,1,1,1})); //8
        pop.setIndividual(7,new Individual(new int[]{1,1,1,1,1,1,1,1,1,1})); //10
        pop.setIndividual(8,new Individual(new int[]{1,0,0,0,1,1,1,0,0,0})); //4
        pop.setIndividual(9,new Individual(new int[]{1,1,0,0,1,1,1,0,0,0})); //5
        return pop;
    }

}