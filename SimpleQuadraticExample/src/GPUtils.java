import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.DoubleStream;

public class GPUtils {


    static GPFunction add = new GPFunction(Double::sum,"add","+");
    static GPFunction subtract = new GPFunction((a, b)-> a-b,"subtract","-");
    static GPFunction multiply = new GPFunction((a, b)-> a*b, "multiply","*");
    static GPFunction protectedDivision = new GPFunction((a, b)-> (b==0)?1.0:a/b,"protectedDivision","/");

    static BiFunction<DoubleStream, Node, Double> FitnessFunctionSumOfErrors = (d, node) -> d.reduce(0,(sum, x) -> sum + Math.abs((x*x + x + 1) - node.get(new double[]{x})));

    public static List<GPFunction> FunctionList(String basic) {
        List<GPFunction> list = new ArrayList<>();
        list.add(GPUtils.add);
        list.add(GPUtils.subtract);
        list.add(GPUtils.multiply);
        list.add(GPUtils.protectedDivision);
        return list;
    }



}
