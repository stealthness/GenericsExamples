
import lombok.Data;

import java.util.List;
import java.util.function.BiFunction;

@Data
public class GPSingleFunction implements GPFunction{



    private BiFunction<Double[], Node, Double> function;


    GPSingleFunction(BiFunction<Double[], Node, Double> function){
        this.function = function;
    }


    @Override
    public Double apply(Double[] inputs, List<Node> nodes) {
        return nodes.get(0).get(inputs);
    }

    @Override
    public int getMaxSubNodes() {
        return 1;
    }
}
