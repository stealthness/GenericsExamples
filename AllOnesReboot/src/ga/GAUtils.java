package ga;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Created by Stephen West on 02/04/2019.
 */
public class GAUtils {

    static {

    }

    /**
     * Returns a value between 0.0 (all zeros) and 1.0 (all ones) that is the sum of ones divide by chromosome size
     */
    static Function<Individual,Double> getAllOnesFitness = individual ->{

        // calculate the fitness
        final var fitness = ((double)Arrays.stream(individual.getChromosome())
                .filter(gene -> gene == 1)
                .count())/individual.size();

        // Store fitness
        individual.setFitness(fitness);

        return fitness;
    };

    /**
     * Selection is based on relative fitness value, and a random individual from the population. fitter individuals
     * are more likely to be selected
     */
    static Function<Population,Individual> selectWeightedParent = population -> {
        // Get individuals
        final var individuals = population.getIndividuals();

        // Spin roulette wheel
        final var populationFitness = population.getPopulationFitness();
        final var  rouletteWheelPosition = Math.random() * populationFitness;

        // Find parent
        var spinWheel = 0;
        for (final Individual individual : individuals) {
            spinWheel += individual.getFitness();
            if (spinWheel >= rouletteWheelPosition) {
                return individual;
            }
        }
        return individuals[population.size() - 1];
    };

    /**
     * returns a new offspring from two parent Individuals. Each gene has 50/50 chance of being taken from either parent.
     */
    static BiFunction<Individual,Individual,Individual> crossoverFunction = (parent1, parent2) ->{

        final var  offspring = new Individual(parent1.size());
        // Loop over chromosome
        IntStream.range(0,parent1.size()).forEach(geneIndex ->
                offspring.setGene(geneIndex, Math.random() < 0.5 ? parent1.getGene(geneIndex) : parent2.getGene(geneIndex)));
        return offspring;
    };

    static BiFunction<Individual,Double, Individual> mutatePopulation = (individual, mutationRate) -> {

        final var newIndividual = new Individual(individual.getChromosome());

        Arrays.stream(newIndividual.getChromosome()).forEach(geneIndex ->{
            if ( Math.random() < mutationRate ) {
                newIndividual.setGene(geneIndex,(newIndividual.getGene(geneIndex) == 1)?0:1);
            }
        });

        return newIndividual;
    };

    /**
     * returns true if any individual in population has a fitness value of 1 (all genes are ones and is completed solution)
     */
    static Function<Population,Boolean> terminationConditionSolutionFound = population ->
            Arrays.stream(population.getIndividuals()).anyMatch(individual -> individual.getFitness()==1);

}
