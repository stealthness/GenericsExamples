import lombok.Builder;
import lombok.Data;


/**
 * The Individual class contains the
 */
@Data
@Builder
public class Individual implements Node,Comparable{

    Node root;
    Double fitness;


    /**
     * Returns the size of  the root tree
     * @return root size
     */
    public int size(){
        return root.size();
    }

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

    public int maxDepth() {
        return root.getDepth();
    }
}
