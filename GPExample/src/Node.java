import java.util.Optional;

/**
 * Interface Node provide the...
 * @param <T> input
 * @param <S> returned value type
 */
public interface Node<T, S> {

    /**
     * values of the inputs to evaluate any express
     * @param inputs the input to evaluated
     * @return the calculated value
     */
    S calculate(T inputs);

    /**
     * toClojureString the the node express in LISP form
     * @return a clojure string representation
     */
    String toClojureString();

    /**
     * the size of the tree, which is count of the number of the nodes.
     *
     * @return size of the the Node
     */
    default int size() {
        return 1;
    }

    /**
     * Returns the depth of the tree, which is the maximum depth of of any subtree
     * @return Returns the depth of the tree
     */
    default int getDepth(){
        return 0;
    }

    Node clone();

    /**
     * returns the subtree at index
     * @param index the index of the subtree
     * @return subtree node at index
     */
    default Optional<Node> getSubtree(int index){
        return  (index == 0)?Optional.of(this):Optional.empty();
    }
}
