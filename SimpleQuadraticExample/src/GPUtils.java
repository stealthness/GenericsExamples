import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

public class GPUtils {


    static BiFunction<Double[], List<Node>, Double> identity = ((inputs, nodes) -> nodes.get(0).calculate(inputs));
    static BiFunction<Double[], List<Node>, Double> abs = ((inputs, nodes) -> -nodes.get(0).calculate(inputs));
    static BiFunction<Double[], List<Node>, Double> reciprocal = ((inputs, nodes) -> 1/nodes.get(0).calculate(inputs));
    static BiFunction<Double[], List<Node>, Double> sin  = ((inputs, nodes) ->Math.sin(nodes.get(0).calculate(inputs)));


    static BiFunction<Double[], List<Node>, Double> addBiFunction =
            ((inputs,nodes) -> nodes.stream()
                    .mapToDouble(node -> node.calculate(inputs))
                    .reduce(0.0, Double::sum));

    static BiFunction<Double[], List<Node>, Double> multiplyBiFunction =
            ((inputs, nodes) -> nodes.get(0).calculate(inputs)*nodes.get(1).calculate(inputs));

    public static BiFunction<Double[], List<Node>, Double> subtractBiFunction =
            ((inputs,nodes) -> nodes.get(0).calculate(inputs)-nodes.get(1).calculate(inputs));

    public static BiFunction<Double[], List<Node>, Double> protectedDivisionBiFunction =
            ((inputs,nodes) -> {
                Double divisor = nodes.get(1).calculate(inputs);
                Double numerator = nodes.get(0).calculate(inputs);
                return numerator/(( divisor== 0.0)?1.0:divisor);
            });

    // Mutate Functions

    public static BiFunction<List<Node>, Double, Node> mutateIndex1 = (nodes,mutateRate) -> {
        System.out.println("node : " + nodes.get(0).print());
        System.out.println("mutate : " + nodes.get(1).print());
        if (nodes.get(0).size() == 0){
            return nodes.get(0);
        }
        Node mutatedNode =  nodes.get(0).clone();
        System.out.println("** pre-mutation  : "+mutatedNode.print());
        ((FunctionNode)mutatedNode).setSubNodeAt(1,nodes.get(1));
        System.out.println("** post-mutation : "+mutatedNode.print());
        return mutatedNode;
    };

    // static methods

    static Node generateFullTree(List<FunctionNode> functionNodeList, List<Node> leafNodeList, int maxDepth){
        Node root;
        if (maxDepth >0){
            root = functionNodeList.get(new Random().nextInt(functionNodeList.size())).clone();
            if (root.getClass() == FunctionNode.class){
                ((FunctionNode)root).setSubNode(generateFullTree(functionNodeList,leafNodeList,maxDepth-1));
            }
        }else{
            root = leafNodeList.get(new Random().nextInt(leafNodeList.size()));
        }
        return root;
    }


}
