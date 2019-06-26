import java.util.List;
import java.util.function.BiFunction;

public class GPUtils {

    static BiFunction<Double[], List<Node<Double[],Double>>, Double> identity = ((inputs, nodes) -> nodes.get(0).calculate(inputs));
}
