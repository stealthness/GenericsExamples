package ga;


/**
 * Created by Stephen West on 22/03/2019.
 */
public class Individual {

    /**
     * Stores the chromosome of an individual, where selection of gene represents a possible Individual
     */
    private int[] chromosome;

    public Individual(final int...chromosome) {
        this.chromosome = chromosome.clone();
    }

    /**
     * Returns the size of the chromosome
     * @return
     */
    public int size() {
        return this.chromosome.length;
    }

    public int getGene(int gene) {
        return this.chromosome[gene];
    }
}
