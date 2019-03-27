package ga;

import lombok.Data;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * Created by Stephen West on 27/03/2019.
 *
 * Population is collection of Individuals each of which represent a possible solution for problem that is being solved
 */
@Data
public class Population {

    ArrayList<Individual> population;

    /**
     * Sort the Population
     */
    void sort(){

    }

    /**
     * Evaluate the function
     */
    void evaluate(Function<Individual,Double> fitnessFunction){

    }

    /**
     * mutates the population at the mutation rate
     * @param mutateFunction
     * @param mutation
     */
    void mutate(BiFunction<Individual,Double,ArrayList<Integer>> mutateFunction,Double mutation){

    }

    /**
     * Returns the fitness of the individual at index
     */
    int getFitness(int index){
        return -0;
    }
}
