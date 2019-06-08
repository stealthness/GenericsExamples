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

    }


    Optional<Node> getSubNode(int index){
        if (subNodes== null || index > subNodes.size()){
            return Optional.empty();
        }
        return Optional.of(subNodes.get(0));
    }

    void setSubNode(int index, Node subNode){
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



    @Override
    public Optional<Node> getSubtree(int index){
        int count;
        if (index == 0){
            return Optional.of(this);
        } else  if (index > 0 && index < this.size() ){

            for (int i = 0 ; i< subNodes.size();i++) {

                int subNodeIndex = getSubNodeIndex(i);
//                System.out.println("i : " + i);
//                System.out.println("subNode is " + subNodes.get(i).print());
//                System.out.println("subNode size : "+ subNodes.get(i).size());
//                System.out.println("getSubNodeIndex : " + subNodeIndex);
//                System.out.println(index <= subNodeIndex + subNodes.get(i).size());
                if (index < subNodeIndex + subNodes.get(i).size()) {
                    System.out.println(subNodes.get(i).getSubtree(index - subNodeIndex).get().print());
                    return Optional.of(subNodes.get(i).getSubtree(index - subNodeIndex).get());
                }
            }

        }
        return Optional.empty();
    }
    
    int getSubNodeIndex(int subNodeIndex){
        int count = 0;
        var sum  = 1;
        while ( count < this.size()){
            if (count++ == subNodeIndex){
                return sum;
            }
            sum += subNodes.get(count).size();
        }
        return sum;
    }



}
