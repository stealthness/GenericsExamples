import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Created by Stephen West on 02/04/2019.
 */
public class GAUtils {

    /**
     * Returns a value between 0.0 (all zeros) and 1.0 (all ones) that is the sum of ones divide by chromosome size
     */
    static Function<Individual,Double> getAllOnesFitness = (individual ->{

        // calculate the fitness
        final var fitness = ((double)individual.getChromosome().stream()
                .filter(gene -> gene == 1)
                .count())/individual.size();

        // Store fitness
        individual.setFitness(fitness);

        return fitness;

    } );

    /**
     * Selection is based on relative fitness value, and a random individual from the population. fitter individuals
     * are more likely to be selected
     */
    static Function<Population,Individual> selectWeightedParent = population -> {
        // Get individuals
        final var individuals = population.getIndividuals();

        // Spin roulette wheel
        final var populationFitness = population.getPopulationFitness();
        final var rouletteWheelPosition = Math.random() * populationFitness;

        // Find parent
        var spinWheel = 0.0;
        for (Individual individual : individuals) {
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
        
        final var offspring = new Individual(parent1.size());
        // Loop over genome
        IntStream.range(0,parent1.size()).forEach(geneIndex ->
                offspring.setGene(geneIndex, (0.5 > Math.random())?parent1.getGene(geneIndex): parent2.getGene(geneIndex)));
        return offspring;
    };

    static BiFunction<Individual,Double, Individual> mutatePopulation = (individual, mutationRate) -> {

        //final var newIndividual = new Individual(individual.getChromosome());
			    for (int geneIndex = 0; geneIndex < individual.size(); geneIndex++) {

					// Does this gene need mutation?
					if (Math.random() < mutationRate) {
						// Get new gene
						int newGene = 1;
						if (individual.getGene(geneIndex) == 1) {
							newGene = 0;
						}
						// Mutate gene
						individual.setGene(geneIndex, newGene);
					}
				}
//        Arrays.stream(individual.getChromosome()).forEach(geneIndex ->{
//            if ( Math.random() < mutationRate ) {
//                individual.setGene(geneIndex,(individual.getGene(geneIndex) == 1)?0:1);
//            }
//        });

        return individual;
    };

    /**
     * returns true if any individual in population has a fitness value of 1 (all genes are ones and is completed solution)
     */
    static Function<Population,Boolean> terminationConditionSolutionFound = population ->
            Arrays.stream(population.getIndividuals()).anyMatch(individual -> individual.getFitness()==1);

}
