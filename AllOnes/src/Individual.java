/**
 * Created by Stephen West on 07/03/2019.
 */
class Individual {
    private int[] chromosome;

    Individual(int[] chromosome) {
        this.chromosome = chromosome;
    }

    int[] getChromosome() {
        return this.chromosome;
    }

    public int getGene(int gene) {
        return chromosome[gene];
    }
}
