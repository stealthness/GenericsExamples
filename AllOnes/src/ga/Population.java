package ga;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * A individuals is an abstraction of a collection of individuals. The individuals
 * class is generally used to perform group-level operations on its individuals,
 * such as finding the strongest individuals, collecting stats on the individuals
 * as a whole, and selecting individuals to mutate or crossover.
 */
@Getter
@Setter
public class Population {
	private Individual[] individuals;
	private double populationFitness = -1;

	/**
	 * Initializes blank individuals of individuals
	 * 
	 * @param populationSize
	 *            The number of individuals in the individuals
	 */
	public Population(int populationSize) {
		// Initial individuals
		this.individuals = new Individual[populationSize];
	}

	/**
	 * Initializes individuals of individuals
	 * 
	 * @param populationSize
	 *            The number of individuals in the individuals
	 * @param chromosomeLength
	 *            The size of each individual's chromosome
	 */
	public Population(int populationSize, int chromosomeLength) {
		// Initialize the individuals as an array of individuals
		this.individuals = new Individual[populationSize];

        IntStream.range(0,populationSize).forEach(individual -> {
            final var newIndividual = new Individual(chromosomeLength);
            this.individuals[individual] = newIndividual;
        });
	}

	/**
	 * Find an individual in the individuals by its fitness
	 * 
	 * This method lets you select an individual in order of its fitness. This
	 * can be used to find the single strongest individual (eg, if you're
	 * testing for a solution), but it can also be used to find weak individuals
	 * (if you're looking to cull the individuals) or some of the strongest
	 * individuals (if you're using "elitism").
	 * 
	 * @param index
	 *            The index of the individual you want, sorted by fitness. 0 is
	 *            the strongest, individuals.length - 1 is the weakest.
	 * @return individual ga.ga.Individual at index
	 */
	public Individual getFittest(int index) {
		// Order individuals by fitness
        Arrays.sort(this.individuals);

		return this.individuals[index];
	}

	/**
	 * Get individuals's size
	 * 
	 * @return size The individuals's size
	 */
	public int size() {
		return this.individuals.length;
	}

	/**
	 * Set individual at index
	 * 
	 * @param individual
	 * @param index
	 */
	public void setIndividual(int index, Individual individual) {
		individuals[index] = individual;
	}

	/**
	 * Get individual at index
	 * 
	 * @param index
	 * @return individual
	 */
	public Individual getIndividual(int index) {
		return individuals[index];
	}
	

}