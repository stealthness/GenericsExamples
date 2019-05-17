import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.DoubleStream;

@Data
public class Individual implements Node{

    private static final double FUNCTION_CHANCE = 0.5;
    List<BiFunction<Double,Double,Double>> setOfFunctions;
    List<Double> terminals;
    private Node root;
    private Double fitness;
    private int maxNumberOfVariables;
    private int maxDepth;
    private double[] range;
    private BiFunction<DoubleStream, Node,Double> fitnessFunction;

    private Individual(){}


    public void evaluate(){
        var d = DoubleStream.of(-1.0,-0.9,-0.8,-0.7,-0.6,-0.5,-0.4,-0.3,-0.2,-0.1
                ,0.0,0.1,0.2,0.3,0.4,0.5,0.6,0.7,0.8,0.9,1.0);

        var fit = fitnessFunction.apply(d, getRoot());
        setFitness(fit);
    }

    @Override
    public int size(){
        return root.size();
    }

    @Override
    public int getDepth(){
        return root.getDepth();
    }

    @Override
    public Double get(double[] inputs) {
        return root.get(inputs);
    }

    @Override
    public String print() {
        return root.print();
    }


    /**
     * Generate an intial tree
     *  NOTE 1 function, 2 terminal node 50% chance of being variable
     * @return
     */
    static Individual generate(){
        // default values
        Individual individual = new Individual();
        individual.setMaxNumberOfVariables(1);
        individual.setMaxDepth(2);

        // problem specific
        //individual.setSetOfFunctions(GPUtils.FunctionList("Basic"));
        var setOfTerminal = new ArrayList<Double>();
        setOfTerminal.add(0.0);
        setOfTerminal.add(1.0);
        setOfTerminal.add(2.0);
        setOfTerminal.add(3.0);
        individual.setTerminals(setOfTerminal);

        individual.setRange(new double[]{-1.0,1.0});
        individual.setFitnessFunction(GPUtils.FitnessFunctionSumOfErrors);
        // non random set
        individual.setRoot(new FunctionNode(GPUtils.add, new VariableNode(0),new TerminalNode(1.0)));

        if (Math.random() < 0.5){
            if (Math.random() < 0.5){
                individual.setRoot(new VariableNode(0));
                return individual;
            } else{
                individual.setRoot(new TerminalNode(getRandomElement(setOfTerminal)));
                return individual;
            }
        } else {


        }

        return individual;

    }

    // Function select an element base on index
    // and return an element
    public static Double getRandomElement(List<Double> list)
    {
        Random rand = new Random();
        return list.get(rand.nextInt(list.size()));
    }


    public Node generatingTerminal() {

        if (Math.random() < 0.5){
            return new VariableNode(0);
        } else{
            return new TerminalNode(getRandomElement(getTerminals()));
        }
    }

    public Node generatingFunction(int maxDepth) {
        BiFunction<Double, Double, Double> function = GPUtils.add;
        Map<String, String> functionStrings = GPUtils.getFunctionStringMap("basic");
        System.out.println(function.getClass().getSimpleName());

        if (maxDepth > 1){
            return new FunctionNode(GPUtils.add, selectRandomTerminalOrFunction(),selectRandomTerminalOrFunction());
        }else{
            return new FunctionNode(GPUtils.add,  generatingTerminal(),generatingTerminal());
        }
    }

    Node selectRandomTerminalOrFunction(){
        return (Math.random() < FUNCTION_CHANCE)?generatingFunction(maxDepth -1):generatingTerminal();
    }


}
