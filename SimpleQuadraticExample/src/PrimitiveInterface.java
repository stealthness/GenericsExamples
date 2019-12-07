import lombok.Builder;

public interface PrimitiveInterface {

    /**
     * Adds terminal or function Node to the Primitive Set
     * @param node
     */
    void add(Node node);

    /**
     * Returns the size, number of terminal and function Nodes, contain in the object
     * @return the size of the set
     */
    int size();
}
