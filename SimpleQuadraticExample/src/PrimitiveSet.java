import org.w3c.dom.ls.LSOutput;

import java.util.ArrayList;
import java.util.List;



/**
 * The Primitive class contains list of function and terminal nodes.
 */
public class PrimitiveSet implements PrimitiveSetInterface{
    String NEWLINE = "\n";
    List<Node> terminalNodes;
    List<GPFunction>  functionNodes;

    public PrimitiveSet() {
        terminalNodes = new ArrayList<>();
        functionNodes = new ArrayList<>();
    }

    @Override
    public void add(Node node) {
            terminalNodes.add(node);
    }

    @Override
    public void add(GPFunction function) {
        functionNodes.add(function);
    }

    @Override
    public int size() {
        return terminalNodes.size() + functionNodes.size();
    }

    @Override
    public Node getNode() {
        if (this.isEmpty()){
            return null;
        }else{
            int pick = 0;
            if (terminalNodes.size() > 0 ) {
                return terminalNodes.get(0);
            }else{
                return new FunctionNode(functionNodes.get(pick - terminalNodes.size()),null);
            }

        }

    }

    private boolean isEmpty() {
        return terminalNodes.isEmpty() && functionNodes.isEmpty();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        terminalNodes.stream().forEach(node -> sb.append(node + NEWLINE));
        functionNodes.stream().forEach(node -> sb.append(node + NEWLINE));

        return sb.toString();
    }
}
