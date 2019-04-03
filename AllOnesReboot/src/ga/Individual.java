package ga;

import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * An individual position in the chromosome is called a gene, and these are the
 * atomic pieces of the solution that can be manipulated or mutated. When the
 * chromosome is a string, as in this case, each character or set of characters
 * can be a gene.
 * 
 * An individual also has a "fitness" score; this is a number that represents
 * how good a solution to the problem this individual is. The meaning of the
 * fitness score will vary based on the problem at hand.
 *
 */
@Setter
@Getter
public class Individual implements Comparable{
	private int[] chromosome;
	private double fitness = -1;

	/**
	 * Initializes individual with specific chromosome
	 * 
	 * @param chromosome
	 *            The chromosome to give individual
	 */
	public Individual(int...chromosome) {
		// Create individual chromosome
		this.chromosome = chromosome.clone();
	}

	/**
	 * Initializes random individual.
	 * 
	 * This constructor assumes that the chromosome is made entirely of 0s and
	 * 1s, which may not always be the case, so make sure to modify as
	 * necessary. This constructor also assumes that a "random" chromosome means
	 * simply picking random zeroes and ones, which also may not be the case
	 * (for instance, in a traveling salesman problem, this would be an invalid
	 * solution).
	 * 
	 * @param chromosomeLength
	 *            The length of the individuals chromosome
	 */
	public Individual(int chromosomeLength) {

		this.chromosome = new int[chromosomeLength];
        IntStream.range(0,chromosomeLength).forEach(gene -> this.setGene(gene, (Math.random()<0.5)?0:1));
	}


	/**
	 * Gets individual's chromosome length
	 * 
	 * @return The individual's chromosome length
	 */
	public int size() {
		return this.chromosome.length;
	}

	/**
	 * Set gene at index
	 * 
	 * @param gene
	 * @param index
	 * @return gene
	 */
	public void setGene(int index, int gene) {
		this.chromosome[index] = gene;
	}

	/**
	 * Get gene at index
	 * 
	 * @param index
	 * @return gene
	 */
	public int getGene(int index) {
		return this.chromosome[index];
	}


	
	/**
	 * Display the chromosome as a string.
	 * 
	 * @return string representation of the chromosome
	 */
	public String toString() {
	    StringBuilder sb = new StringBuilder();
        Arrays.stream(chromosome).forEach(sb::append);
        return sb.toString();

	}

    @Override
    public int compareTo(Object o) {
	    if (this.getFitness() > ((Individual)o).getFitness()){
	        return -1;
        }else if (this.getFitness() < ((Individual)o).getFitness()){
	        return 1;
        }
	    // equal
        return 0;
    }
}
