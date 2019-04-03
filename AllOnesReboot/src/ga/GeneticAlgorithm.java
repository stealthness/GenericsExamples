package ga;

import lombok.Builder;

import java.util.Optional;
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
	 * Elitism is the concept that the strongest members of the individuals should be preserved from generation to
     * generation. If an individual is one of the elite, it will not be mutated or crossover.
	 */
	private int elitismCount;

    /**
     * Selection function is function that will select an individual from a give individuals
     */
	private Function<Population, Individual> selectionFunction;

    /**
     * crossoverFunction applies crossover of two parent individuals and returns a offspring individual
     */
	private BiFunction<Individual,Individual,Individual> crossoverFunction;

    /**
     * Applies Mutation on Individual
     */
	private BiFunction<Individual,Double, Individual> mutationFunction;


	/**
	 * Initialize individuals with random chromosome
	 * @return individuals The initial individuals generated
	 */
	public Population initPopulation() {
		// Initialize individuals
		return new Population(populationSize, chromosomeSize);
	}


	/**
	 * Calculates fitness using
	 * @param individual
	 *            the individual to evaluate
	 * @return double The fitness value for individual
	 */
	public double calcFitness(final Individual individual) {
        return calcFitness(individual,GAUtils.getAllOnesFitness);
	}

	public double calcFitness(final Individual individual, final Function<Individual,Double> calculateFitness){
	    return calculateFitness.apply(individual);
    }

	/**
	 * Evaluate the whole individuals
	 * 
	 * Essentially, loop over the individuals in the individuals, calculate the
	 * fitness for each, and then calculate the entire individuals's fitness. The
	 * individuals's fitness may or may not be important, but what is important
	 * here is making sure that each individual gets evaluated.
	 * 
	 * @param population
	 *            the individuals to evaluate
	 */
	public void evalPopulation(final Population population) {
		var populationFitness = 0;

		// Loop over individuals evaluating individuals and suming individuals
		// fitness
		for (final Individual individual : population.getIndividuals()) {
			populationFitness += calcFitness(individual);
		}

		population.setPopulationFitness(populationFitness);
	}

	/**
	 * Check if individuals has met termination condition
	 * 
	 * For this simple problem, we know what a perfect solution looks like, so
	 * we can simply stop evolving once we've reached a fitness of one.
	 * 
	 * @param population
	 * @return boolean True if termination condition met, otherwise, false
	 */
	public boolean isTerminationConditionMet(Population population) {
        return isTerminationConditionMet(population,Optional.empty());
	}

    public boolean isTerminationConditionMet(Population population, Optional<Function<Population,Boolean>> terminationCondition) {
        if (terminationCondition.isEmpty()){
            terminationCondition =  new Optional<>(GAUtils.terminationConditionSolutionFound);
        }
        return terminationCondition.get().apply(population);
    }

	/**
	 * Apply crossover to individuals
	 * 
	 * Crossover, more colloquially considered "mating", takes the individuals
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
	 *            The individuals to apply crossover to
	 * @return The new individuals
	 */
	Population crossoverPopulation(Population population) {

        // Create new individuals
        final var newPopulation = new Population(population.size());

        // Loop over current individuals by fitness
        IntStream.range(0,populationSize).sorted().forEach(index ->{

            //Individual parent1 = individuals.getFittest(index);
            final var parent1 = population.getIndividual(index);

            // Apply crossover to this individual?
            if (index >= this.elitismCount && this.crossoverRate > Math.random()) {
                newPopulation.setIndividual(index, this.crossoverFunction.apply(parent1,selectionFunction.apply(population)));
            } else {
                // Add individual to new individuals without applying crossover
                newPopulation.setIndividual(index, parent1);
            }
        });
		return newPopulation;
	}


	/**
	 * Will mutate individuals in the population based on mutationFunction that will apply random changes based
     * according to mutationRate.
	 * @param population
	 *            The individuals to apply mutation to
	 * @return The mutated individuals
	 */
	Population mutatePopulation(Population population) {
        // Initialize new individuals
        final var newPopulation = new Population(population.size());

        // Loop over current individuals by fitness
        IntStream.range(0,populationSize).forEach(index ->{
            if (index > this.elitismCount) {
                // Add mutated individual to newPopulation
                newPopulation.setIndividual(index, mutationFunction.apply(population.getFittest(index), mutationRate));
            }
        });
        return newPopulation;
	}

}