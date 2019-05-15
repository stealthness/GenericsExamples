import lombok.Data;

@Data
public class TerminalNode implements Node {

    final Double value;

    @Override
    public Double get(double[] inputs) {
        return value;
    }

    @Override
    public String print() {
        return value.toString();
    }
}
