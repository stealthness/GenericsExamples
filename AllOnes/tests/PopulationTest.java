import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PopulationTest {

    private static final int POP_SIZE = 20;
    private static final int CHROMO_SIZE = 8;
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
            assertTrue(Arrays.stream(individual.getChromosome()).allMatch(gene -> gene == 0 || gene == 1));
        });
    }

}