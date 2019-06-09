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
                }else if (index < subNodeIndex + subNodes.get(i).size()) {
//                    setSubNodeAt(index-subNodeIndex,subNode);
                }
            }
        } else{
            throw new IllegalArgumentException("index out of bounds");
        }
    }

    @Override
    public Optional<Node> getSubtree(int index){

        System.out.println(String.format("\n\nCalled getSubTree() with index %d",index));
        if (index == 0){
            return Optional.of(this);
        } else  if (index > 0 && index < this.size() ){
            for (int i = 0 ; i< subNodes.size();i++) {
                int subNodeIndex = getSubNodeIndex(i);
                if (index < subNodeIndex + subNodes.get(i).size()) {
                    Optional<Node> node = subNodes.get(i).getSubtree(index - subNodeIndex);
                    System.out.println(String.format("\n\nThis %s   :  index %d",this.print(),index));
                    System.out.println(String.format("subnode size %d   :  subNodeIndex %d  :  subNode(%d) : %s" , subNodes.get(i).size(), subNodeIndex ,i,subNodes.get(i).print()));
                    if (node.isEmpty()){
                        System.out.println("************************");
                    }else{

                        System.out.println(node.get().print());
                    }
                    return node;
                }
            }

        }
        System.out.println("\n\nSUPER BAD");
        System.out.println(String.format("This %s   :  index %d",this.print(),index));
        System.out.println("SUPER BAD");
        return Optional.empty();
    }
    
    int getSubNodeIndex(int subNodeIndex){
        if (subNodes.size() < subNodeIndex) {
            throw new IllegalArgumentException("Index out of bounds");
        }if (subNodes.size() == 0){
            return -1; // error
        } else if (subNodeIndex == 0){
            return 1;
        }else if (subNodeIndex == 1){
            return 1 + subNodes.get(0).size();
        } else{
            int count = 1;
            var sum  = 1 + subNodes.get(0).size();
            while ( count < this.size()){
                if (count++ == subNodeIndex){
                    return sum;
                }
                sum += subNodes.get(count).size();
            }
            return sum;
        }
    }



}
