import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Stephen West on 08/03/2019.
 */
class GeneticAlgorithmTest {

    int POP_SIZE = 100;
    int CHROMO_SIZE = 30;
    int ELITISM = 0;
    double CROSSOVER_RATE = 0.95;
    double MUTATION_RATE = 0.01;

    GeneticAlgorithm ga;

    @BeforeEach
    void setUp(){
        ga = new GeneticAlgorithm(POP_SIZE,CROSSOVER_RATE,MUTATION_RATE,ELITISM);
    }

    @Test
    void testCreate(){
        assertNotNull(ga);
    }

}