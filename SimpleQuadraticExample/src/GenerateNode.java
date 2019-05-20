import java.util.List;

public class GenerateNode  {

    static List<GPFunction> setOfFunctions = GPUtils.getFunctionList("Basic");
    static List<Node> setOfTerminals = GPUtils.getTerminalsList("basic");

    static String generateMethod = "full";

    Node root;
    Node node1,node2;

    Node generateNode(int maxDepth){
        if (maxDepth > 1){
            node1 = generateNode(maxDepth-1);
            node2 = generateNode(maxDepth-1);
            root = new FunctionNode(setOfFunctions.get(0),node1,node2);
        }else{
            root = setOfTerminals.get(0);
        }
        return root;
    }

    Node getRoot(){
        return root;
    };
}
