package queens;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Stephen West on 03/05/2019.
 */
class BoardTest {

    @Test
    void getBoardValueOfNoQueens() {

        String expBoard = "00000000\n00000000\n00000000\n00000000\n00000000\n00000000\n00000000\n00000000\n";

        Board board = new Board();
        assertNotNull(board);
        assertEquals(expBoard,board.printBoardValue());
    }


    @Test
    void testBoardWithOneQueenAT00(){
        String expBoardValue = "10000001\n10000010\n00000100\n10001000\n10010000\n10100000\n11000000\n11111111\n";
        String expBoard = "........\n........\n........\n........\n........\n........\n........\nQ.......\n";
        Board board = new Board();
        board.setQueenAt(0,0);
        assertNotNull(board);
        assertEquals(expBoard,board.toString());
        assertEquals(expBoardValue,board.printBoardValue());
    }
}