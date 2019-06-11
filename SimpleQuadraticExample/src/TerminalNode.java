import lombok.Data;

/**
 * TerminalNode class is representation of a leaf node on the tree program. The Terminal node will return a constant
 * value create at initialisation.
 */
@Data
public class TerminalNode implements Node {

    final Double value;

    /**
     * returns the default size of 1
     * @return the value of 1
     */
    @Override
    public int size(){
        return Node.super.size();
    }

    /**
     * Returns the default value of 0. A leaf node has no depth.
     * @return the value of 0
     */
    @Override
    public int getDepth(){
        return Node.super.getDepth();
    }

    /**
     * Given an input (which is not used), calculates the return value, which is the value on the TerminalNode.
     * @param inputs not needed for TerminalNode
     * @return value
     */
    @Override
    public Double calculate(Double[] inputs) {
        return value;
    }

    /**
     * Returns a string of the TerminalNode in clojure string form
     * @return value as a string
     */
    @Override
    public String toTreeString() {
        return value.toString();
    }

    /**
     * Clones the TerminalNode
     * @return a new TerminalNode with same value
     */
    @Override
    public Node clone() {
        return new TerminalNode(value);
    }

}
