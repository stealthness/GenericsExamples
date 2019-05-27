
import lombok.Data;

import java.util.List;
import java.util.function.BiFunction;

@Data
public class GPBiFunction implements GPFunction{



    private BiFunction<Double[], List<Node>, Double> function;
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

    @Override
    public int getMaxSubNodes() {
        return 2;
    }
}
