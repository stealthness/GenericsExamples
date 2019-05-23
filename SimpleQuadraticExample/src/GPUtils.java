import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.BiFunction;
import java.util.function.Function;

public class GPUtils {


    static GPBiFunction add = new GPBiFunction(Double::sum,"add","+");
    static GPBiFunction subtract = new GPBiFunction((a, b)-> a-b,"subtract","-");
    static GPBiFunction multiply = new GPBiFunction((a, b)-> a*b, "multiply","*");
    static GPBiFunction protectedDivision = new GPBiFunction((a, b)-> (b==0)?1.0:a/b,"protectedDivision","/");
    static GPSingleFunction sin = new GPSingleFunction((a) -> Math.sin(a),"sin","sin");

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

    static Function<Node,Optional<Node>> reduceRules = (node -> {
        if (node.size()==3){
            if (node.print().equals("(/ x0 x0)")){
                System.out.println("<1>");
                return Optional.of(new TerminalNode(1.0));
            } else if(node.print().startsWith("(*") && node.print().contains("0.0")){
                return Optional.of(new TerminalNode(0.0));
            } else if (node.print().startsWith("(/") && node.print().endsWith("0.0)")){
                return Optional.of(new TerminalNode(1.0));
            } else if (node.print().startsWith("(+ 0.0") && node.print().endsWith("x0)")){
                return Optional.of(new VariableNode(0));
            } else if (node.print().endsWith("x0 x0)") && (node.print().startsWith("+") || (node.print().startsWith("*")))){
                return Optional.of(new TerminalNode(1.0));
            } else if (node.print().equals("(- x0 x0)")){

                System.out.println("<2>");
                return Optional.of(new TerminalNode(0.0));
            }
        } else{
            if (node.print().startsWith("(* 0.0") || node.print().startsWith("(/ 0.0")){
                return Optional.of(new TerminalNode((0.0)));
            }
        }
        return Optional.empty();
    });

    //



    public static List<GPBiFunction> getFunctionList(String basic) {
        List<GPBiFunction> list = new ArrayList<>();
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

    public static final Node subNode3 = new FunctionNode(GPUtils.protectedDivision,new TerminalNode(1.0), new VariableNode(0));
    public static final Node subNode4 = new FunctionNode(GPUtils.multiply, new VariableNode(0),new VariableNode(0));

    public static final Node testNode2 = new FunctionNode(GPUtils.add, subNode2,subNode3);
}
