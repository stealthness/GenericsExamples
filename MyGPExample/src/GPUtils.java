import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiFunction;

public class GPUtils {

    static BiFunction<Double[], List<Node>, Double> constant = ((inputs, nodes) -> 1.0);

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

    static Node createNode(String clojureString){

        List<String> strings = Arrays.asList(clojureString.split(" "));
        if (strings.size() == 1){
            String string = strings.get(0).replaceAll("[()]","");
            if (string.substring(0,1).matches("[0-9]|-")){
                return new TerminalNodeImpl(Double.parseDouble(string));
            }else{
                return new VariableNodeImpl(string,Integer.parseInt(string.substring(string.length()-1)));
            }
        }
        return new TerminalNodeImpl(1.0);
    }

//    static Node createNodeFromString(String string){
//        List<String> strings = Arrays.asList(string.split(" "));
//        if (strings.size()==1){
//            return getTerminalNode(strings.get(0));
//        } else {
//            if (string.chars().filter(ch -> ch == '(').count() >1){
//                List<String> newString = new ArrayList<>();
//                newString.add(strings.get(0));
//                for (int i = 1  ; i < strings.size(); i++){
//                    if (strings.get(i).contains("(")){
//                        String openString = strings.get(i);
//                        while(isStillOpen(openString)){
//                            openString += " "+strings.get(++i);
//                        }
//                        newString.add(openString);
//                    }else {
//                        newString.add(strings.get(i));
//                    }
//                }
//                return createFunctionNodeFromString(newString);
//            } else{
//                return createFunctionNodeFromString(strings);
//            }
//        }
//    }
}
