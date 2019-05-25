import lombok.Builder;
import lombok.Data;


/**
 * The Individual class contains the
 */
@Data
@Builder
public class Individual implements Node,Comparable{

    Node root;


    @Override
    public Double calculate(Double[] inputs) {
        return root.calculate(inputs);
    }

    @Override
    public String print() {
        return null;
    }

    @Override
    public Node clone() {
        return null;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }
}
