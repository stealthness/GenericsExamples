import java.util.Arrays;
import java.util.stream.IntStream;

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

    Population initPopulation(int chromosomeSize) {
        Population population = new Population(this.popSize);
        population.initialize(chromosomeSize);
        population.evaluateFitness();
        return population;
    }

    Individual selectParent(Population population) {

        // Spin roulette Wheel
        double populationFitness = population.getFitness();
        double rouletteWheelPosition = Math.random() * populationFitness;

        // Find Parent
        double spinWheel = 0.0;
        for (Individual individual : population.getIndividuals()){
            spinWheel += individual.getFitness();
            if(spinWheel >= rouletteWheelPosition){
                return  individual;
            }
        }
        return population.getIndividuals()[this.popSize - 1];
    }

   boolean solutionFound(Population population) {
       return Arrays.stream(population.getIndividuals())
               .anyMatch(individual -> Arrays.stream(individual.getChromosome())
                       .allMatch(gene ->gene==1));
    }
}
