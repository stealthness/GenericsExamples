import lombok.Builder;

public interface PrimitiveSetInterface {

    /**
     * Adds terminal Node to the Primitive Set
     * @param node
     */
    void add(Node node);

    /**
     * Adds tfunction Node to the Primitive Set
     * @param node
     */
    void add(GPFunction node);

    /**
     * Returns the size, number of terminal and function Nodes, contain in the object
     * @return the size of the set
     */
    int size();
}
