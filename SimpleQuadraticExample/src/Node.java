import java.util.Optional;

public interface Node {

    /**
     * values of the inputs to evaluate any express
     * @param inputs
     * @return
     */
    Double get(double[] inputs);

    /**
     * print the the node express in LISP form
     * @return
     */
    String print();
}
