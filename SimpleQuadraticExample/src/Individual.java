import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;


/**
 * The Individual class contains the
 */
@Data
@Builder
public class Individual implements Node,Comparable{

    private static final String FULL = "full";
    private static final String GROW = "grow";
    private static final double FUNCTION_TERMINAL_SELECTION_RATIO = 1.0;
    Node root;
    Double fitness;


    /**
     * Returns the size of  the root tree
     * @return root size
     */
    public int size(){
        return root.size();
    }

    @Override
    public Double calculate(Double[] inputs) {
        return root.calculate(inputs);
    }

    @Override
    public String print() {
        if (root.getClass() != FunctionNode.class){
            return "("+root.print()+")";
        }
        return root.print();
    }

    @Override
    public Node clone() {
        return null;
    }

    @Override
    public int compareTo(Object o) {
        return 0;
    }

    public int maxDepth() {
        return root.getDepth();
    }


    /**
     * Returns a new Individual with randomly selected tree
     * @param terminalList
     * @param functionList
     * @param method
     * @param depth
     * @return
     */
    static Individual generate(List<Node> terminalList, List<GPFunction> functionList, String method, int depth){

        Node root;
        if (depth == 0){
            root = selectTerminalNode(terminalList);
        }else{
            root = generateNode(terminalList,functionList,method,depth);
        }
        return Individual.builder().root(root).build();
    }

    private static Node generateNode(List<Node> terminalList, List<GPFunction> functionList, String method, int depth){
        Node node = null;
        if (depth == 0){
            node = selectTerminalNode(terminalList);
        }else if (method.equals(FULL)){
            node = selectFunctionNode(functionList);
            for (int i = 0 ; i < Math.min(2, ((FunctionNode)node).getMaxSubNodes()); i++){
                ((FunctionNode)node).setSubNode(i, generateNode(terminalList,functionList,method,depth-1));
            }
        }else if (method.equals(GROW)){
            if (Math.random() < FUNCTION_TERMINAL_SELECTION_RATIO){
                node = selectTerminalNode(terminalList);
            }else{
                node = selectFunctionNode(functionList);
                for (int i = 0 ; i<Math.min(2, ((FunctionNode)node).getMaxSubNodes()); i++){
                    ((FunctionNode)node).setSubNode(i, generateNode(terminalList,functionList,method,depth-1));
                }
            }

        }
        return node;
    }


    private static FunctionNode selectFunctionNode(List<GPFunction>functionNodeList) {
        // standard select equal random bag
        int selection = new Random().nextInt(functionNodeList.size());
        return new FunctionNode(functionNodeList.get(selection));
    }

    private static Node selectTerminalNode(List<Node> terminalNodeList) {
        // standard select equal random bag
        int selection = new Random().nextInt(terminalNodeList.size());
        Node node = terminalNodeList.get(selection).clone();
        return node;
    }

}
