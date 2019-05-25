import java.util.List;
import java.util.function.BiFunction;

public class GPUtils {


    public static BiFunction<Double[], List<Node>, Double> identity = ((inputs, nodes) -> nodes.get(0).calculate(inputs));

    public static BiFunction<Double[], List<Node>, Double> abs = ((inputs, nodes) ->null);

    public static BiFunction<Double[], List<Node>, Double> reciprocal = ((inputs, nodes) ->null);

    public static BiFunction<Double[], List<Node>, Double> sin  = ((inputs, nodes) ->null);
}
