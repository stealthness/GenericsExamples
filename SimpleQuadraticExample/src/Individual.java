import lombok.Data;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.DoubleStream;

/**
 * The Individual class contains the
 */
@Data
public class Individual implements Node,Comparable{


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
    public int compareTo(Object o) {
        return 0;
    }
}
