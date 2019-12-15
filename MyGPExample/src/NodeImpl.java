import lombok.Data;
import lombok.NoArgsConstructor;

@Data
public class NodeImpl implements Node {


    boolean isTerminal = false;


    public NodeImpl(){
    }

    public NodeImpl(boolean isTerminal){
        this.isTerminal = isTerminal;
    }


    @Override
    public boolean isTerminalNode() {
        return  isTerminal;
    }

    @Override
    public Double calculate(Double[] inputs) {
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
