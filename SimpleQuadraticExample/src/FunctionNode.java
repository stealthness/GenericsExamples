import lombok.Data;

@Data
public class FunctionNode implements Node,Comparable<FunctionNode> {

    /**
     * Function that the node will apply to the results from its two child nodes
     */
    private final GPFunction function;
    Node node1;
    Node node2;

    FunctionNode(GPFunction function, Node node1, Node node2) {
        this.function = function;
        setNode1(node1);
        setNode2(node2);
    }

    FunctionNode(GPFunction function) {
        this.function = function;
    }

    /**
     * Returns the subtree at node1 or node2
     * @param index
     * @return
     */
    Node getNode(int index){
        return (index == 0)? node1:node2;
    }

    /**
     * Returns the selected node. The index of the selected node is counted via depth first search, with the root node
     * being index 0, node1 being index 1 and node2 being index node1.size()+1.
     * @param nodeIndex the index of the subtree to be returned.
     * @return subtree at nodeIndex.
     */
    Node getSubtree(int nodeIndex){
        switch(nodeIndex){
            case 0 : return this;
            case 1 : return getNode(0);
            default :
                if (nodeIndex < node2.size()) {
                    // already established that must be Function Node otherwise would be selection 1
                    return ((FunctionNode)node1).getSubtree(nodeIndex-1);
                }else if (nodeIndex == node2.size()){
                        return getNode(1);
                } else if (nodeIndex <= this.size()){
                    return ((FunctionNode)node1).getSubtree(nodeIndex - node1.size() -1);
                }
                throw new IllegalArgumentException("invalid selection");
        }
    }

    /**
     * Changes the subtree at index with new subTree
     * @param index
     * @param subTree
     */
    void changeSubtreeAt(int index, Node subTree) {
    }

    // Override methods

    @Override
    public int size() {
        return 1 + getNode(0).size() + getNode(1).size();
    }

    @Override
    public int getDepth() {
        return 1 + Math.max(getNode(0).getDepth(), getNode(1).getDepth());
    }

    @Override
    public Double get(double[] inputs) {
        return function.apply(getNode(0).get(inputs),getNode(1).get(inputs));
    }

    @Override
    public String print() {
        return "(" + function.getClojureString() + " " + ((node1 == null)?"null":node1.print()) + " " + ((node1 == null)?"null":node2.print()) + ")";
    }

    @Override
    public int compareTo(FunctionNode that) {
        if (this.size() != that.size()){
            return Integer.compare(this.size(),that.size());
        } else{
            return Integer.compare(this.getDepth(), that.getDepth());
        }
    }
}
