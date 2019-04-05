import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Created by Stephen West on 07/03/2019.
 */
class Individual implements Comparable{

    // Fields

    private ArrayList<Integer> chromosome;
    private double fitness;

    // Constructors

    /**
     * Constructs
     * @param chromosome
     */
    Individual(int[] chromosome) {
        this.chromosome =  new ArrayList<>();

        IntStream.range(0,chromosome.length).forEach(i -> {
            this.chromosome.add(chromosome[i]);
        });

    }

    Individual(int chromosomeLength) {
        this.chromosome = new ArrayList<>();
        IntStream.range(0,chromosomeLength).forEach(i -> this.chromosome.add(i,(Math.random() > 0.5)?1:0));
    }

    // Methods

    /**
     * Evaluates the fitness of the chromosome using default method from GAUtils.getMeanGeneFitness
     * fitness = sum of gene of value 1 divided by chromosome length
     */
    void evaluateFitness() {
        evaluateFitness(GAUtils.getMeanGeneFitness);
    }

    /**
     * Evaluates the fitness of the chromosome using @param functions
     * @param fitnessFunction a function that will evaluate the fitness of the individual
     */
    void evaluateFitness(Function<Individual,Double> fitnessFunction){
        this.fitness = fitnessFunction.apply(this);
    }

    /**
     * @return the fitness of an individual
     */
    double getFitness() {
        return this.fitness;
    }

    // getters and setters

    ArrayList<Integer> getChromosome() {
        return this.chromosome;
    }

    void setChromosome(int[] chromosome) {
        IntStream.range(0,chromosome.length).forEach(gene -> this.chromosome.set(gene, chromosome[gene]));
    }

    int getGene(int gene) {
        return chromosome.get(gene);
    }

    void setGene(int gene, int value) {
        chromosome.set(gene, value);
    }

    int size() {
        return chromosome.size();
    }

    // Override

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        chromosome.stream().forEach(sb::append);
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
