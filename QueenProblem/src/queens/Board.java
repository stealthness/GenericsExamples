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

                // position (row, col) = 0 if not a queen, 1 if a queen
                int count = board[row][col];

                // rows
                for (int i = 0; i<MAX_BOARD_ROWS;i++) {
                    if (board[i][col]==1 && row != i){
                        count++;
                    }
                }
                // cols
                for (int i = 0; i<MAX_BOARD_COLS;i++) {
                    if (board[row][i]==1 && col != i){
                        count++;
                    }
                }
                // diagonal
                int d1 = row+col;
                int d2 = MAX_BOARD_ROWS-row-1+col;
                for (int i = 0; i<MAX_BOARD_COLS;i++) {
                    for (int j = 0; j < MAX_BOARD_ROWS; j++){
                        //  this is for down diagonal (\)
                        if (board[i][j]==1 && i+j == d1 && ( i != row || j != col)){
                            count++;
                        }
                        // this is for up diagonal (/)
                        if (board[i][j]==1 && MAX_BOARD_ROWS-i-1+j == d2 && ( i != row || j != col)){
                            count++;
                        }
                    }

                }

                boardValue[row][col]=count;
            });
        });
        return boardValue;
    }

    public String printBoardValue(){
        int[][] boardValue = getBoardValue();
        var sb = new StringBuilder();
        IntStream.range(0,MAX_BOARD_ROWS).forEach(row ->{
            IntStream.range(0,MAX_BOARD_COLS).forEach(col ->{
                sb.append(boardValue[MAX_BOARD_ROWS-row-1][col]);
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

    public int getTotalBoardValue() {
        int[][] boardValue = getBoardValue();
        int sum = 0;
        for (int i = 0; i<MAX_BOARD_ROWS;i++){
            for (int j = 0; j<MAX_BOARD_COLS;j++){
                sum = +boardValue[i][j];
            }
        }

        return Math.abs(64 - sum);
    }
}
