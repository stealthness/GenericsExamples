package ga;


import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by Stephen West on 02/04/2019.
 */
public class GAUtils {


    static Function<Individual,Double> getAllOnesFitness = (individual ->{

        // calculate the fitness
        double fitness = ((double)Arrays.stream(individual.getChromosome())
                .filter(gene -> gene == 1)
                .count())/individual.getChromosomeLength();

        // Store fitness
        individual.setFitness(fitness);

        return fitness;

    } );


    static Function<Population,Individual> selectWeightedParent = population -> {
        // Get individuals
        Individual individuals[] = population.getIndividuals();

        // Spin roulette wheel
        double populationFitness = population.getPopulationFitness();
        double rouletteWheelPosition = Math.random() * populationFitness;

        // Find parent
        double spinWheel = 0;
        for (Individual individual : individuals) {
            spinWheel += individual.getFitness();
            if (spinWheel >= rouletteWheelPosition) {
                return individual;
            }
        }
        return individuals[population.size() - 1];
    };


    static BiFunction<Individual,Individual,Individual> crossoverFunction = (parent1, parent2) ->{

        Individual offspring = new Individual(parent1.getChromosomeLength());
        // Loop over genome
        for (int geneIndex = 0; geneIndex < parent1.getChromosomeLength(); geneIndex++) {
            // Use half of parent1's genes and half of parent2's genes
            if (0.5 > Math.random()) {
                offspring.setGene(geneIndex, parent1.getGene(geneIndex));
            } else {
                offspring.setGene(geneIndex, parent2.getGene(geneIndex));
            }
        }
        return offspring;
    };

    static Function<Population,Boolean> terminationConditionSolutionFound = population -> {
        Arrays.stream(population.getIndividuals()).anyMatch(individual -> individual.getFitness()==1);
    };
}
