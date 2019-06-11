import java.util.Optional;

/**
 * Interface Node provide the...
 */
public interface Node{

    /**
     * values of the inputs to evaluate any express
     * @param inputs
     * @return
     */
    Double calculate(Double[] inputs);

    /**
     * toClojureString the the node express in LISP form
     * @return
     */
    String toClojureString();

    /**
     * the size of the tree, which is count of the number of the nodes.
     *
     * @return
     */
    default int size() {
        return 1;
    }

    /**
     * Returns the depth of the tree
     * @return
     */
    default int getDepth(){
        return 0;
    }

    Node clone();

    /**
     * returns the subtree at index
     * @param index
     * @return subtree node at index
     */
    default Optional<Node> getSubtree(int index){
        return  (index == 0)?Optional.of(this):Optional.empty();
    }
}
