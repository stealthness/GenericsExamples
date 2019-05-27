import lombok.Data;

@Data
public class TerminalNode implements Node {

    final Double value;

    @Override
    public int size(){
        return Node.super.size();
    }

    @Override
    public int getDepth(){
        return Node.super.getDepth();
    }

    @Override
    public Double calculate(Double[] inputs) {
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
