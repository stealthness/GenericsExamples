import java.util.List;
import java.util.function.BiFunction;

public class GPUtils {


    public static BiFunction<Double[], List<Node>, Double> identity = ((inputs, nodes) -> nodes.get(0).calculate(inputs));

    public static BiFunction<Double[], List<Node>, Double> abs = ((inputs, nodes) -> -nodes.get(0).calculate(inputs));

    public static BiFunction<Double[], List<Node>, Double> reciprocal = ((inputs, nodes) -> 1/nodes.get(0).calculate(inputs));

    public static BiFunction<Double[], List<Node>, Double> sin  = ((inputs, nodes) ->Math.sin(nodes.get(0).calculate(inputs)));


    public static BiFunction<Double[], List<Node>, Double> addBiFunction = ((inputs,nodes) -> {
        System.out.println("<1>");
        System.out.println(nodes.get(0).calculate(inputs));
        System.out.println(nodes.get(1).calculate(inputs));
        return nodes.get(0).calculate(inputs)+nodes.get(1).calculate(inputs);
    });
}
