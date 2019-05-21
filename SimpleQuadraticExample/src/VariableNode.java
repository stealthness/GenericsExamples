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

    @Override
    public Node clone() {
        return new VariableNode(index);
    }

//    @Override
//    public boolean equals(Object that){
//        return that.getClass().equals(VariableNode.class) && this.getIndex() == ((VariableNode)that).getIndex();
//    }
}
