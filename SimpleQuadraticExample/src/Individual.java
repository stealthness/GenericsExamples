import lombok.Data;

import java.util.List;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.stream.DoubleStream;

@Data
public class Individual implements Node,Comparable{

    private static final String DEFAULT_TYPE = "grow";
    private static final int DEFAULT_MAX_DEPTH = 2;
    private static final int DEFAULT_SINGLE_VARIABLE = 1;
    static List<GPFunction> setOfFunctions;
    static List<Node> setOfTerminals;

    private static final double FUNCTION_CHANCE = 0.5;
    private String type;
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
    public Node clone() {
        // to do
        return null;
    }

    @Override
    public Double apply(double[] inputs) {
        return root.apply(inputs);
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
        return generate(DEFAULT_TYPE,DEFAULT_MAX_DEPTH,DEFAULT_SINGLE_VARIABLE);
    }

    static Individual generate(Node node) {

        Individual individual = new Individual();
        individual.setRange(new double[]{-1.0,1.0});
        individual.setFitnessFunction(GPUtils.FitnessFunctionSumOfErrors);
        individual.setRoot(node.clone());
        return individual;
    }

    static Individual generate(String type, int maxDepth, int maxNumberOfVariables){
        Individual individual = new Individual();
        individual.setMaxNumberOfVariables(maxNumberOfVariables);
        individual.setMaxDepth(maxDepth);

        individual.setRange(new double[]{-1.0,1.0});
        individual.setFitnessFunction(GPUtils.FitnessFunctionSumOfErrors);

        setOfFunctions = GPUtils.getFunctionList("Basic");
        setOfTerminals = GPUtils.getTerminalsList("basic");

        if (type.endsWith("Grow")){
            // to do
        }else{
            // default is full
            if (maxDepth == 0){
                    individual.setRoot(generatingTerminal());
                    return individual;
            } else if (maxDepth >= 1){
                    individual.setRoot(generatingFunction(maxDepth,type));
                    return individual;
            }else{
                throw new IllegalArgumentException("maxdepth value not allowed" + maxDepth);
            }

        };
        return null;
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

    static Node generatingFunction(int maxDepth, String type) {
        List<GPFunction> list = GPUtils.getFunctionList("basic");
        Random r = new Random();
        GPFunction function = list.get(r.nextInt(list.size()));

        if (maxDepth > 1){
            if (type.equals("grow")){
                return new FunctionNode(function, selectRandomTerminalOrFunction(maxDepth, type),selectRandomTerminalOrFunction(maxDepth, type));
            }else{
                return new FunctionNode(function, generatingFunction(maxDepth -1, type), generatingFunction(maxDepth -1, type));
            }

        }else{
            return new FunctionNode(function,  generatingTerminal(),generatingTerminal());
        }
    }

    private static Node selectRandomTerminalOrFunction(int maxDepth, String type){
        return (Math.random() < FUNCTION_CHANCE)?generatingFunction(maxDepth -1,type):generatingTerminal();
    }


    @Override
    public int compareTo(Object that) {
        return Double.compare(this.getFitness(),((Individual)that).getFitness());
    }


    void crossWith(Individual randomIndividual) {
        Node subTree = randomIndividual.selectSubtree(new Random().nextInt(randomIndividual.size()));
        this.changeSubtreeAt(new Random().nextInt(size()), subTree);
    }

    void changeSubtreeAt(int index, Node subTree) {
        if (index == 0){
            setRoot(subTree);
        } else {
            // change at FunctionNode level
            ((FunctionNode)getRoot()).changeSubtreeAt(index, subTree);
        }
    }

    /**
     * Returns the subtree located at the index
     * @param index
     * @return
     */
    Node selectSubtree(int index) {
        if (index == 0){
            return root;
        }else {

            return ((FunctionNode)root).getSubtree(index);
        }
    }
}
