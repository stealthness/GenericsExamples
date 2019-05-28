import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PopulationTest {


    @Test
    void testCreatPopulation(){
        Population population = Population.builder().build();
        assertEquals(Population.class, population.getClass());
    }
}