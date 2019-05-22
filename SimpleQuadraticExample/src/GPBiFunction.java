import lombok.Data;

import java.util.function.BiFunction;

/**
 * GPBiFunction wraps a BiFunction<Double, Double, Double> in a container. Together with fields functionName and clojureString
 */
@Data
public class GPBiFunction implements BiFunction<Double, Double, Double> {

    private final BiFunction<Double, Double, Double> function;
    private final String functionName;
    private final String clojureString;


    @Override
    public Double apply(Double a, Double b) {
        return function.apply(a,b);
    }


}
