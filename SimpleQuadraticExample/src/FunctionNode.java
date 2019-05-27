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



    FunctionNode(GPFunction function, Node node){
        this.function = function;
        subNodes = Arrays.asList(node.clone());
    }

    FunctionNode(GPFunction function, List<Node> nodes){
        this.function = function;
        nodes.forEach(subNode -> {
            subNodes = Arrays.asList(subNode.clone());
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
    public Double calculate(Double[] inputs) {
        return function.apply(inputs,subNodes);
    }

    @Override
    public String print() {
        return String.format("(%s)", function.toClojureString());
    }

    @Override
    public Node clone() {
        return null;
    }

    @Override
    public int compareTo(FunctionNode o) {
        return 0;
    }

}
