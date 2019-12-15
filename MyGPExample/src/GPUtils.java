import java.util.List;
import java.util.function.BiFunction;

public class GPUtils {

    static BiFunction<Double[], List<Node>, Double> constant = ((inputs, nodes) -> 1.0);
}
