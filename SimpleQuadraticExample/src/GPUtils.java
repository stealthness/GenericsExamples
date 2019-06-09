import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;

public class GPUtils {


    static BiFunction<Double[], List<Node>, Double> identity = ((inputs, nodes) -> nodes.get(0).calculate(inputs));
    static BiFunction<Double[], List<Node>, Double> abs = ((inputs, nodes) -> -nodes.get(0).calculate(inputs));
    static BiFunction<Double[], List<Node>, Double> reciprocal = ((inputs, nodes) -> 1/nodes.get(0).calculate(inputs));
    static BiFunction<Double[], List<Node>, Double> sin  = ((inputs, nodes) ->Math.sin(nodes.get(0).calculate(inputs)));


    static BiFunction<Double[], List<Node>, Double> add =
            ((inputs,nodes) -> nodes.stream()
                    .mapToDouble(node -> node.calculate(inputs))
                    .reduce(0.0, Double::sum));

    static BiFunction<Double[], List<Node>, Double> min =
            ((inputs,nodes) -> nodes.stream()
                    .mapToDouble(node -> node.calculate(inputs)).min().getAsDouble());

    static BiFunction<Double[], List<Node>, Double> multiply =
            ((inputs, nodes) -> nodes.get(0).calculate(inputs)*nodes.get(1).calculate(inputs));

    static BiFunction<Double[], List<Node>, Double> subtract =
            ((inputs,nodes) -> nodes.get(0).calculate(inputs)-nodes.get(1).calculate(inputs));

    static BiFunction<Double[], List<Node>, Double> divide =
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


    static Node createNodeFromString(String string){
        List<String> strings = Arrays.asList(string.split(" "));
        if (strings.size()==1){
            return getTerminalNode(strings.get(0));
        } else {
            if (string.chars().filter(ch -> ch == '(').count() >1){
                List<String> newString = new ArrayList<>();
                newString.add(strings.get(0));
                for (int i = 1  ; i < strings.size(); i++){
                    if (strings.get(i).contains("(")){
                        String openString = strings.get(i);
                        while(isStillOpen(openString)){
                            openString += " "+strings.get(++i);
                        }
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
        System.out.println(functionString);
        return switch (functionString) {
            case "+" -> new FunctionNode(new GPMultiFunction(add, functionString), subNodes);
            case "/" -> new FunctionNode(new GPBiFunction(divide, functionString), subNodes);
            case "*" -> new FunctionNode(new GPMultiFunction(multiply, functionString), subNodes);
            case "-" -> new FunctionNode(new GPBiFunction(subtract, functionString), subNodes);
            default -> {
                Node r = null;
                for (Field field : GPUtils.class.getDeclaredFields()) {
                    if (functionString.equals(field.getName())){
                        try {
                            r = switch (functionString){
                                case "abs","reciprocal","identity" -> {
                                    Class<?> functionClass = Class.forName(GPSingleFunction.class.getName());
                                    Constructor<?> functionConstructor = functionClass.getDeclaredConstructors()[0];

                                    break new FunctionNode((GPFunction) functionConstructor.newInstance(GPUtils.class.getDeclaredField(functionString).get(null),functionString), subNodes);
                                }
                                default -> {
                                    Class<?> functionClass = Class.forName(GPMultiFunction.class.getName());
                                    Constructor<?> functionConstructor = functionClass.getDeclaredConstructors()[0];
                                    break new FunctionNode((GPFunction) functionConstructor.newInstance(GPUtils.class.getDeclaredField(functionString).get(null),functionString), subNodes);
                                }
                            };
                        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                    }
                }
                break r;
            }
        };
    }

    private static Node getTerminalNode(String string) {
        String strip = string.replace("(", "").replace(")", "");
        if (strip.startsWith("x")){
            return new VariableNode(Integer.valueOf(strip.replace("x", "")));
        }else{
            return new TerminalNode(Double.valueOf(strip));
        }
    }

}
