import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;

@Data
public class FunctionNodeImpl implements Node {


    List<Node> nodes;
    BiFunction<Double[], List<Node>, Double> function;
    String functionString;

    boolean isTerminal = false;


    public FunctionNodeImpl(){
        nodes = new ArrayList<>();
    }

    public FunctionNodeImpl (BiFunction<Double[], List<Node>, Double> function, String functionString, List<Node> subNodes){
        super();
        if (subNodes != null){
            nodes = subNodes;
        }
        this.functionString = functionString;
        this.function = function;
    }


    @Override
    public boolean isTerminalNode() {
        return  false;
    }

    @Override
    public Double calculate(Double[] inputs) {
        if (nodes == null){
            throw new NullPointerException("nodes is null");
        }else{
            return function.apply(inputs,nodes);
        }
    }

    @Override
    public String toClojureString() {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        sb.append(functionString);
        for (Node node : nodes){
            if (node.isTerminalNode()){
                sb.append(" " + node.toClojureString().replaceAll("[()]",""));
            }else{

                sb.append(" " + node.toClojureString());
            }
        }
        sb.append(")");
        return sb.toString();
    }

    @Override
    public Node clone() {
        return new FunctionNodeImpl();
    }

    @Override
    public Node getSubtree(int index) {
        if (index <0 || (nodes != null && index > nodes.size())){
            throw new IndexOutOfBoundsException("index: "+index);
        }else if(index == 0){
            return this;
        }else{
                for (int i = 0 ; i < nodes.size();i++) {
                    int subNodeIndex = getSubNodeIndex(i);
                    if (index < subNodeIndex + nodes.get(i).size()) {
                        return nodes.get(i).getSubtree(index - subNodeIndex);
                    }
                }

        }
        return null;
    }

    private int getSubNodeIndex(int subNodesIndex ) {
        if (nodes.size() < subNodesIndex || nodes.size() == 0) {
            throw new IndexOutOfBoundsException("subNodeIndex : "+subNodesIndex);
        }else{
            var sum  = 1 ;
            for (int i = 0; i<subNodesIndex;i++){
                sum += nodes.get(i).size();
            }
            return sum;
        }
    }
}
