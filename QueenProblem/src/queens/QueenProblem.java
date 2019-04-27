package queens;

import lombok.Data;

/**
 * Created by Stephen West on 19/04/2019.
 */
@Data
public class QueenProblem {
    final public static int DEFAULT_SIZE = 8;
    int[] positions;


    public QueenProblem(String string){
        this.positions = new int[DEFAULT_SIZE];
    }

    public QueenProblem(int[] positions){
        this.positions = positions;
    }


}
