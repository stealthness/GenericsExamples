import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
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

    static BiFunction<List<Node>, Double, Node> mutateIndex1 = (nodes,mutateRate) -> {
        if (Math.random() > mutateRate){
            return nodes.get(0);
        }else{
            if (nodes.get(0).size()==1){
                return nodes.get(1).clone();
            }else{
                Node mutatedNode =  nodes.get(0).clone();
                ((FunctionNode)mutatedNode).replaceSubtreeAt(1,nodes.get(1));
                return mutatedNode;
            }
        }
    };

    static BiFunction<List<String>,Integer[], List<Node>> crossoverAt = (parentList,indexes) -> {
        var parentNode0 = NodeUtils.createNodeFromString(parentList.get(0));
        var parentNode1 = NodeUtils.createNodeFromString(parentList.get(1));
        Node child0 = parentNode0.clone();
        ((FunctionNode)child0).replaceSubtreeAt(indexes[0],parentNode1.getSubtree(indexes[1]).get());
        Node child1 = parentNode1.clone();
        ((FunctionNode)child1).replaceSubtreeAt(indexes[1],parentNode0.getSubtree(indexes[0]).get());
        return Arrays.asList(child0,child1);
    };



    static BiFunction<List<String>, Double, List<Node>> crossoverAt1 = (parentList,crossoverRate) -> {
        var parentNode0 = NodeUtils.createNodeFromString(parentList.get(0));
        var parentNode1 = NodeUtils.createNodeFromString(parentList.get(1));
        Node child0 = parentNode0.clone();
        ((FunctionNode)child0).replaceSubtreeAt(1,parentNode1.getSubtree(1).get());
        Node child1 = parentNode1.clone();
        ((FunctionNode)child1).replaceSubtreeAt(1,parentNode0.getSubtree(1).get());
        return Arrays.asList(child0,child1);
    };

    static BiFunction<List<String>, Double, List<Node>> crossoverAt2 = (parentList,crossoverRate) -> {
        var parentNode0 = NodeUtils.createNodeFromString(parentList.get(0));
        var parentNode1 = NodeUtils.createNodeFromString(parentList.get(1));

        Node child0 = parentNode0.clone();
        ((FunctionNode)child0).replaceSubtreeAt(2,parentNode1.getSubtree(2).get());
        Node child1 = parentNode1.clone();
        ((FunctionNode)child1).replaceSubtreeAt(2,parentNode0.getSubtree(2).get());
        return Arrays.asList(child0,child1);
    };

    static BiFunction<List<Node>, Double, Node> mutateRandomIndex = (nodes,mutateRate) -> {
        if (Math.random() > mutateRate){
            return nodes.get(0);
        }else{
            if (nodes.get(0).size()==1){  // only a single terminal node
                return nodes.get(1).clone();
            }else{
                int randomIndex = new Random().nextInt(nodes.get(0).size());
                Node mutatedNode =  nodes.get(0).clone();
                ((FunctionNode)mutatedNode).replaceSubtreeAt(randomIndex ,nodes.get(1));
                return mutatedNode;
            }
        }
    };

    // static methods


    public static GPFunction getGPFunction(String functionString){
        return switch (functionString) {
            case "+" -> new GPMultiFunction(add, functionString);
            case "/" -> new GPBiFunction(divide, functionString);
            case "*" -> new GPMultiFunction(multiply, functionString);
            case "-" -> new GPBiFunction(subtract, functionString);
            default -> {
                GPFunction function = null;
                for (Field field : GPUtils.class.getDeclaredFields()) {

                    if (functionString.equals(field.getName())){
                        try {
                            switch (functionString) {
                                case "abs", "reciprocal", "identity" -> {
                                    Class<?> functionClass = Class.forName(GPSingleFunction.class.getName());
                                    Constructor<?> functionConstructor = functionClass.getDeclaredConstructors()[0];
                                    function = (GPFunction) functionConstructor.newInstance(GPUtils.class.getDeclaredField(functionString).get(null), functionString);
                                }
                                default -> {
                                    Class<?> functionClass = Class.forName(GPMultiFunction.class.getName());
                                    Constructor<?> functionConstructor = functionClass.getDeclaredConstructors()[0];
                                    function = (GPFunction) functionConstructor.newInstance(GPUtils.class.getDeclaredField(functionString).get(null), functionString);
                                }
                            }
                        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchFieldException e) {
                            e.printStackTrace();
                        }
                        break;
                    }
                }
                break function;
            }
        };
    }


}
