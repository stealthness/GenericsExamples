package queens;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


/**
 * Created by Stephen West on 19/04/2019.
 */
class QueenProblemTest {

    final int DEFAULT_SIZE = 8;

    @Test
    void testToStringMethod() {

        QueenProblem qb1 = new QueenProblem("000000000");

        assertEquals(DEFAULT_SIZE, qb1.toString().length());

        qb1 = new QueenProblem("01234567");
        assertEquals("01234567", qb1.toString());

    }

    @Test
    void getFitnessOnEmptyQueensProblem() {
        QueenProblem qb1 = new QueenProblem("000000000");

        // fitness for no queens set will be 64
        assertEquals(64, qb1.getFitness());
    }

    @Test
    void testIsValidString(){
        // not valid
        QueenProblem qb1 = new QueenProblem("000000000");
        assertFalse(qb1.isValid());
        qb1 = new QueenProblem("1123456");
        assertFalse(qb1.isValid());
        qb1 = new QueenProblem("56732189");
        assertFalse(qb1.isValid());

        // valid
        qb1 = new QueenProblem("01234567");
        assertTrue(qb1.isValid());
        qb1 = new QueenProblem("03721456");
        assertTrue(qb1.isValid());
    }
}