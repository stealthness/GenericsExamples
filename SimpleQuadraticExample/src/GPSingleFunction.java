import lombok.Data;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * GPBiFunction wraps a BiFunction<Double, Double, Double> in a container. Together with fields functionName and clojureString
 */
@Data
public class GPSingleFunction implements Function<Double, Double> {

    private final Function<Double, Double> function;
    private final String functionName;
    private final String clojureString;


    @Override
    public Double apply(Double a) {
        return function.apply(a);
    }


}
