import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.DoubleStream;

@Data
public class Individual implements Node{

    List<BiFunction<Double,Double,Double>> setOfFunctions;
    List<Double> terminals;
    private Node root;
    private Double fitness;
    private int maxNumberOfVariables;
    private Function<Double,Double[]> fitnessFunction;

    private Individual(){

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
        Individual individual = new Individual();
        individual.setMaxNumberOfVariables(1);
        individual.setSetOfFunctions(GPUtils.FuctionList("Basic"));
        var setOfTrminal = new ArrayList<Double>();
        setOfTrminal.add(0.0);
        setOfTrminal.add(1.0);
        setOfTrminal.add(2.0);
        setOfTrminal.add(3.0);
        individual.setTerminals(setOfTrminal);

        individual.setRoot(new FunctionNode(GPUtils.add,"+", new VariableNode(0),new TerminalNode(1.0)));

        return individual;

    }
}
