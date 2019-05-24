import lombok.Data;

@Data
public class EphemeralNode implements Node {

    private final double[] range;

    @Override
    public Double apply(double[] inputs) {
        // should be never called
        return null;
    }

    @Override
    public String print() {
        return "R[" + range[0] + "," + range[1] + "]";
    }

    @Override
    public Node clone() {
        return new TerminalNode(Math.random()*(range[0]+range[1])-range[0]);
    }
}
