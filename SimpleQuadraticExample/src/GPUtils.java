import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.DoubleStream;

public class GPUtils {


    static GPFunction add = new GPFunction(Double::sum,"add","+");
    static GPFunction subtract = new GPFunction((a, b)-> a-b,"subtract","-");
    static GPFunction multiply = new GPFunction((a, b)-> a*b, "multiply","*");
    static GPFunction protectedDivision = new GPFunction((a, b)-> (b==0)?1.0:a/b,"protectedDivision","/");

    static BiFunction<DoubleStream, Node, Double> FitnessFunctionSumOfErrors = (d, node) ->{
        return d.reduce(0,(sum, x) -> sum + Math.abs((x*x + x + 1) - node.get(new double[]{x})));
    };

    public static List<String> FunctionList(String basic) {
        List<String> list = new ArrayList<>();
        list.add("add");
        list.add("subtract");
        list.add("multiply");
        list.add("protectedDivision");
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

    public static Map<String, BiFunction<Double,Double,Double>> getFunctionMap(String basic) {
        Map<String,BiFunction<Double,Double,Double>> functionMap = new HashMap();
        functionMap.put("add",add);
        functionMap.put("subtract",subtract);
        functionMap.put("multiply",multiply);
        functionMap.put("protectedDivision",protectedDivision);
        return functionMap;
    }
}
