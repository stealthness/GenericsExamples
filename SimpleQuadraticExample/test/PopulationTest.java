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
                .initialMaxDepth(2)
                .maxSize(10)
                .build();
        population.generate();
        population.getIndividuals().forEach(individual -> {
            System.out.println(individual.print());
            assertEquals(2, individual.getDepth());
            assertEquals(7, individual.size());
        });
    }


    @Test
    void testGenerateFullTreePopulationOfDepth1(){
        population = Population.builder()
                .generationMethod("full")
                .initialMaxDepth(1)
                .maxSize(10)
                .build();
        population.generate();
        population.getIndividuals().forEach(individual -> {
            System.out.println(individual.print());
            assertEquals(1, individual.getDepth());
            assertEquals(3, individual.size());
        });
    }

    @Test
    void testGenerateFullTreePopulationOfDepth0(){
        population = Population.builder()
                .generationMethod("full")
                .initialMaxDepth(0)
                .maxSize(10)
                .build();
        population.generate();
        population.getIndividuals().forEach(individual -> {
            System.out.println(individual.print());
            assertEquals(0, individual.getDepth());
            assertEquals(1, individual.size());
            assertTrue(individual.getRoot().getClass().equals(VariableNode.class) || individual.getRoot().getClass().equals(TerminalNode.class));
        });
    }

    @Test
    void testGetFittest(){
        population = Population.builder()
                .maxSize(4)
                .build();
        Individual newIndividual = Individual.generate("full",2,1);
        newIndividual.setRoot(TestUtils.xPlus1Tree);
        population.addIndividual(newIndividual);

        population.getIndividuals().forEach(individual -> System.out.println(individual.print()));
        newIndividual.setRoot(TestUtils.twoTree);
        population.addIndividual(newIndividual);
        population.addIndividual(newIndividual);
        population.addIndividual(newIndividual);
        System.out.println("---");
        population.evaluate(population.getTestNode());

        population.getIndividuals().forEach(individual -> System.out.println(individual.print()));
    }

    // size

    // helper method

    void printPopulation(){
        if (population == null){
            System.out.println("population is null");
        } else if (population.getIndividuals() == null){
            System.out.println("individuals are null");
        }
        if (population.size()==0){
            System.out.println("population is empty");
        }else{
            System.out.println(population.getIndividuals().size());
            population.getIndividuals().forEach(individual ->  System.out.println(individual.print()));
        }

    }

}