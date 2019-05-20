import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

@Data
@Builder
/**
 * Populations contains a list of individuals, how the initial population is constructed
 * The rates at which population is mutated, breed and crossed with. Also contains the
 * evaluation, selection and fitness function
 */
public class Population {

    /**
     * Contains a List of the individuals. Each individual contains a Node, fitness
     */
    private List<Individual> individuals;

    /**
     * Maximum size of the population
     */
    private int maxSize;



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

    // crossover function

    // mutate function

    void doMutations() {
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
        return true;
    }

    // builder methods



}
