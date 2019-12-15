import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

@Data
public class NodeImpl implements Node {


    List<Node> nodes;
    BiFunction<Double[], List<Node>, Double> function;
    Double constant;

    boolean isTerminal = false;


    public NodeImpl(){
        nodes = new ArrayList<>();
    }

    public NodeImpl(boolean isTerminal,double constant){
        super();
        this.constant = constant;
        this.isTerminal = isTerminal;
    }


    @Override
    public boolean isTerminalNode() {
        return  isTerminal;
    }

    @Override
    public Double calculate(Double[] inputs) {
        if (isTerminal){
            return constant;
        }
        return null;
    }

    @Override
    public String toClojureString() {
        return null;
    }

    @Override
    public Node clone() {
        return null;
    }

    @Override
    public Node getSubtree(int index) {
        return null;
    }
}
