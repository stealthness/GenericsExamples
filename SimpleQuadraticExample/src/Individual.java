import lombok.Data;

import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.DoubleStream;

@Data
public class Individual implements Node{

    static List<GPFunction> setOfFunctions;
    static List<Node> setOfTerminals;

    private static final double FUNCTION_CHANCE = 0.5;

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
     * Generate an initial tree
     *  NOTE 1 function, 2 terminal node 50% chance of being variable
     * @return
     */
    static Individual generate(){
        // default values
        Individual individual = new Individual();
        individual.setMaxNumberOfVariables(1);
        individual.setMaxDepth(2);

        // problem specific
        setOfFunctions = GPUtils.getFunctionList("Basic");
        setOfTerminals = GPUtils.getTerminalsList("basic");

        individual.setRange(new double[]{-1.0,1.0});
        individual.setFitnessFunction(GPUtils.FitnessFunctionSumOfErrors);

        // non random set
        individual.setRoot(generatingFunction(1));

        return individual;

    }

    static Node generatingTerminal() {

        if (Math.random() < 0.5){
            return new VariableNode(0);
        } else{
            List<Node> list = GPUtils.getTerminalsList("basic");
            Random r = new Random();
            return list.get(r.nextInt(list.size()));
        }
    }

    static Node generatingFunction(int maxDepth) {
        List<GPFunction> list = GPUtils.getFunctionList("basic");
        Random r = new Random();
        GPFunction function = list.get(r.nextInt(list.size()));

        if (maxDepth > 1){
            return new FunctionNode(function, selectRandomTerminalOrFunction(maxDepth),selectRandomTerminalOrFunction(maxDepth));
        }else{
            return new FunctionNode(function,  generatingTerminal(),generatingTerminal());
        }
    }

    static Node selectRandomTerminalOrFunction(int maxDepth){
        return (Math.random() < FUNCTION_CHANCE)?generatingFunction(maxDepth -1):generatingTerminal();
    }


    public void setNode(int selectedNodeIndex, Node node) {
        if (selectedNodeIndex == 0){
            this.root = node;
        } else if ( selectedNodeIndex == 1 && root.size() > 1) {
            ((FunctionNode) root).setNode1(node);
        } else if (selectedNodeIndex < ((FunctionNode)root).getNode1().size()){
            // to do
        } else if (selectedNodeIndex == root.size() - ((FunctionNode)root).getNode1().size() ){
            ((FunctionNode)root).setNode1(node);
        } else {
            // to do
        }
    }
}
