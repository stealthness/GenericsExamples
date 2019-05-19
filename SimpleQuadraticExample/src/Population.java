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

    /**
     * Generate the initial population according method selected
     */
    public void generate(String type) {
        individuals = new ArrayList<>();
        Individual.setOfTerminals = GPUtils.getTerminalsList("basic");
        Individual.setOfFunctions = GPUtils.getFunctionList("basic");
        IntStream.range(0,maxSize).forEach(i -> {
            individuals.add(Individual.generate());
        });
    }

    public int size() {
        if (individuals == null){
            return 0;
        }
        return individuals.size();
    }


    // generate population


    // crossover function

    // mutate function

    // breed function

    // evaluate function

    // sort function

    // builder methods



}
