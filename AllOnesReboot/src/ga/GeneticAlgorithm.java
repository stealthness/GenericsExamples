package ga;

import lombok.Builder;

import java.util.Arrays;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 *  Genetic Algorithm
 */
@Builder
public class GeneticAlgorithm {


    /**
     * Size of the Chromosome
     */
    private int chromosomeSize;

    /**
     * Size of the Population
     */
    private int populationSize;

	/**
	 * Mutation rate is the fractional probability than an individual gene will mutate randomly in a given generation.
     * The range is 0.0-1.0, but is generally small (on the order of 0.1 or less).
	 */
	private double mutationRate;

	/**
	 * Crossover rate is the fractional probability that two individuals will "mate" with each other, sharing genetic
     * information, and creating offspring with traits of each of the parents. The range is 0.0-1.0 and is generally
     * around 0.9 value.
	 */
	private double crossoverRate;

	/**
	 * Elitism is the concept that the strongest members of the population should be preserved from generation to
     * generation. If an individual is one of the elite, it will not be mutated or crossover.
	 */
	private int elitismCount;

    /**
     * Selection function is function that will select an individual from a give population
     */
	private Function<Population, Individual> selectionFunction;

    /**
     * crossoverFunction applies crossover of two parent individuals and returns a offspring individual
     */
	private BiFunction<Individual,Individual,Individual> crossoverFunction;

    /**
     * Applies Mutation on Population
     */
	private Function<Population, Individual> mutationFunction;


	/**
	 * Initialize population with random chromosome
	 * @return population The initial population generated
	 */
	public Population initPopulation() {
		// Initialize population
		return new Population(populationSize, chromosomeSize);
	}


	/**
	 * Calculates fitness using
	 * @param individual
	 *            the individual to evaluate
	 * @return double The fitness value for individual
	 */
	public double calcFitness(Individual individual) {
        return calcFitness(individual,GAUtils.getAllOnesFitness);
	}

	public double calcFitness(Individual individual, Function<Individual,Double> calculateFitness){
	    return calculateFitness.apply(individual);
    }

	/**
	 * Evaluate the whole population
	 * 
	 * Essentially, loop over the individuals in the population, calculate the
	 * fitness for each, and then calculate the entire population's fitness. The
	 * population's fitness may or may not be important, but what is important
	 * here is making sure that each individual gets evaluated.
	 * 
	 * @param population
	 *            the population to evaluate
	 */
	public void evalPopulation(Population population) {
		double populationFitness = 0;

		// Loop over population evaluating individuals and suming population
		// fitness
		for (Individual individual : population.getPopulation()) {
			populationFitness += calcFitness(individual);
		}

		population.setPopulationFitness(populationFitness);
	}

	/**
	 * Check if population has met termination condition
	 * 
	 * For this simple problem, we know what a perfect solution looks like, so
	 * we can simply stop evolving once we've reached a fitness of one.
	 * 
	 * @param population
	 * @return boolean True if termination condition met, otherwise, false
	 */
	public boolean isTerminationConditionMet(Population population) {
        return Arrays.stream(population.getPopulation()).anyMatch(individual -> individual.getFitness()==1);

	}


	/**
	 * Apply crossover to population
	 * 
	 * Crossover, more colloquially considered "mating", takes the population
	 * and blends individuals to create new offspring. It is hoped that when two
	 * individuals crossover that their offspring will have the strongest
	 * qualities of each of the parents. Of course, it's possible that an
	 * offspring will end up with the weakest qualities of each parent.
	 * 
	 * This method considers both the ga.GeneticAlgorithm instance's crossoverRate
	 * and the elitismCount.
	 * 
	 * The type of crossover we perform depends on the problem domain. We don't
	 * want to create invalid solutions with crossover, so this method will need
	 * to be changed for different types of problems.
	 * 
	 * This particular crossover method selects random genes from each parent.
	 * 
	 * @param population
	 *            The population to apply crossover to
	 * @return The new population
	 */
	public Population crossoverPopulation(Population population) {

        // Create new population
        Population newPopulation = new Population(population.size());

        // Loop over current population by fitness
        IntStream.range(0,populationSize).sorted().forEach(index ->{

            //Individual parent1 = population.getFittest(index);
            Individual parent1 = population.getIndividual(index);

            // Apply crossover to this individual?
            if (index >= this.elitismCount && this.crossoverRate > Math.random()) {
                newPopulation.setIndividual(index, this.crossoverFunction.apply(parent1,selectionFunction.apply(population)));
            } else {
                // Add individual to new population without applying crossover
                newPopulation.setIndividual(index, parent1);
            }
        });
		return newPopulation;
	}


	/**
	 * Apply mutation to population
	 * 
	 * Mutation affects individuals rather than the population. We look at each
	 * individual in the population, and if they're lucky enough (or unlucky, as
	 * it were), apply some randomness to their chromosome. Like crossover, the
	 * type of mutation applied depends on the specific problem we're solving.
	 * In this case, we simply randomly flip 0s to 1s and vice versa.
	 * 
	 * This method will consider the ga.GeneticAlgorithm instance's mutationRate
	 * and elitismCount
	 * 
	 * @param population
	 *            The population to apply mutation to
	 * @return The mutated population
	 */
	public Population mutatePopulation(Population population) {
		// Initialize new population
		Population newPopulation = new Population(this.populationSize);

		// Loop over current population by fitness
		for (int populationIndex = 0; populationIndex < population.size(); populationIndex++) {
			Individual individual = population.getFittest(populationIndex);

			// Loop over individual's genes
			for (int geneIndex = 0; geneIndex < individual.getChromosomeLength(); geneIndex++) {
				// Skip mutation if this is an elite individual
				if (populationIndex > this.elitismCount) {
					// Does this gene need mutation?
					if (this.mutationRate > Math.random()) {
						// Get new gene
						int newGene = 1;
						if (individual.getGene(geneIndex) == 1) {
							newGene = 0;
						}
						// Mutate gene
						individual.setGene(geneIndex, newGene);
					}
				}
			}

			// Add individual to population
			newPopulation.setIndividual(populationIndex, individual);
		}

		// Return mutated population
		return newPopulation;
	}

}