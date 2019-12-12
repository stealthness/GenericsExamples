import java.util.List;

public class GPFunction implements GPFunctionInterface<Double, Double[], List<Node<Double, Double[]>>> {


    Node root;

    @Override
    public List<Node<Double, Double[]>> apply(Double[] inputs, List<Double[]> nodes) {
        return root.calculate(inputs);
    }

    @Override
    public String toClojureString() {
        return "(to do)";
    }

    @Override
    public int getMaxSubNodes() {
        return -1;
    }
}
