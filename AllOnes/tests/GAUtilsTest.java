import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Stephen West on 20/03/2019.
 */
class GAUtilsTest {

    private static final double TOL = 0.001;
    private int[] emptyChromosome,evenChromosome,completeChromosome;
    private Individual emptyIndividual,evenIndividual,completeIndividual;

    @BeforeEach
    void SetUp(){
        emptyChromosome = new int[]{0,0,0,0,0,0,0,0};
        evenChromosome = new int[]{1,1,1,0,0,0,1,0};
        completeChromosome = new int[]{1,1,1,1,1,1,1,1};

        emptyIndividual = new Individual(emptyChromosome);
        evenIndividual = new Individual(evenChromosome);
        completeIndividual = new Individual(completeChromosome);
    }

    // Test using fitness function returns the last gene


    @Test
    void testSillyFirstGeneFitnessReturnsFirstGeneOfEmptyIndividual(){
        assertEquals(0, GAUtils.sillyFirstGeneFitness.apply(emptyIndividual),TOL);
    }
    @Test
    void testSillyFirstGeneFitnessReturnsFirstGeneOfEvenIndividual(){
        assertEquals(1, GAUtils.sillyFirstGeneFitness.apply(evenIndividual),TOL);
    }
    @Test
    void testSillyFirstGeneFitnessReturnsFirstGeneOfCompleteIndividual(){
        assertEquals(1, GAUtils.sillyFirstGeneFitness.apply(completeIndividual),TOL);
    }

    // Test using fitness function returns the last gene

    @Test
    void testSillyLastGeneFitnessReturnsFirstGeneOfEmptyIndividual(){
        assertEquals(0, GAUtils.sillyLastGeneFitness.apply(emptyIndividual),TOL);
    }
    @Test
    void testSillyLastGeneFitnessReturnsFirstGeneOfEvenIndividual(){
        assertEquals(0, GAUtils.sillyLastGeneFitness.apply(evenIndividual),TOL);
    }
    @Test
    void testSillyLastGeneFitnessReturnsFirstGeneOfCompleteIndividual(){
        assertEquals(1, GAUtils.sillyLastGeneFitness.apply(completeIndividual),TOL);
    }

    // Test using fitness function returns the mean gene fitness

    @Test
    void testGetMeanGeneFitnessReturnsFirstGeneOfEmptyIndividual(){
        assertEquals(0.0, GAUtils.getMeanGeneFitness.apply(emptyIndividual),TOL);
    }
    @Test
    void testGetMeanGeneFitnessReturnsFirstGeneOfEvenIndividual(){
        assertEquals(0.5, GAUtils.getMeanGeneFitness.apply(evenIndividual),TOL);
    }
    @Test
    void testGetMeanGeneFitnessReturnsFirstGeneOfCompleteIndividual(){
        assertEquals(1, GAUtils.getMeanGeneFitness.apply(completeIndividual),TOL);
    }

}