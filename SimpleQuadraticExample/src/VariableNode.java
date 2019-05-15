import lombok.Data;

/**
 * Variable Node will return the index value of the inputs
 */
@Data
public class VariableNode implements Node {

    private final int index;

    @Override
    public Double get(double[] inputs) {
        return inputs[index];
    }

    @Override
    public String print() {
        return "x"+index;
    }
}
