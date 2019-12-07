import java.util.ArrayList;
import java.util.List;

/**
 * The Primitive class contains list of function and terminal nodes.
 */
public class PrimitiveSet implements PrimitiveSetInterface{

    List<Node> terminalNodes;
    List<Node> functionNodes;

    public PrimitiveSet() {
        terminalNodes = new ArrayList<>();
        functionNodes = new ArrayList<>();
    }

    @Override
    public void add(Node node) {
        if (node.getClass() == TerminalNode.class){
            terminalNodes.add(node);
        }else{
            functionNodes.add(node);
        }
    }

    @Override
    public int size() {
        return terminalNodes.size() + functionNodes.size();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        terminalNodes.stream().forEach(node -> sb.append(node));
        functionNodes.stream().forEach(node -> sb.append(node));

        return sb.toString();
    }
}
