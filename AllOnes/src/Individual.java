import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Stephen West on 07/03/2019.
 */
class Individual {

    // Fields

    private int[] chromosome;
    private double fitness;

    Individual(int[] chromosome) {
        this.chromosome = chromosome;
    }

    Individual(int chromosomeLength) {
        this.chromosome = new int[chromosomeLength];
        IntStream.range(0,chromosomeLength).forEach(i -> this.chromosome[i] = (Math.random() > 0.5)?1:0);
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

    // Override

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        Arrays.stream(chromosome).forEach(sb::append);
        return sb.toString();
    }

    /**
     * Evaluates the fitness of the chromosome
     * fitness = sum of gene of value 1 divided by chromosome length
     */
    void evaluateFitness() {
        this.fitness = (double)Arrays.stream(chromosome).sum()/(double)this.size();
    }

    double getFitness() {
        return this.fitness;
    }
}
