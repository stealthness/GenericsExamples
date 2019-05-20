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

    @Test
    void testGenerateFullTreePopulationOfDepth2(){
        population = Population.builder()
                .generationMethod("full")
                .maxSize(10)
                .build();
    }




    @Test
    void testGetFittest(){
        population = Population.builder()
                .maxSize(4)
                .build();
        Individual newIndividual = Individual.generate();
        newIndividual.setRoot(TestUtils.xPlus1Tree);
        population.addIndividual(newIndividual);

        newIndividual.setRoot(TestUtils.twoTree);
        population.addIndividual(newIndividual);
        population.addIndividual(newIndividual);
        population.addIndividual(newIndividual);


        population.getIndividuals().forEach(individual -> System.out.println(individual.print()));
    }

    // size

    // helper method

    void printPopulation(){
        population.getIndividuals().forEach(individual -> System.out.println(individual.print()));
    }

}