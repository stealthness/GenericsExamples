public interface Node{

    /**
     * values of the inputs to evaluate any express
     * @param inputs
     * @return
     */
    Double get(Double[] inputs);

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
}
