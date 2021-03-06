import lombok.Data;
import java.util.List;
import java.util.function.BiFunction;

@Data
/**
 * The class GPSingleFunction is used for GPFunctions that allow a maximum of one node
 *
 * example of single functions are : identity, abs, cos, sin,...
 */
public class GPSingleFunction implements GPFunction{

    private static final int MAX_NUMBER_SUB_NODES = 1;
    private final BiFunction<Double[], List<Node>, Double> function;
    private final String clojureString;


    GPSingleFunction(BiFunction<Double[], List<Node>, Double> function, String clojureString){
        this.function = function;
        this.clojureString = clojureString;
    }


    @Override
    public Double apply(Double[] inputs, List<Node> nodes) {
        return function.apply(inputs,nodes);
    }

    @Override
    public String toString(){
        return String.format("GPSingleFunction(%s)",this.clojureString);
    }

    @Override
    public String toClojureString() {
        return clojureString;
    }

    @Override
    public int getMaxSubNodes() {
        return MAX_NUMBER_SUB_NODES;
    }
}
