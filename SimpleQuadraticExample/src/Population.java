import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.IntStream;

@Data
@Builder
/**
 * Populations contains a list of individuals, how the initial population is constructed
 * The rates at which population is mutated, breed and crossed with. Also contains the
 * evaluation, selection and fitness function
 */
public class Population {

    private static final Double ERROR_TOLERANCE = 0.01;
    /**
     * Contains a List of the individuals. Each individual contains a Node, fitness
     */
    private List<Individual> individuals;

    /**
     * Maximum size of the population
     */
    private int maxSize;

    /**
     * Method used to generate the initial population
     */
    private String generationMethod = "grow";


    private int initialMaxDepth = 2;

    int size() {
        if (individuals == null){
            return 0;
        }
        return individuals.size();
    }



    // generate population
    /**
     * Generate the initial population according method selected
     */
    void generate(String type) {
        individuals = new ArrayList<>();
        Individual.setOfTerminals = GPUtils.getTerminalsList("basic");
        Individual.setOfFunctions = GPUtils.getFunctionList("basic");
        if (type.equals("full" )){
            IntStream.range(0,maxSize).forEach(i -> {
                individuals.add(Individual.generate());
            });
        } else if (type.equals("grow")){
            // to do
        } else {
            // to doo
        }
    }

    void generate(){
        generate(generationMethod);
    }

    // crossover function

    // mutate function

    void doMutations() {
        individuals.forEach(individual -> {
            if (Math.random() <0.25){
                int selectedNode = new Random().nextInt(individual.size());
                individual.setNode(selectedNode, new FunctionNode(GPUtils.multiply,new VariableNode(0),new VariableNode(0)));
            }
        });
    }

    // breed function

    void doSelection() {
    }


    void doCrossing() {
    }

    // evaluate function



    public void evaluate() {
        individuals.stream().forEach(Individual::evaluate);
    }

    // sort function

    void sort(){
        individuals.stream().sorted();
    }

    public boolean isTerminationConditionMet() {

        return individuals.stream().anyMatch(individual -> (individual.getFitness() < ERROR_TOLERANCE));

    }

    public Individual getFittest(int index) {
        return null;
    }

    public void addIndividual(Individual individual) {
        if (individuals.size() < maxSize){
            individuals.add(individual);
        }
    }

    // builder methods



}
