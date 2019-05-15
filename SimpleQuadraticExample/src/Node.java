import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.BiFunction;

@Data
public class Node {

    private BiFunction<Double,Double, Double> lamda;
    private double value;
    private Node Node1;
    private Node Node2;

    public Node(double value) {
        setValue(value);
    }

    public Double get(){

        return getValue();
    }

}
