import java.util.ArrayList;
import java.util.List;

/**
 * The Primitive class contains list of function and terminal nodes.
 */
public class Primitive implements PrimitiveInterface{

    List<Node> terminalNodes;
    List<Node> functionNodes;

    public Primitive() {
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
}
