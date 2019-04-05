import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
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
	private ArrayList<Integer> chromosome;
	private double fitness = -1.0;

	/**
	 * Initializes individual with specific chromosome
	 * 
	 * @param chromosome
	 *            The chromosome to give individual
	 */
	public Individual(int...chromosome) {
		// Create individual chromosome
        Arrays.stream(chromosome).forEach(gene ->{
            this.chromosome.add(gene);
        });
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
	    this.chromosome = new ArrayList<>();
        IntStream.range(0,chromosomeLength).forEach(gene -> this.chromosome.add((Math.random()<0.5)?0:1));
	}


	/**
	 * Gets individual's chromosome length
	 * 
	 * @return The individual's chromosome length
	 */
	public int size() {
		return this.chromosome.size();
	}

	/**
	 * Set gene at index
	 * 
	 * @param geneIndex
	 * @param geneValue
	 * @return gene
	 */
	public void setGene(int geneIndex, int geneValue) {
		this.chromosome.set(geneIndex, geneValue);
	}

	/**
	 * Get gene at index
	 * 
	 * @param geneIndex
	 * @return gene
	 */
	public int getGene(int geneIndex) {
		return this.chromosome.get(geneIndex);
	}


	
	/**
	 * Display the chromosome as a string.
	 * 
	 * @return string representation of the chromosome
	 */
	public String toString() {
	    final StringBuilder sb = new StringBuilder();
        chromosome.forEach(sb::append);
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
