import java.util.List;
import java.util.function.BiFunction;

public class GPUtils {


    public static BiFunction<Double[], List<Node>, Double> identity = ((inputs, nodes) -> nodes.get(0).calculate(inputs));
    public static BiFunction<Double[], List<Node>, Double> abs = ((inputs, nodes) -> -nodes.get(0).calculate(inputs));
    public static BiFunction<Double[], List<Node>, Double> reciprocal = ((inputs, nodes) -> 1/nodes.get(0).calculate(inputs));
    public static BiFunction<Double[], List<Node>, Double> sin  = ((inputs, nodes) ->Math.sin(nodes.get(0).calculate(inputs)));


    public static BiFunction<Double[], List<Node>, Double> addBiFunction = ((inputs,nodes) -> nodes.get(0).calculate(inputs)+nodes.get(1).calculate(inputs));
    public static BiFunction<Double[], List<Node>, Double> multiplyBiFunction = ((inputs, nodes) -> nodes.get(0).calculate(inputs)*nodes.get(1).calculate(inputs));
    public static BiFunction<Double[], List<Node>, Double> subtractBiFunction = ((inputs,nodes) -> nodes.get(0).calculate(inputs)-nodes.get(1).calculate(inputs));
    public static BiFunction<Double[], List<Node>, Double> protectedDivisionBiFunction = ((inputs,nodes) -> {
        Double divisor = nodes.get(1).calculate(inputs);
        Double numerator = nodes.get(0).calculate(inputs);
        return numerator/(( divisor== 0.0)?1.0:divisor);
    });
}
