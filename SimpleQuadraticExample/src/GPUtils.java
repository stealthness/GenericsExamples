import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;

public class GPUtils {


    static GPFunction add = new GPFunction(Double::sum,"add","+");
    static GPFunction subtract = new GPFunction((a, b)-> a-b,"subtract","-");
    static GPFunction multiply = new GPFunction((a, b)-> a*b, "multiply","*");
    static GPFunction protectedDivision = new GPFunction((a, b)-> (b==0)?1.0:a/b,"protectedDivision","/");

    static BiFunction<Individual, Node, Double> FitnessFunctionSumOfErrors = (individual, node) -> individual.getDoubleStream().reduce(0,(sum, x) -> sum + Math.abs(node.apply(new double[]{x}) - individual.apply(new double[]{x})));

    static Function<Population,Individual> selectWeightedParent = population -> {
        // Get individuals
        final var individuals = population.getIndividuals();

        // Spin roulette wheel
        final var rouletteWheelPosition = Math.random() * population.getSumOfFitness();

        // Find parent
        var spinWheel = 0.0;
        for (Individual individual : individuals) {
            spinWheel += individual.getFitness();
            if (spinWheel >= rouletteWheelPosition) {
                return individual;
            }
        }
        return individuals.get(individuals.size()-1);
    };

    //

    static Function<Node,Node> reduceRules = (node -> {
        if (node.print().equals("(/ x0 x0)")){
            return new TerminalNode(1.0);
        } else if(node.print().startsWith("(* 0.0")){
            return new TerminalNode(0.0);
        }
        return node;
    });

    //



    public static List<GPFunction> getFunctionList(String basic) {
        List<GPFunction> list = new ArrayList<>();
        list.add(GPUtils.add);
        list.add(GPUtils.subtract);
        list.add(GPUtils.multiply);
        list.add(GPUtils.protectedDivision);
        return list;
    }


    public static List<Node> getTerminalsList(String basic) {
        List<Node> list = new ArrayList<>();
        list.add(new TerminalNode(0.0));
        list.add(new TerminalNode(1.0));
        list.add(new TerminalNode(2.0));
        list.add(new TerminalNode(2.0));
        list.add(new TerminalNode(3.0));
        list.add(new TerminalNode(4.0));
        list.add(new VariableNode(0));
        return list;
    }


    public static final Node subNode1 = new FunctionNode(GPUtils.add, new VariableNode(0),new TerminalNode(1.0));
    public static final Node subNode2 = new FunctionNode(GPUtils.multiply, new VariableNode(0),new VariableNode(0));

    public static final Node testNode = new FunctionNode(GPUtils.add, subNode1,subNode2);
}
