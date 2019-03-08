import java.util.Arrays;

/**
 * Created by Stephen West on 08/03/2019.
 */
public class GeneticAlgorithm {

    private final int popSize;
    private final double crossoverRate;
    private final double mutationRate;
    private final int elitism;

    GeneticAlgorithm(int popSize, double crossoverRate, double mutationRate, int elitism) {
        this.popSize = popSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.elitism = elitism;
    }

    double getFitness(Population population){
        population.evaluateFitness();
        return population.getFitness();
    }

    Individual getFitessIndividual(int index, Population population) {
         return Arrays.stream(population.getIndividuals()).sorted().skip(index).findFirst().orElse(null);
    }

    void evaluateFitness(Population population) {
        population.evaluateFitness();
    }
}
