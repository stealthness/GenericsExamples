import java.util.List;

/**
 *
 * @param <T> inputs
 * @param <N> nodes
 * @param <S> returned value
 */
interface GPFunctionInterface<T, N, S> {

    /**
     * Applies the inputs on the given nodes and returns a value
     * @param inputs the input values to be calculated against
     * @param nodes list of the nodes to used in the calculation
     * @return the calculated value
     */
    S apply(T[] inputs, List<N> nodes);

    /**
     * Constructs a string representation of the function
     * @return a clojure representation of the function
     */
    String toClojureString();

    /**
     * Returns the maximum number of nodes that GPFunction may use
     * @return the maximum number of nodes the function may use in calculation
     */
    int getMaxSubNodes();

}
