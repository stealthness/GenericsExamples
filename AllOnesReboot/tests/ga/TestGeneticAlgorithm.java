package ga;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ExecutionException;

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

    @Test
    void testFutureReturnsPopulation() throws InterruptedException, ExecutionException {
        var pop1 = ga.initPopulation();
        var evolove = ga.evolove(pop1);
        while(!evolove.isDone()){
            System.out.println("waiting");
            Thread.sleep(100);
        }
        var pop2 = evolove.get();
        assertNotNull(pop2);
        assertEquals(100,pop2.size());
    }
}
