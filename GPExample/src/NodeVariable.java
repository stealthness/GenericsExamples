import lombok.Data;

/**
 *
 */
@Data
public class NodeVariable implements Node<Double[],Double>{

    /**
     * Variable node index
     */
    private final int index;

    @Override
    public Double calculate(Double[] inputs) {
        return inputs[index];
    }

    @Override
    public String toClojureString() {
        return "x"+index;
    }

    @Override
    public Node clone() {
        return new NodeVariable(index);
    }
}
