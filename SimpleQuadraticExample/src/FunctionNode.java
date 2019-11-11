import lombok.Data;

import java.util.*;

@Data
public class FunctionNode implements Node,Comparable<FunctionNode>{

    GPFunction function;
    List<Node> subNodes;
    int maxSubNodes = -1;

    // Constructors

    FunctionNode(GPFunction function, List<Node> nodes){
        setFunction(function);
        subNodes = new ArrayList<>();
        if (nodes == null || nodes.isEmpty()){
            nodes.forEach(subNode -> subNodes.add(subNode.clone()));
            setMaxSubNodes(function.getMaxSubNodes());
        }


    }

    // methods

    Optional<Node> getSubNode(int index){
        if (subNodes== null || index > subNodes.size()){
            return Optional.empty();
        }
        return Optional.of(subNodes.get(0));
    }

    /**
     * Adds a subNode
     * @param subNode
     */
    void addSubNode(Node subNode){
        if (subNodes == null){
            subNodes = new ArrayList<>();
        }
        if (subNodes.size() < function.getMaxSubNodes()){
            subNodes.add(subNode.clone());
        }
    }

    /**
     * Replace the subtree at index with new subtree of node.
     * @param index of the subtree to replace.
     * @param newSubtree the new tree to be inserted.
     */
    void replaceSubtreeAt(int index, Node newSubtree){
        System.out.println(String.format("index: %d  newSubnode: %s",index,subNodes.toString()));
        if (subNodes == null && index == 0){
            subNodes = new ArrayList<>();
            subNodes.add(newSubtree);
        } else if (size() > index){
            for (int i = 0 ; i< subNodes.size();i++) {
                int subNodeIndex = getSubNodeIndex(i);
                if (index == subNodeIndex){
                    subNodes.remove(i);
                    subNodes.add(i,newSubtree);
                    break;
                }else if (index < getSubNodeIndex(i) + subNodes.get(i).size()){
                    ((FunctionNode)subNodes.get(i)).replaceSubtreeAt(index - subNodeIndex, newSubtree);
                    break;
                }
            }
        } else{
            throw new IllegalArgumentException("index out of bounds");
        }
    }

    // Override Methods

    @Override
    public int size(){
        return 1 + subNodes.stream().mapToInt(Node::size).sum();
    }

    @Override
    public int getDepth(){

        if (subNodes.size()==0){
            System.out.println(subNodes.toString());
            return 1;
        }
        return 1 + subNodes.stream().mapToInt(Node::getDepth).max().getAsInt() ;
    }

    @Override
    public Double calculate(Double[] inputs) {
        return function.apply(inputs,subNodes);
    }

    @Override
    public String toClojureString() {
        var sb = new StringBuilder();
        sb.append(String.format("(%s", function.toClojureString()));
        subNodes.forEach(subNode-> sb.append(String.format(" %s",subNode.toClojureString())));
        sb.append(")");
        return sb.toString();
    }

    @Override
    public Node clone() {
        var subNodeList = new ArrayList<Node>();
        subNodes.forEach(node -> subNodeList.add(node.clone()));
        return new FunctionNode(function,subNodeList);
    }

    @Override
    public int compareTo(FunctionNode o) {
        return 0;
    }

    @Override
    public Optional<Node> getSubtree(int index){
        if (index == 0){
            return Optional.of(this);
        } else  if (index > 0 && index < this.size() ){
            for (int i = 0 ; i < subNodes.size();i++) {
                int subNodeIndex = getSubNodeIndex(i);
                if (index < subNodeIndex + subNodes.get(i).size()) {
                    return subNodes.get(i).getSubtree(index - subNodeIndex);
                }
            }
        }
        return Optional.empty();
    }

    // private helper methods

    /**
     * Returns the tree index of the subNodes at the position of subNodesIndex
     * @param subNodesIndex the index of the subNodes
     * @return the tree index of the subNodes at index subNodesIndex
     */
    private int getSubNodeIndex(int subNodesIndex){
        if (subNodes.size() < subNodesIndex || subNodes.size() == 0) {
            throw new IllegalArgumentException("Index out of bounds");
        }else{
            var sum  = 1 ;
            for (int i = 0; i<subNodesIndex;i++){
                sum += subNodes.get(i).size();
            }
            return sum;
        }
    }

}
