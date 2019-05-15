import lombok.Data;

import java.util.function.BiFunction;

@Data
public class FunctionNode implements Node {

    private final BiFunction<Double,Double,Double> lambda;
    private final String functionString;
    Node node1;
    Node node2;



    @Override
    public Double get(double[] inputs) {
        return null;
    }

    @Override
    public String print() {

        return "( " + node1.print()+" "+ functionString + " " + node2.print() + " )";
    }
}
