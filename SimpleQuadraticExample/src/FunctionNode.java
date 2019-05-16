import lombok.Data;

import java.util.Optional;
import java.util.function.BiFunction;

@Data
public class FunctionNode implements Node {

    private final BiFunction<Double,Double,Double> lambda;
    private final String functionString;
    Node node1;
    Node node2;

    public FunctionNode(BiFunction<Double, Double, Double>lambda, String functionString, Node node1, Node node2) {
        this.lambda = lambda;
        this.functionString = functionString;
        setNode1(node1);
        setNode2(node2);
    }

    public FunctionNode(BiFunction<Double, Double, Double> lambda, String functionString) {
        this.lambda = lambda;
        this.functionString = functionString;
    }

    public Node getSubtree(int index){
        return (index == 0)? node1:node2;
    }


    @Override
    public Double get(double[] inputs) {
        return lambda.apply(node1.get(inputs),node2.get(inputs));
    }

    @Override
    public String print() {
        return "(" + functionString + " " + ((node1 == null)?"null":node1.print()) + " " + ((node1 == null)?"null":node2.print()) + ")";
    }
}
