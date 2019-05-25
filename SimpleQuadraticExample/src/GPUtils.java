import java.util.function.BiFunction;

public class GPUtils {


    public static BiFunction<Double[], Node, Double> identity = ((inputs, node) -> node.get(inputs));

}
