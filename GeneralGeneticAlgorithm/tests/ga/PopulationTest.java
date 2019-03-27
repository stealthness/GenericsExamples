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
    }


    @Test
    void testCreateEmptyIndividual(){
        assertEquals(POPULATION_SIZE,emptyPopulation.getPopulation().size());

    }
}