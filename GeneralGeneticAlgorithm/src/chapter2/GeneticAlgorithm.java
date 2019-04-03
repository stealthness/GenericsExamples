package chapter2;

import lombok.Data;

import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by Stephen West on 27/03/2019.
 */
@Data
public class GeneticAlgorithm {


    private int populationSize;
    private double mutationRate;
    private double crossoverRate;
    private int elitismCount;

    public GeneticAlgorithm(int populationSize, double mutationRate, double crossoverRate, int elitismCount) {
        this.populationSize = populationSize;
        this.mutationRate = mutationRate;
        this.crossoverRate = crossoverRate;
        this.elitismCount = elitismCount;
    }

    public Population initialisePopulation(int chromosomeSize) {
        final var  population = new Population();
        return population;
    }

    double evaulateFitness(Function<Individual,Double> fitnessFunction, Population population) {
        return -1;
    }

    public boolean solutionFound(Population population) {
        return false;
    }

    public Population crossoverPopulation(BiFunction<Individual, Double, Individual> crossoverFunction, Population population) {
        Population newPopulation = new Population();
        return newPopulation;
    }

    public Population mutatePopulation(BiFunction<Individual,Double,Individual> mutateFunction, Population population) {
        Population newPopulation = new Population();
        return newPopulation;
    }
}
