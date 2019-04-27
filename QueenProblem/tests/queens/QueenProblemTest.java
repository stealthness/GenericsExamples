package queens;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;


/**
 * Created by Stephen West on 19/04/2019.
 */
class QueenProblemTest {

    final int DEFAULT_SIZE = 8;

    @Test
    void testToStringMethod() {

        QueenProblem qb1 = new QueenProblem("000000000");
        QueenProblem qb2 = new QueenProblem(new int[DEFAULT_SIZE]);

        assertEquals(DEFAULT_SIZE, qb1.toString().length());

    }
}