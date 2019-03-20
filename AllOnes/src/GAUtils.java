import java.util.Arrays;
import java.util.function.Function;

/**
 * Created by Stephen West on 20/03/2019.
 */
class GAUtils {

    static Function<Individual, Double> sillyFirstGeneFitness = individual -> (double)individual.getGene(0);

    static Function<Individual, Double> sillyLastGeneFitness = individual -> (double)individual.getGene(individual.size()-1);

    static Function<Individual, Double> getMeanGeneFitness = individual -> {
        Integer s = Arrays.stream(individual.getChromosome()).sum();
        return ((double)s)/individual.size();
    };
}
