import java.util.Optional;

public interface Node{

    /**
     * values of the inputs to evaluate any express
     * @param inputs
     * @return
     */
    Double calculate(Double[] inputs);

    /**
     * print the the node express in LISP form
     * @return
     */
    String print();

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
        System.out.println("super");
        return  (index == 0)?Optional.of(this):Optional.empty();
    }
}
