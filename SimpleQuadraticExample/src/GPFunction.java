import java.util.List;

interface GPFunction {

    Double apply(Double[] inputs, List<Node> nodes);

    /**
     * Returns a clojure String representation of the function
     * @return a string representation of the function
     */
    String toClojureString();

    /**
     * Returns the the maximum number of sub-nodes that a function, default is -1 indicating no maximum.
     * @return max number of sub-nodes
     */
    default int getMaxSubNodes(){
        return -1;
    };

    /**
     * Returns the the minimum number of sub-nodes that a function, default is 1 node
     * @return min number of sub-nodes
     */
    default int getMinSubNodes(){
        return 1;
    }

}
