import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.BiFunction;

@Data
public class Node {

    private BiFunction<Double,Double, Double> lambda;
    private double value;
    private Node Node1;
    private Node Node2;
    private int indexOfInput = -1;

    public Node(double value) {
        setValue(value);
    }

    public Node(int indexOfInput){
        setIndexOfInput(indexOfInput);
    }

    public Double get(double[] inputs){

        if (indexOfInput >=0){
            return inputs[indexOfInput];
        }else{
            return getValue();
        }

    }

    public Double get(){
        return get(null);
    }

}
