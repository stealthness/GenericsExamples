import java.util.List;

/**
 * The interface of GPFunction
 */
interface GPFunction {

    /**
     * Applies the the function using inputs
     * @param inputs
     * @param nodes
     * @return
     */
    Double apply(Double[] inputs, List<Node> nodes);

    /**
     * Returns the clojure string of the function
     * @return
     */
    String toClojureString();

    /**
     * returns the maximum number of subnodes.
     * @return
     */
    int getMaxSubNodes();

}
