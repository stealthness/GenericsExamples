package ga;


import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.IntStream;

/**
 * A population is an abstraction of a collection of individuals. The population
 * class is generally used to perform group-level operations on its individuals,
 * such as finding the strongest individuals, collecting stats on the population
 * as a whole, and selecting individuals to mutate or crossover.
 */
@Getter
@Setter
public class Population {
	private Individual population[];
	private double populationFitness = -1;

	/**
	 * Initializes blank population of individuals
	 * 
	 * @param populationSize
	 *            The number of individuals in the population
	 */
	public Population(int populationSize) {
		// Initial population
		this.population = new Individual[populationSize];
	}

	/**
	 * Initializes population of individuals
	 * 
	 * @param populationSize
	 *            The number of individuals in the population
	 * @param chromosomeLength
	 *            The size of each individual's chromosome
	 */
	public Population(int populationSize, int chromosomeLength) {
		// Initialize the population as an array of individuals
		this.population = new Individual[populationSize];

        IntStream.range(0,populationSize).forEach(individual -> {
            var newIndividual = new Individual(chromosomeLength);
            this.population[individual] = newIndividual;
        });
	}

	/**
	 * Find an individual in the population by its fitness
	 * 
	 * This method lets you select an individual in order of its fitness. This
	 * can be used to find the single strongest individual (eg, if you're
	 * testing for a solution), but it can also be used to find weak individuals
	 * (if you're looking to cull the population) or some of the strongest
	 * individuals (if you're using "elitism").
	 * 
	 * @param index
	 *            The index of the individual you want, sorted by fitness. 0 is
	 *            the strongest, population.length - 1 is the weakest.
	 * @return individual ga.Individual at index
	 */
	public Individual getFittest(int index) {
		// Order population by fitness
        Arrays.sort(this.population);

		return this.population[index];
	}

	/**
	 * Get population's size
	 * 
	 * @return size The population's size
	 */
	public int size() {
		return this.population.length;
	}

	/**
	 * Set individual at index
	 * 
	 * @param individual
	 * @param index
	 * @return individual
	 */
	public Individual setIndividual(int index, Individual individual) {
		return population[index] = individual;
	}

	/**
	 * Get individual at index
	 * 
	 * @param index
	 * @return individual
	 */
	public Individual getIndividual(int index) {
		return population[index];
	}
	
	/**
	 * Shuffles the population in-place
	 *
	 * @return void
	 */
	public void shuffle() {
		Random rnd = new Random();
		for (int i = population.length - 1; i > 0; i--) {
			int index = rnd.nextInt(i + 1);
			Individual a = population[index];
			population[index] = population[i];
			population[i] = a;
		}
	}
}