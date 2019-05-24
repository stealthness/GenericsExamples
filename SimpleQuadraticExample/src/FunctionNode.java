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
            subNodes.add(subNode);
        }
    }

    @Override
    public Double apply(double[] inputs) {
        return null;
    }

    @Override
    public String print() {
        return null;
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
