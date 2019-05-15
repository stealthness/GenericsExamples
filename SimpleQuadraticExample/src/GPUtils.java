import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

public class GPUtils {


    static BiFunction<Double,Double,Double> add = Double::sum;
    static BiFunction<Double,Double,Double> subtract = (a, b)-> a-b;
    static BiFunction<Double,Double,Double> multiply = (a, b)-> a*b;
    static BiFunction<Double,Double,Double> protectedDivision = (a, b)-> (b==0)?1.0:a/b;

    public static List<BiFunction<Double,Double,Double>> FuctionList(String basic) {
        List<BiFunction<Double,Double,Double>> list = new ArrayList<>();
        list.add(add);
        list.add(subtract);
        list.add(multiply);
        list.add(protectedDivision);
        return list;
    }
}
