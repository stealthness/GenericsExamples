import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class PopulationTest {


    private static final double MAX_RUNS = 10;
    Population population;

    @Test
    void testCreatPopulation(){
        population = Population.builder().build();
        assertEquals(Population.class, population.getClass());
    }

    @Test
    void generateTreesOfDepth0(){
        for (int i = 0; i < MAX_RUNS;i++){
            int maxPopulationSize = new Random().nextInt(10)+10;
            population = Population.builder()
                                    .maxPopulation(maxPopulationSize)
                                    .build();
            population.initialise();
            assertEquals(maxPopulationSize,population.getMaxPopulation());
            assertEquals(Population.class, population.getClass());
            assertPopulation(Optional.of(1),Optional.of(0), population);
        }
    }

    @Test
    void generateTreesOfDepth1(){
        for (int i = 0; i < MAX_RUNS;i++){

            int maxPopulationSize = new Random().nextInt(10)+10;
            population = Population.builder()
                    .maxGenerationDepth(1)
                    .generationMethod("full")
                    .maxPopulation(maxPopulationSize)
                    .build();
            population.initialise();
            assertEquals(maxPopulationSize,population.getMaxPopulation());
            assertEquals(Population.class, population.getClass());
            assertPopulation(Optional.of(2),Optional.of(1), population);
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