import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Data
public class FunctionNode implements Node,Comparable<FunctionNode> {

    /**
     * Function that the node will apply to the results from its two child nodes
     */
    private final GPFunction function;
    /**
     * List of Nodes to be used as inputs for the function
     */
    List<Node> nodes;

    FunctionNode(GPFunction function, Node node0, Node node1) {
        this(function,Arrays.asList(node0, node1));
    }

    FunctionNode(GPFunction function, List<Node> nodes) {
        this.function = function;
        this.nodes = new ArrayList<>();
        this.nodes.addAll(nodes);
    }

    /**
     * Returns the subtree at node1 or node2
     * @param index of the node to get
     * @return
     */
    Node getNode(int index){
        return nodes.get(index);
    }

    /**
     * Sets the node at index to the subtree. node1 is index 0, node2 is index 1.
     * @param index
     * @param subtree
     */
    void setNode(int index,final Node subtree){
        nodes.remove(index);
        nodes.add(index,subtree.clone());
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
                if (nodeIndex == getNode(0).size()+1){
                    return getNode(1);
                } else if (nodeIndex <= getNode(0).size()) {
                    if (getNode(0).size()==1){
                        return getNode(0);
                    }else{
                        return ((FunctionNode)getNode(0)).getSubtree(nodeIndex - 1);
                    }
                } else if (nodeIndex < this.size()){
                    if (getNode(1).size()==1){
                        return getNode(1);
                    }else{
                        return ((FunctionNode)getNode(1)).getSubtree(nodeIndex - getNode(0).size()-1);
                    }
                }
                throw new IllegalArgumentException("invalid selection");
        }
    }

    /**
     * Changes the subtree at index with new subTree. (root cannot be change only subtrees)
     * @param index the index at which to place the new subTree
     * @param subTree
     */
    void changeSubtreeAt(int index, Node subTree) {
        int node0Size = getNode(0).size();
        if (index < 1){
            throw new IllegalArgumentException("1 <= index must < nodes(0).size()");
        } else if (index == 1){
            setNode(0,subTree);
        } else if (index == node0Size+1){
            setNode(1,subTree);
        } else if (index <= node0Size){
            // assume its functionalNode
            ((FunctionNode)getNode(0)).changeSubtreeAt(index-1,subTree);
        } else if (index < this.size()){
            ((FunctionNode)getNode(1)).changeSubtreeAt(index-1-getNode(0).size(),subTree);
        } else {
            throw new IllegalArgumentException("index too large < this.size()");
        }
    }

    // Override methods

    @Override
    public String print() {
        return "(" + function.getClojureString() + " " + getNode(0).print()
                + " " + getNode(1).print() + ")";
    }

    /**
     * Returns the size of the Individual which is count of the number of nodes
     * @return the number of Nodes contained in the root
     */
    @Override
    public int size() {
        return 1 + getNode(0).size() + getNode(1).size();
    }

    /**
     * Returns the greatest depth of the root, where root is 0.
     * @return the greatest depth of the root tree
     */
    @Override
    public int getDepth() {
        return 1 + Math.max(getNode(0).getDepth(), getNode(1).getDepth());
    }


    @Override
    public Double apply(double[] inputs) {
        return function.apply(getNode(0).apply(inputs),getNode(1).apply(inputs));
    }

    @Override
    public int compareTo(FunctionNode that) {
        if (this.size() != that.size()){
            return Integer.compare(this.size(),that.size());
        } else{
            return Integer.compare(this.getDepth(), that.getDepth());
        }
    }

    @Override
    public Node clone() {
        return new FunctionNode(getFunction(),getNode(0).clone(), getNode(1).clone());
    }


    public void reduce(double reductionRate) {
        for (int index = 0;index <2;index++){
            Node node = getNode(index);
            if (node.size() == 3){
                Node newNode = GPUtils.reduceRules.apply(node);
                if (newNode != node && Math.random()<reductionRate){
                    setNode(index,newNode);
                }
            } else if (getNode(index).size()>3){
                ((FunctionNode)node).reduce(reductionRate);
            }
        }
    }
}
