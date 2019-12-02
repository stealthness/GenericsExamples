import lombok.Data;

@Data
/**
 * Epherial Node will create aTerminal Node with in a given
 */
public class EphemeralNode implements Node {

    private final Double[] range;
    private String method = "uniform";

    @Override
    public Double calculate(Double[] inputs) {
        // should be never called
        return null;
    }

    @Override
    public String toClojureString() {
        return "R[" + range[0] + "," + range[1] + "]";
    }

    @Override
    public Node clone() {
        return switch (method){
            case "uniform" -> new TerminalNode(Math.random()*(range[0]+range[1])-range[0]);
            default -> null;
        };
    }
}
