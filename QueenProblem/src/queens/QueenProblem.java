package queens;

import lombok.Data;

import java.util.Arrays;

/**
 * Created by Stephen West on 19/04/2019.
 */
@Data
public class QueenProblem {
    Board board;

    final public static int DEFAULT_SIZE = 8;
    int[] positions;


    public QueenProblem(String string){
        this.positions = new int[DEFAULT_SIZE];
        board = new Board();
    }

    public QueenProblem(int[] positions){
        this.positions = positions;
        board = new Board();
    }


    public int getFitness(){

        return board.getTotalBoardValue();
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        Arrays.stream(positions).forEach(sb::append);
        return sb.toString();
    }
}
