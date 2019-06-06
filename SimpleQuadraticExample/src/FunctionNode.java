import lombok.Data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.DoubleStream;

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
        subNodes = Arrays.asList(node.clone());
        setMaxSubNodes(function.getMaxSubNodes());
    }

    FunctionNode(GPFunction function, List<Node> nodes){
        this.function = function;
        if (subNodes == null){
            subNodes = new ArrayList<>();
        }
        nodes.forEach(subNode -> {
            subNodes.add(subNode.clone());
        });

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
        subNodes.stream().forEach(node ->{
            subNodeList.add(node.clone());
        });
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
            int count = 1;
            for ( int i = 0; i< subNodes.size();i++){
                if (index < count + subNodes.get(i).size()){
                    return Optional.of(subNodes.get(index - count));
                }
                count += subNodes.get(i).size();
            }

        }
        return Optional.empty();
    }

}
