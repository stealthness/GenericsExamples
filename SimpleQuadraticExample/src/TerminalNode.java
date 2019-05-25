import lombok.Data;

@Data
public class TerminalNode implements Node {

    final Double value;

    @Override
    public Double get(Double[] inputs) {
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
