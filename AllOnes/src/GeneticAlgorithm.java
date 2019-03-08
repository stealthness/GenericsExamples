import java.util.Arrays;

/**
 * Created by Stephen West on 08/03/2019.
 */
public class GeneticAlgorithm {

    private final int popSize;
    private final double crossoverRate;
    private final double mutationRate;
    private final int elitism;
    private Population population;

    public GeneticAlgorithm(int popSize, double crossoverRate, double mutationRate, int elitism) {
        this.popSize = popSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.elitism = elitism;
    }

    public void setPopulation(Population population) {
        this.population = population;

    }

    public Population getPopulation() {
        return this.population;
    }

    public Individual getFitess(int index) {
         return Arrays.stream(population.getIndividuals()).sorted().skip(index).findFirst().orElse(null);
    }

    public void evaluateFitness() {
        this.population.evaluateFitness();
    }
}
