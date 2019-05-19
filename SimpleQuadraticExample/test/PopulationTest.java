import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PopulationTest {

    Population population;

    @BeforeEach
    void setUp(){
        population = Population.builder()
                        .maxSize(4)
                        .build();
    }


    // Test Lombok Builder

    @Test
    void testLombokBuilder(){
        assertNotNull(population);
        assertEquals(4, population.getMaxSize());
    }

    // generate and size

    @Test
    void testGenerateAndSize(){
        assertEquals(0,population.size());
        assertEquals(4, population.getMaxSize());
        population.generate("full");
        assertEquals(4,population.size());

        printPopulation();
    }


    // size

    // helper method

    void printPopulation(){
        population.getIndividuals().forEach(individual -> System.out.println(individual.print()));
    }

}