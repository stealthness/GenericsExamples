import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.function.BiFunction;

@Data
public class ExNode implements Node{

    private BiFunction<Double,Double, Double> lambda;
    private double value;
    private Node Node1;
    private Node Node2;
    private int indexOfInput = -1;

    public ExNode(double value) {
        setValue(value);
    }

    public ExNode(int indexOfInput){
        setIndexOfInput(indexOfInput);
    }

    public ExNode(BiFunction<Double, Double, Double> lambda, Node node1, Node node2) {
        setLambda(lambda);
        setNode1(node1);
        setNode2(node2);
    }

    public Double get(double[] inputs){

        if (indexOfInput >=0){
            return inputs[indexOfInput];
        }else if (lambda != null) {
            return lambda.apply(getNode1().get(inputs),getNode2().get(inputs));
        }else{
            return getValue();
        }

    }

    @Override
    public String print() {
        return null;
    }

    public Double get(){
        return get(null);
    }

}
