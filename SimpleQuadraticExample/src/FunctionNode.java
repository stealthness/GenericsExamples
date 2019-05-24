import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

@Data
public class FunctionNode implements Node,Comparable<FunctionNode> {


    @Override
    public Double apply(double[] inputs) {
        return null;
    }

    @Override
    public String print() {
        return null;
    }

    @Override
    public Node clone() {
        return null;
    }

    @Override
    public int compareTo(FunctionNode o) {
        return 0;
    }
}
