
import lombok.Data;

import java.util.List;
import java.util.function.BiFunction;

@Data
public class GPMultiFunction implements GPFunction{



    private BiFunction<Double[], List<Node>, Double> function;


    GPMultiFunction(BiFunction<Double[], List<Node>, Double> function){
        this.function = function;
    }


    @Override
    public Double apply(Double[] inputs, List<Node> nodes) {


        return function.apply(inputs,nodes);
    }

    @Override
    public int getMaxSubNodes() {
        return 1;
    }
}
