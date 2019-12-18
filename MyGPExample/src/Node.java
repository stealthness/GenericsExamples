/**
 * Interface Node provide the...
 */
public interface Node {

    /**
     * If Node has an empty list list of sub-nodes and only returns variable or constant node
     * @return true if node is a terminal, false otherwise
     */
    boolean isTerminalNode();

    /**
     * values of the inputs to evaluate any express
     * @param inputs values of any variables
     * @return the calculated value
     */
    Double calculate(Double[] inputs);

    /**
     * toClojureString the the node express in LISP form
     * @return node clojure string form
     */
    String toClojureString();

    /**
     * the size of the tree, which is count of the number of the nodes.
     *
     * @return tree size
     */
    default int size() {
        return 1;
    }

    /**
     * Returns the depth of the tree
     * @return tree depth
     */
    default int getDepth(){
        return 1;
    }

    Node clone();

    /**
     * returns the subtree at index
     * @param index of the sub node tree to return
     * @return subtree node at index
     */
    Node getSubtree(int index);
}
