import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class NodeUtils {


    private static final String FULL = "full";
    private static final String GROW = "grow";
    private static final double FUNCTION_TERMINAL_SELECTION_RATIO = 0.5;

    static Node generateFullTree(List<FunctionNode> functionNodeList, List<Node> leafNodeList, int maxDepth){
        Node root;
        if (maxDepth >0){
            root = functionNodeList.get(new Random().nextInt(functionNodeList.size())).clone();
            if (root.getClass() == FunctionNode.class){
                ((FunctionNode)root).addSubNode(generateFullTree(functionNodeList,leafNodeList,maxDepth-1));
            }
        }else{
            root = leafNodeList.get(new Random().nextInt(leafNodeList.size()));
        }
        return root;
    }

    static Node createNodeFromString(String string){
        System.out.println(string);
        List<String> strings = Arrays.asList(string.split(" "));
        if (strings.size()==1){
            System.out.println("<1>");
            return getTerminalNode(strings.get(0));
        } else {

            System.out.println("<2+>");
            if (string.chars().filter(ch -> ch == '(').count() >1){
                System.out.println("<open>");
                List<String> newString = new ArrayList<>();
                newString.add(strings.get(0));
                for (int i = 1  ; i < strings.size(); i++){
                    if (strings.get(i).contains("(")){
                        String openString = strings.get(i);
                        while(isStillOpen(openString)){
                            openString += " "+strings.get(++i);
                        }
                        System.out.println("<close>");
                        newString.add(openString);
                    }else {
                        newString.add(strings.get(i));
                    }
                }
                return createFunctionNodeFromString(newString);
            } else{
                return createFunctionNodeFromString(strings);
            }
        }
    }

    private static boolean isStillOpen(String openString) {
        return openString.chars().filter(ch -> ch =='(').count()> openString.chars().filter(ch -> ch ==')').count();

    }

    private static Node createFunctionNodeFromString(List<String> strings) {
        List<Node> subNodes = new ArrayList<>();
        for (int i = 1; i < strings.size() ; i++){
            subNodes.add(createNodeFromString(strings.get(i)));
        }
        String functionString = strings.get(0).replace("(","");
        return new FunctionNode(GPUtils.getGPFunction(functionString), subNodes);
    }

    private static Node getTerminalNode(String string) {
        String strip = string.replace("(", "").replace(")", "");
        if (strip.startsWith("x")){
            return new VariableNode(Integer.valueOf(strip.replace("x", "")));
        }else{
            return new TerminalNode(Double.valueOf(strip));
        }
    }

    static Node generateNode(List<Node> terminalList, List<GPFunction> functionList, String method, int depth){
        Node node = null;
        if (depth == 0){
            node = selectTerminalNode(terminalList);
        }else if (method.equals(FULL)){
            node = selectFunctionNode(functionList);
            for (int i = 0 ; i < Math.min(2, ((FunctionNode)node).getMaxSubNodes()); i++){
                ((FunctionNode)node).addSubNode(generateNode(terminalList,functionList,method,depth-1));
            }
        }else if (method.equals(GROW)){
            if (Math.random() < FUNCTION_TERMINAL_SELECTION_RATIO){
                node = selectTerminalNode(terminalList);
            }else{
                node = selectFunctionNode(functionList);
                for (int i = 0 ; i<Math.min(2, ((FunctionNode)node).getMaxSubNodes()); i++){
                    ((FunctionNode)node).addSubNode(generateNode(terminalList,functionList,method,depth-1));
                }
            }

        }
        return node;
    }

    public static List<Node> crossoverNodes(List<Node> parentList, double rate){
        if (Math.random()<rate){
            return crossoverNodes(parentList);
        }
        return parentList;
    }


    public static List<Node> crossoverNodes(List<Node> parentList){
        var parentNode0 = parentList.get(0).clone();
        var parentNode1 = parentList.get(1).clone();

        int randomIndex0 = selectRandomIndex(parentNode0.size());
        int randomIndex1 = selectRandomIndex(parentNode1.size());
        Node childNode0;
        Node childNode1;
        if (randomIndex0 == 0 && randomIndex1==0){
            return Arrays.asList(parentNode1.clone(),parentNode0.clone());
        }else if (randomIndex0 == 0){
            childNode1 = parentNode1.clone();
            ((FunctionNode)childNode1).replaceSubtreeAt(randomIndex1,parentNode0.getSubtree(randomIndex0).get());

            return Arrays.asList(parentNode1.clone(),childNode1);
        }else if (randomIndex1 == 0){
            childNode0 = parentNode0.clone();
            ((FunctionNode)childNode0).replaceSubtreeAt(randomIndex0,parentNode1.getSubtree(randomIndex1).get());
            return Arrays.asList(childNode0,parentNode0.clone());
        }else{

        }

        childNode0 = parentNode0.clone();
        ((FunctionNode)childNode0).replaceSubtreeAt(randomIndex0,parentNode1.getSubtree(randomIndex1).get());
        childNode1 = parentNode1.clone();
        ((FunctionNode)childNode1).replaceSubtreeAt(randomIndex1,parentNode0.getSubtree(randomIndex0).get());

        return Arrays.asList(childNode0,childNode1);
    }

    private static int selectRandomIndex(int size) {
        return new Random().nextInt(size);
    }

    private static FunctionNode selectFunctionNode(List<GPFunction>functionNodeList) {
        // standard select equal random bag
        int selection = new Random().nextInt(functionNodeList.size());
        return new FunctionNode(functionNodeList.get(selection),null);
    }

    static Node selectTerminalNode(List<Node> terminalNodeList) {
        // standard select equal random bag
        int selection = new Random().nextInt(terminalNodeList.size());
        return terminalNodeList.get(selection).clone();
    }
}
