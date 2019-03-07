/**
 * Created by Stephen West on 07/03/2019.
 */
class Individual {

    // Fields

    private int[] chromosome;

    Individual(int[] chromosome) {
        this.chromosome = chromosome;
    }

    public Individual(int chromosomeLength) {
    }

    // getters and setters

    int[] getChromosome() {
        return this.chromosome;
    }

    int getGene(int gene) {
        return chromosome[gene];
    }

    void setGene(int gene, int value) {
        chromosome[gene] = value;
    }

    int size() {
        return chromosome.length;
    }
}
