package ga;

import lombok.Data;

import java.util.ArrayList;
import java.util.function.Function;

/**
 * Created by Stephen West on 22/03/2019.
 */
@Data
public class Individual {


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
        // Override methods
    }

    @Override
    public String toString(){
        final StringBuilder sb = new StringBuilder(); //NOPMD
        chromosome.forEach(sb::append);
        return sb.toString();
    }


}
