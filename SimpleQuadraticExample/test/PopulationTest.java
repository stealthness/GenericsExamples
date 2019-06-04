import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class PopulationTest {


    private static final double MAX_RUNS = 10;

    @Test
    void testCreatPopulation(){
        Population population = Population.builder().build();
        assertEquals(Population.class, population.getClass());
    }

    @Test
    void generateTreesOfDepth0(){
        for (int i = 0; i < MAX_RUNS;i++){
            Population population = Population.builder().build();
            assertEquals(Population.class, population.getClass());
            assertPopulation(Optional.of(1),Optional.of(0), population);
        }
    }

    private void assertPopulation(Optional<Integer> expSize,Optional<Integer> expDepth, Population actPopulation){

        if (!expSize.isEmpty()){
            assertEquals(expSize.get(),actPopulation.getMaxSize());
        }
        if (!expDepth.isEmpty()){
            assertEquals(expDepth.get(),actPopulation.getMaxDepth());
        }
    }
}