package ga;

import lombok.Data;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Created by Stephen West on 22/03/2019.
 */
@Data
public class Individual implements Comparable, Cloneable {


    /**
     * chromosome contains the genes of an individual which make up a possible solution to a GA.
     */
    private ArrayList<Integer> chromosome; //NOPMD
    /**
     * Is the evalution of the Individual possible solution raging from 0 (no match) to 1 (perfect match)
     */
    private double fitness =-1.0;

    // Constructor

    Individual(ArrayList<Integer> chromosome) {
        this.chromosome = (ArrayList<Integer>) chromosome.clone();
    }

    Individual(int chromosomeSize) {
        this.chromosome = new ArrayList<>();
        IntStream.range(0,chromosomeSize).forEach(i ->chromosome.add((Math.random()<0.5)?1:0));
    }

    // Methods

    /**
     * Evaluate the fitness of the chromosome using the fitnessFunction. The value of Fitness range from zero (no match)
     * to 1 (perfect fit)
     * @param fitnessFunction use to evaluate the fitness of the chromosome
     */
    void evaluateFitness(final Function<Individual,Double> fitnessFunction) {
        setFitness(fitnessFunction.apply(this));
    }

    /**
     * Apply a mutates on the individuals chromosome based on mutationRate and using mutation function
     * @param function applied to individuals chromosome
     * @param mutationRate rate of random mutation applied
     */
    void mutate(final BiFunction<Individual,Double,ArrayList<Integer>> function, Double mutationRate){
        this.chromosome = function.apply(this,mutationRate);
    }

    /**
     * Flips the value of a gene from 1 to 0 or vice versa
     * @param gene index of the individuals chromosome
     */
    void flip(int gene){
        chromosome.set(gene, (chromosome.get(gene)==0)?1:0);
    }

    /**
     * Returns the size of the chromosome
     * @return the size of chromosome
     */
     int size() {
        return this.chromosome.size();
    }

    /**
     * Returns the value of the gene in the chromosome
     * @param gene value of the chromosome at gen position
     * @return value of the gene
     */
    int getGene(final int gene) {
        return chromosome.get(gene);

    }
    // Override methods

    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder(); //NOPMD
        chromosome.forEach(sb::append);
        return sb.toString();
    }

    @Override
    public boolean equals(Object o){
        return o.getClass()==Individual.class && ((Individual) o).getChromosome().equals(chromosome);
    }

    @Override
    public int compareTo(Object o) {
        return Double.compare(this.fitness, ((Individual) o).getFitness());
    }

    @Override
    public Individual clone(){
        var genes = new ArrayList<Integer>();
        this.chromosome.stream().forEach(gene -> genes.add(gene));
        var newIndividual = new Individual(genes);
        return newIndividual;
    }
}
