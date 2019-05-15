import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.BiFunction;

@Data
public class Node {

    private BiFunction<Double,Double, Double> lambda;
    private double value;
    private Node Node1;
    private Node Node2;
    private int indexOfInput;

    public Node(double value) {
        setValue(value);
    }

    public Double get(double[] inputs){
        return getValue();
    }

    public Double get(){
        return get(null);
    }

}
