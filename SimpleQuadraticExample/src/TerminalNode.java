import lombok.Data;

@Data
public class TerminalNode implements Node {

    private static final double TOL = 0.0000001;
    final Double value;

    @Override
    public Double get(double[] inputs) {
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

    @Override
    public boolean equals(Object that){
        return that.getClass().equals(TerminalNode.class) && Math.abs(this.getValue()  - ((TerminalNode)that).getValue()) < TOL;
    }


}
