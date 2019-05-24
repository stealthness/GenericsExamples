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
    private List<Individual> individuals;

    // Methods

}
