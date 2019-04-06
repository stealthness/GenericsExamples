package ga;

import ga.Individual;
import ga.Population;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PopulationTest {

    private static final int POP_SIZE = 20;
    private static final int CHROMOSOME_SIZE = 8;
    private static final double TOL = 0.01;
    private static final int MAX_COUNT = 10000;
    private Population population;
    private Individual emptyIndividual,evenIndividual,completeIndividual;
    private int[] emptyChromosome,evenChromosome,completeChromosome;



    @BeforeEach
    void setUp(){
        emptyChromosome = new int[]{0,0,0,0,0,0,0,0};
        evenChromosome = new int[]{1,1,1,0,0,0,1,0};
        completeChromosome = new int[]{1,1,1,1,1,1,1,1};

        emptyIndividual = new ga.Individual(emptyChromosome);
        evenIndividual = new ga.Individual(evenChromosome);
        completeIndividual = new ga.Individual(completeChromosome);

        population = new ga.Population(POP_SIZE);
    }

    @Test
    void testSize(){
        assertEquals(POP_SIZE,population.size());
    }
//
//
//    @Test
//    void testInitializePopulation(){
//        population.initialize(CHROMOSOME_SIZE);
//        Arrays.stream(population.getIndividuals()).forEach(individual -> {
//            assertEquals(CHROMOSOME_SIZE,individual.size());
//            assertTrue(individual.getChromosome().stream()
//                    .allMatch(gene -> gene == 0 || gene == 1));
//        });
//    }

//    @Test
//    void testRandomIndividual(){
//        var count = 0;
//        var noOnes = 0;
//        while (count++ < MAX_COUNT){
//            var size = (int)(Math.random()*10)+2;
//            int[] randomArray = createRandomArray(Optional.of(size));
//            StringBuilder sb = new StringBuilder();
//            Arrays.stream(randomArray).forEach(sb::append);
//            count +=size;
//            var individual = new ga.Individual(randomArray);
//            assertEquals(size, individual.size(), "size");
//            assertEquals(sb.toString(),individual.toString(),"string");
//            noOnes += Arrays.stream(randomArray).filter(x -> x==1).count();
//        }
//
//        // check that within 2 SD of mean
//        assertTrue(Math.abs(count - 2*noOnes) < MAX_COUNT/5," error:"+(Math.abs(count - 2*noOnes)) );
//    }
//
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

    // Note evaluation of population fitness now been moved to GeneticAlgorithms

    @Test
    void testGetFitnessForCompleteIndividuals(){
        testFitnessFor(1.0,completeIndividual);
    }

    @Test
    void testGetFitnessForEvenIndividuals(){
        testFitnessFor(0.5,evenIndividual);
    }

    @Test
    void testGetFitnessForEmptyIndividuals(){

        testFitnessFor(0.0,emptyIndividual);
    }

    private void testFitnessFor(double fitness,Individual individual) {
        setAllIndividualsInPopulationTo(individual);
        population.setPopulationFitness(fitness);
        Arrays.stream(population.getIndividuals()).forEach(i -> {
            GAUtils.getAllOnesFitness.apply(i);
            assertEquals(fitness, i.getFitness(), TOL);
        });
        assertEquals(fitness, population.getPopulationFitness(), TOL);
    }

    private void setAllIndividualsInPopulationTo(ga.Individual individual){
        population = new Population(POP_SIZE,CHROMOSOME_SIZE);
        IntStream.range(0,POP_SIZE).forEach(i -> population.setIndividual(i,individual));
    }
//
//    static public ga.Population setMixedIndividualPopulation(){
//        ga.Population pop = new ga.Population(10);
//        pop.initialize(10);
//        pop.setIndividual(0,new ga.Individual(new int[]{0,0,1,0,0,0,0,0,0,0})); //1
//        pop.setIndividual(1,new ga.Individual(new int[]{0,0,0,0,0,0,0,0,0,0})); //0
//        pop.setIndividual(2,new ga.Individual(new int[]{0,0,0,1,0,0,1,0,0,0})); //2
//        pop.setIndividual(3,new ga.Individual(new int[]{0,0,1,0,1,1,1,1,1,1})); //7
//        pop.setIndividual(4,new ga.Individual(new int[]{0,1,0,1,0,0,1,0,0,0})); //3
//        pop.setIndividual(5,new ga.Individual(new int[]{1,1,1,0,1,1,1,1,1,1})); //9
//        pop.setIndividual(6,new ga.Individual(new int[]{1,0,1,1,1,1,0,1,1,1})); //8
//        pop.setIndividual(7,new ga.Individual(new int[]{1,1,1,1,1,1,1,1,1,1})); //10
//        pop.setIndividual(8,new ga.Individual(new int[]{1,0,0,0,1,1,1,0,0,0})); //4
//        pop.setIndividual(9,new ga.Individual(new int[]{1,1,0,0,1,1,1,0,0,0})); //5
//        return pop;
//    }
//
    public int[] createRandomArray(Optional<Integer> size){
        int[] randomArray = new int[(size.orElse(CHROMOSOME_SIZE))];
        IntStream.range(0,size.orElse(CHROMOSOME_SIZE)).forEach(i->{
            randomArray[i] = (Math.random()<0.5)?1:0;
        });
        return randomArray;
    }

    // test on private method

    @Test
    void testRandomArray(){
        var count = 0;
        var noOnes = 0;
        while (count++ < MAX_COUNT){
            var size = (int)(Math.random()*10)+2;
            int[] randomArray = createRandomArray(Optional.of(size));
            StringBuilder sb = new StringBuilder();
            Arrays.stream(randomArray).forEach(sb::append);
            count += size;
            noOnes += Arrays.stream(randomArray).filter(x -> x==1).count();
        }
        // check that within 2 SD of mean
        assertTrue(Math.abs(count - 2*noOnes) < MAX_COUNT/5," error:"+(Math.abs(count - 2*noOnes)) );
    }


}