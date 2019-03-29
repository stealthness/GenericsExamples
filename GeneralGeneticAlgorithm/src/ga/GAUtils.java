package ga;

import java.util.ArrayList;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.IntStream;

/**
 * Created by Stephen West on 20/03/2019.
 */
class GAUtils {

    // Fitness Functions
//
//    static Function<Individual, Double> sillyFirstGeneFitness = individual -> (double)individual.getGene(0);
//
//    static Function<Individual, Double> sillyLastGeneFitness = individual -> (double)individual.getGene(individual.size()-1);

    static Function<Individual, Double> getMeanGeneFitness = individual -> {
        final Integer s = individual.getChromosome().stream().reduce(0,(x,y)->x+y);
        return ((double)s)/individual.size();
    };

    static BiFunction<Individual,Double, ArrayList<Integer>> sillyMutateFirst = (individual, mutationRate) -> {
        if (Math.random() < mutationRate){
            individual.flip(0);
        }
        return individual.getChromosome();
    };


    static BiFunction<Individual,Double, ArrayList<Integer>> mutate = (individual, mutationRate) -> {
        var newIndividual = individual.clone();
        IntStream.range(0,individual.size()).forEach(i ->{
            if (Math.random() < mutationRate){
                newIndividual.flip(i);
            }
        });

        return newIndividual.getChromosome();
    };

    // Crossover Functions

    static BiFunction<Individual,Individual,Individual> sillyFirstParentGeneCrossover = (parent1,parent2) -> {
        var offspring = new Individual(parent1.size());
        // set first gene to parent 1
        offspring.getChromosome().set(0,parent1.getGene(0));
        // rest to parent 2
        IntStream.range(1,parent1.size()).forEach(gene -> offspring.getChromosome().set(gene,parent2.getGene(gene)));
        return offspring;
    };

    // Select Parent

    static Function<Population,Individual> sillySelectFirstIndividual = (population -> population.getIndividual(0));

//    static Function<Population,Individual> sillySelectLastIndividual = (population -> population.getIndividuals()[population.size()-1]);
//
//    static Function<Population,Individual> selectWeightedWheelParent = population -> {
//
//        // Spin roulette Wheel
//        double populationFitness = population.getFitness();
//        double rouletteWheelPosition = Math.random() * populationFitness;
//        // Find Parent
//        double spinWheel = 0.0;
//        for (Individual individual : population.getIndividuals()) {
//            spinWheel += individual.getFitness();
//            if (spinWheel >= rouletteWheelPosition) {
//                return individual;
//            }
//        }
//        return population.getIndividuals()[population.size() - 1];
//    };
//
//
//    static BiFunction<Population,Double, Population> sillyFirstGeneMutateOnly = ( population, mutationRate )-> {
//        if ( mutationRate >= 1.0) {
//            population.setGene(0,0, population.getGene(0,0)==0?1:0);
//        }else if ( mutationRate <= 0.0){
//            // do nothing
//            return population;
//        }else{
//            if (Math.random() < mutationRate){
//                population.setGene(0,0, population.getGene(0,0)==0?1:0);
//            }
//        }
//        return population;
//    };
}
