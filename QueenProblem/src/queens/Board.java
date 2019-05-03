package queens;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Stephen West on 03/05/2019.
 *
 * This Java class hold the representation of a chess board
 *
 *  (0,7)...(7,7)
 *  .   .       .
 *  .      .    .
 *  .         . .
 *  (0,0)...(7,0)
 *
 *  the bottom left square is (0,0), any posith (n,m) will nth column, and mth row
 *
 *  the board will store the each row in and array
 *
 *
 */
@Data
public class Board {

    private static final int MAX_BOARD_ROWS = 8;
    private static final int MAX_BOARD_COLS = 8;
    public static final String QUEEN_CHAR = "Q";
    public static final String EMPTYSPACE_CHAR = ".";
    int[][] board;

    public Board(){
        board = new int[MAX_BOARD_ROWS][MAX_BOARD_COLS];
    }


    void setQueenAt(int row, int col){
        board[row][col] = 1;
    }


    /**
     * return a a board array representing the value of each board based the number of queens attacking
     *
     *  00100101    ........
     *  00100110    ........
     *  10100200    ........
     *  21101100    ........
     *  01210100    ........
     *  11221212    ..Q.....
     *  01111110    ........
     *  21212211    .....Q..
     *
     * @return
     */
    public int[][] getBoardValue(){
        int[][] boardValue = new int[MAX_BOARD_ROWS][MAX_BOARD_COLS];
        IntStream.range(0,MAX_BOARD_ROWS).forEach(row ->{
            IntStream.range(0,MAX_BOARD_COLS).forEach(col ->{
                boardValue[row][col] = 0;
            });
        });
        return boardValue;
    }

    public String printBoardValue(){
        int[][] boardValue = getBoardValue();
        var sb = new StringBuilder();
        IntStream.range(0,MAX_BOARD_ROWS).forEach(row ->{
            IntStream.range(0,MAX_BOARD_COLS).forEach(col ->{
                sb.append(boardValue[row][col]);
            });
            sb.append("\n");
        });
        return sb.toString();
    }


    @Override
    public String toString() {
        var sb = new StringBuilder();
        IntStream.range(0,MAX_BOARD_ROWS).forEach(row ->{
            IntStream.range(0,MAX_BOARD_COLS).forEach(col ->{
                sb.append((board[MAX_BOARD_ROWS-row-1][col] == 1)? QUEEN_CHAR : EMPTYSPACE_CHAR);
            });
            sb.append("\n");
        });
        return sb.toString();
    }
}
