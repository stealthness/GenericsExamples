import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class GPUtils {

//    final static BiFunction<Double[], List<Node>, Double> constant = ((inputs, nodes) -> 1.0);

    final static BiFunction<Double[], List<Node>, Double> identity = ((inputs, nodes) -> nodes.get(0).calculate(inputs));
    static BiFunction<Double[], List<Node>, Double> abs = ((inputs, nodes) -> -nodes.get(0).calculate(inputs));
    static BiFunction<Double[], List<Node>, Double> reciprocal = ((inputs, nodes) -> 1/nodes.get(0).calculate(inputs));
    static BiFunction<Double[], List<Node>, Double> sin  = ((inputs, nodes) ->Math.sin(nodes.get(0).calculate(inputs)));
    static BiFunction<Double[], List<Node>, Double> cos  = ((inputs, nodes) ->Math.cos(nodes.get(0).calculate(inputs)));


    final static BiFunction<Double[], List<Node>, Double> add =
            ((inputs,nodes) -> nodes.stream()
                    .mapToDouble(node -> node.calculate(inputs))
                    .reduce(0.0, Double::sum));

//    final static BiFunction<Double[], List<Node>, Double> min =
//            ((inputs,nodes) -> nodes.stream()
//                    .mapToDouble(node -> node.calculate(inputs)).min().getAsDouble());

    final static BiFunction<Double[], List<Node>, Double> multiply =
            ((inputs, nodes) -> nodes.get(0).calculate(inputs)*nodes.get(1).calculate(inputs));

    final static BiFunction<Double[], List<Node>, Double> subtract =
            ((inputs,nodes) -> nodes.get(0).calculate(inputs)-nodes.get(1).calculate(inputs));

    final static BiFunction<Double[], List<Node>, Double> divide =
            ((inputs,nodes) -> {
                Double divisor = nodes.get(1).calculate(inputs);
                Double numerator = nodes.get(0).calculate(inputs);
                return numerator/(( divisor== 0.0)?1.0:divisor);
            });

    static Node createNode(String clojureString){

        List<String> strings = Arrays.asList(clojureString.split(" "));
        if (strings.size() == 1){ // Terminal or Variable Node
            String string = strings.get(0).replaceAll("[()]","");
            if (string.substring(0,1).matches("[0-9]|-")){
                return new TerminalNodeImpl(Double.parseDouble(string));
            }else{
                return new VariableNodeImpl(string,Integer.parseInt(string.substring(string.length()-1)));
            }
        }else { // Function Node
            if (clojureString.chars().filter(ch -> ch == '(').count() >1){ // nested Functions
                strings = Arrays.asList(clojureString.substring(1,clojureString.length()-1).split(" "));
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
                List<Node> terminalList = newString.stream().skip(1).map(GPUtils::createNode).collect(Collectors.toList());
                String functionString = newString.get(0);
                return new FunctionNodeImpl(getFunction(functionString),functionString,terminalList);
            } else{ // single function Node
                strings = Arrays.asList(clojureString.replaceAll("[()]","").split(" "));
                List<Node> terminalList = strings.stream().skip(1).map(GPUtils::createNode).collect(Collectors.toList());
                return new FunctionNodeImpl(getFunction(strings.get(0)),strings.get(0),terminalList);
            }
        }
    }

    private static boolean isStillOpen(String openString) {
        return openString.chars().filter(ch -> ch =='(').count()> openString.chars().filter(ch -> ch ==')').count();
    }

    static BiFunction<Double[], List<Node>, Double> getFunction(String functionString){


        return switch (functionString){
            case "+" -> GPUtils.add;
            case "-" -> GPUtils.subtract;
            case "*" -> GPUtils.multiply;
            case "/" -> GPUtils.divide;
            case "sin" -> GPUtils.sin;
            case "cos" -> GPUtils.cos;
            case "abs" -> GPUtils.abs;
            case "reciprocal"-> GPUtils.reciprocal;
            default -> GPUtils.identity;
        };
    }
}
