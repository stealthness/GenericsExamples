import lombok.Builder;
import lombok.Data;

import java.util.List;

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
    final private List<Individual> individuals;

    /**
     * Maximum size of the population
     */
    private int maxSize;

    /**
     * Generate the initial population according method selected
     * @param Type
     */
    public void generate(String Type) {
    }

    public int size() {
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
