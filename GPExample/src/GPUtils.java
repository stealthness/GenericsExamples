import java.util.List;
import java.util.function.BiFunction;

public class GPUtils {

    static BiFunction<Double[], List<Node<Double[],Double>>, Double> identity = ((inputs, nodes) -> nodes.get(0).calculate(inputs));
    static BiFunction<Double[], List<Node<Double[],Double>>, Double> abs = ((inputs, nodes) -> Math.abs(nodes.get(0).calculate(inputs)));

    static BiFunction<Double[], List<Node<Double[],Double>>, Double> add =
            ((inputs,nodes) -> nodes.stream()
                    .mapToDouble(node -> node.calculate(inputs))
                    .reduce(0.0, Double::sum));
}
