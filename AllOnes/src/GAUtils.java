import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Created by Stephen West on 20/03/2019.
 */
class GAUtils {

    // Fitness Functions

    static Function<Individual, Double> sillyFirstGeneFitness = individual -> (double)individual.getGene(0);

    static Function<Individual, Double> sillyLastGeneFitness = individual -> (double)individual.getGene(individual.size()-1);

    static Function<Individual, Double> getMeanGeneFitness = individual -> {
        Integer s = Arrays.stream(individual.getChromosome()).sum();
        return ((double)s)/individual.size();
    };


    // Crossover Functions

    static BiFunction<Individual,Individual,Individual> sillyFirstParentGeneCrossover = (parent1,parent2) -> {
        if (parent1.size() != parent2.size()){
            try {
                throw new Exception();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        var offspring = new Individual(parent1.size());
        // set first gene to parent 1
        offspring.setGene(0,parent1.getGene(0));
        // rest to parent 2
        IntStream.range(1,parent1.size()).forEach(gene -> offspring.setGene(gene,parent2.getGene(gene)));
        return offspring;
    };

}
