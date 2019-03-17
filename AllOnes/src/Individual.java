import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Stephen West on 07/03/2019.
 */
class Individual implements Comparable{

    // Fields

    private int[] chromosome;
    private double fitness;

    // Constructors

    Individual(int[] chromosome) {
        this.chromosome = chromosome;
    }

    Individual(int chromosomeLength) {
        this.chromosome = new int[chromosomeLength];
        IntStream.range(0,chromosomeLength).forEach(i -> this.chromosome[i] = (Math.random() > 0.5)?1:0);
    }

    // Methods

    /**
     * Evaluates the fitness of the chromosome
     * fitness = sum of gene of value 1 divided by chromosome length
     */
    void evaluateFitness() {
        this.fitness = (double)Arrays.stream(chromosome).sum()/(double)this.size();
    }

    /**
     * @return the fitness of an individual
     */
    double getFitness() {
        return this.fitness;
    }

    // getters and setters

    int[] getChromosome() {
        return this.chromosome;
    }

    void setChromosome(int[] chromosome) {
        IntStream.range(0,chromosome.length-1).forEach(gene ->{
            this.chromosome[gene] = chromosome[gene];
        });
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

    @Override
    public int compareTo(Object o) {
        if (fitness <0){
            this.evaluateFitness();
        }
        if (fitness < ((Individual)o).fitness){
            return 1;
        } else if (fitness > ((Individual)o).fitness){
            return -1;
        }
        return 0;
    }

    @Override
    public boolean equals(Object o){
        return IntStream.range(0,this.size()).allMatch(i-> getGene(i) == ((Individual)o).getGene(i));
    }
}
