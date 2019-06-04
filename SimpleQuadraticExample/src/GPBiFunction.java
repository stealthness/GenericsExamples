
import lombok.Data;

import java.util.List;
import java.util.function.BiFunction;

/**
 * GPBiFunction implements GPFunction interface. The class contains function that will calculate for method apply, and clojure
 * string representation, the class is similar to GPMultiFunction, but the number of sub nodes is set to 2.
 */
@Data
public class GPBiFunction implements GPFunction{


    /**
     * Contains a function that will apply a function to a List of nodes, which will be two, and return a Double value
     */
    private BiFunction<Double[], List<Node>, Double> function;

    /**
     * The clojure string representation of the function
     */
    private String clojureString;


    GPBiFunction(BiFunction<Double[], List<Node>, Double> function,String clojureString){
        this.function = function;
        this.clojureString = clojureString;
    }

    GPBiFunction(BiFunction<Double[], List<Node>, Double> function){
        this.function = function;
    }


    @Override
    public Double apply(Double[] inputs, List<Node> nodes) {

        return function.apply(inputs,nodes);
    }

    @Override
    public String toClojureString() {
        return clojureString;
    }

    /**
     * Returns the maximum sub nodes, which is 2
     * @return the value of 2
     */
    @Override
    public int getMaxSubNodes() {
        return 2;
    }
}
