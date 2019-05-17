import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.DoubleStream;

public class GPUtils {


    static BiFunction<Double,Double,Double> add = Double::sum;
    static BiFunction<Double,Double,Double> subtract = (a, b)-> a-b;
    static BiFunction<Double,Double,Double> multiply = (a, b)-> a*b;
    static BiFunction<Double,Double,Double> protectedDivision = (a, b)-> (b==0)?1.0:a/b;

    static BiFunction<DoubleStream, Node, Double> FitnessFunctionSumOfErrors = (d, node) ->{
        return d.reduce(0,(sum, x) -> sum + Math.abs((x*x + x + 1) - node.get(new double[]{x})));
    };

    public static List<BiFunction<Double,Double,Double>> FunctionList(String basic) {
        List<BiFunction<Double,Double,Double>> list = new ArrayList<>();
        list.add(add);
        list.add(subtract);
        list.add(multiply);
        list.add(protectedDivision);
        return list;
    }

    public static Map<String, String> getFunctionStringMap(String basic) {
        Map<String, String> functionStringMap = new HashMap();
        functionStringMap.put("add","+");
        functionStringMap.put("subtract","-");
        functionStringMap.put("multiply","*");
        functionStringMap.put("protectedDivision","/");
        return functionStringMap;
    }
}
