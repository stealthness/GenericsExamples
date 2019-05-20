import lombok.Data;

@Data
public class FunctionNode implements Node,Comparable<FunctionNode> {

    /**
     * Function that the node will apply to the results from its two child nodes
     */
    private final GPFunction function;
    Node node1;
    Node node2;

    public FunctionNode(GPFunction function, Node node1, Node node2) {
        this.function = function;
        setNode1(node1);
        setNode2(node2);
    }

    public FunctionNode(GPFunction function) {
        this.function = function;
    }

    public Node getSubtree(int index){
        return (index == 0)? node1:node2;
    }

    Node select(int nodeSelection){
        switch(nodeSelection){
            case 0 : return this;
            case 1 :return node1;
            default :
                if (nodeSelection < node2.size()) {
                    // already established that must be Function Node otherwise would be selection 1
                    return ((FunctionNode)node1).select(nodeSelection-1);
                }else if (nodeSelection == node2.size()){
                        return node2;
                } else if (nodeSelection <= this.size()){
                    return ((FunctionNode)node1).select(nodeSelection - node1.size() -1);
                }
                throw new IllegalArgumentException("invalid selection");
        }
    }

    @Override
    public int size() {
        return 1 + node1.size()+node2.size();
    }

    @Override
    public int getDepth() {
        return 1 + Math.max(node1.getDepth(), node2.getDepth());
    }

    @Override
    public Double get(double[] inputs) {
        return function.apply(node1.get(inputs),node2.get(inputs));
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
