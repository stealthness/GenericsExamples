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

    int getGene(int gene) {
        return chromosome[gene];
    }

    int size() {
        return chromosome.length;
    }

    public void setGene(int gene, int value) {
    }
}
