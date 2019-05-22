import lombok.Data;

@Data
public class TerminalNode implements Node {

    private static final double TOL = 0.0000001;
    final Double value;

    @Override
    public Double apply(double[] inputs) {
        return value;
    }

    @Override
    public String print() {
        return value.toString();
    }

    @Override
    public Node clone() {
        return new TerminalNode(value);
    }

}
