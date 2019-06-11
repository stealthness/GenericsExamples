import lombok.Data;

import java.util.*;

@Data
public class FunctionNode implements Node,Comparable<FunctionNode>{

    GPFunction function;
    List<Node> subNodes;
    int maxSubNodes = -1;

    FunctionNode(GPFunction function){
        this.function = function;
        subNodes = new ArrayList<>();
        setMaxSubNodes(function.getMaxSubNodes());
    }

    FunctionNode(GPFunction function, Node node){
        this.function = function;
        subNodes = Collections.singletonList(node.clone());
        setMaxSubNodes(function.getMaxSubNodes());
    }

    FunctionNode(GPFunction function, List<Node> nodes){
        this.function = function;
        if (subNodes == null){
            subNodes = new ArrayList<>();
        }
        nodes.forEach(subNode -> subNodes.add(subNode.clone()));
        setMaxSubNodes(function.getMaxSubNodes());

    }


    Optional<Node> getSubNode(int index){
        if (subNodes== null || index > subNodes.size()){
            return Optional.empty();
        }
        return Optional.of(subNodes.get(0));
    }

    void setSubNode(Node subNode){
        if (subNodes == null){
            subNodes = new ArrayList<>();
        }
        if (subNodes.size() < function.getMaxSubNodes()){
            subNodes.add(subNode.clone());
        }
    }

    @Override
    public int size(){
        return 1 + subNodes.stream().mapToInt(Node::size).sum();
    }

    @Override
    public int getDepth(){
        return 1 + subNodes.stream().mapToInt(Node::getDepth).max().getAsInt() ;
    }

    @Override
    public Double calculate(Double[] inputs) {
        return function.apply(inputs,subNodes);
    }

    @Override
    public String print() {
        var sb = new StringBuilder();
        sb.append(String.format("(%s", function.toClojureString()));
        subNodes.forEach(subNode-> sb.append(String.format(" %s",subNode.print())));
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


    /**
     * Set 
     * @param index
     * @param subNode
     */
    void setSubNodeAt(int index, Node subNode){
        if (subNodes == null && index == 0){
            subNodes = new ArrayList<>();
            subNodes.add(subNode);
        } else if (size() > index){
            for (int i = 0 ; i< subNodes.size();i++) {
                int subNodeIndex = getSubNodeIndex(i);
                if (index == subNodeIndex){
                    subNodes.remove(i);
                    subNodes.add(i,subNode);
                    break;
                }else if (index < getSubNodeIndex(i) + subNodes.get(i).size()){
                    ((FunctionNode)subNodes.get(i)).setSubNodeAt(index - subNodeIndex, subNode);
                    break;
                }
            }
        } else{
            throw new IllegalArgumentException("index out of bounds");
        }
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
    
    private int getSubNodeIndex(int subNodeIndex){
        if (subNodes.size() < subNodeIndex || subNodes.size() == 0) {
            throw new IllegalArgumentException("Index out of bounds");
        }else{
            var sum  = 1 ;
            for (int i = 0; i<subNodeIndex;i++){
                sum += subNodes.get(i).size();
            }
            return sum;
        }
    }



}
