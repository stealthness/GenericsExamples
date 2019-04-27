package ga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Stephen West on 27/04/2019.
 */
public class TestGeneticAlgorithm {

    private GeneticAlgorithm ga;

    @BeforeEach
    void setUP(){
        ga = new GeneticAlgorithm.GeneticAlgorithmBuilder()
                .selectionFunction(GAUtils.selectWeightedParent)
                .crossoverFunction(GAUtils.crossoverFunction)
                .mutationFunction(GAUtils.mutatePopulation)
                .chromosomeSize(50)
                .populationSize(100)
                .mutationRate(0.001)
                .crossoverRate(0.95)
                .elitismCount(2)
                .build();
    }

    @Test
    void testsetup(){
        assertNotNull(ga);
        var pop = ga.initPopulation();
        assertFalse(ga.isTerminationConditionMet(pop));
        assertEquals(100,pop.size());
        assertEquals(-1, pop.getPopulationFitness());
    }
}
