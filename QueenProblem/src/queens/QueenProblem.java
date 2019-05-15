package queens;

import lombok.Data;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Stephen West on 19/04/2019.
 */
@Data
public class QueenProblem {
    Board board;

    final public static int DEFAULT_SIZE = 8;
    int[] positions;


    public QueenProblem(String position){
        this.positions = new int[DEFAULT_SIZE];
        IntStream.range(0,DEFAULT_SIZE).forEach(i -> {
            if (i < DEFAULT_SIZE-1){
                this.positions[i] = Integer.parseInt(position.substring(i,i+1));
            }else{
                this.positions[i] = Integer.parseInt(position.substring(position.length()-1));
            }
        });
        board = new Board();
    }

    public QueenProblem(int[] positions){
        IntStream.range(0,DEFAULT_SIZE).forEach(i -> {
            this.positions[i] = positions[i];
        });
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

    /**
     * retruns true if the the board is valid set of queen position, that is 1 queen per row
     * @return
     */
    public boolean isValid() {
        return IntStream.range(0,DEFAULT_SIZE)
                .allMatch(i -> {
                    System.out.println(i + " " + this.toString());
                    return Arrays.stream(positions)
                            .filter(j -> i == j)
                            .count() == 1;
                });
    }
}
