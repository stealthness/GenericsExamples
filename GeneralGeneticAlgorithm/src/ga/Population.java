package ga;

import lombok.Data;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Created by Stephen West on 27/03/2019.
 *
 * Population is collection of Individuals each of which represent a possible solution for problem that is being solved
 */
@Data
public class Population {

    ArrayList<Individual> population;

    public Population(){
        population = new ArrayList<>();
    }


    void intialise(Population population){
        IntStream.range(0,population.size()).forEach(i -> this.population.add(population.getIndividual(i).clone()));
    }

    /**
     * Sort the Population
     */
    void sort(){
        population = (ArrayList<Individual>) population.stream().sorted(Comparator.comparing(Individual::getFitness).reversed()).collect(Collectors.toList());
    }

    /**
     * Evaluate the function
     */
    void evaluate(Function<Individual,Double> fitnessFunction){
        population.stream().forEach(individual -> individual.evaluateFitness(fitnessFunction));
    }

    /**
     * Crossovers the population using the crossoverFunbtion
     * @param crossoverFunction
     * @param crossoverRate
     */
    void crossover(BiFunction<Individual,Individual,Individual> crossoverFunction,
                   Function<Population,Individual> selectionFunction,
                   Double crossoverRate){
        Population newPopulation = new Population();
        this.population.stream().forEach(individual -> {
            if (Math.random()<crossoverRate){
                Individual newIndividual = crossoverFunction.apply(individual,this.getIndividual(0));
                newPopulation.addIndividual(newIndividual);
            }else{
                newPopulation.addIndividual(individual);
            }
        });
        this.setPopulation(newPopulation.getPopulation());
    }

    /**
     * mutates the population at the mutation rate
     * @param mutateFunction
     * @param mutation
     */
    void mutate(BiFunction<Individual,Double,ArrayList<Integer>> mutateFunction,Double mutation){

        this.getPopulation().stream().forEach(individual -> {
            System.out.println("1\n" + population);
            individual.mutate(mutateFunction,mutation);
            System.out.println("2\n" + population);
        });
    }

    /**
     * Returns the fitness of the individual at index
     */
    int getFitness(int index){
        return -0;
    }

    int size(){
        return population.size();
    }

    void addIndividual(Individual individual){
        this.population.add(individual.clone());
    }

    // Getter and setters


    public void setIndividual(int index,Individual individual){
        population.set(index,individual.clone());
    }

    Individual getIndividual(int index){
        return population.get(index);
    }


    // Override methods

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        this.getPopulation().stream().forEach(individual -> sb.append(individual.toString()+System.lineSeparator()));
        return sb.toString();
    }

    @Override
    public boolean equals(Object o){
        if (o.getClass() == Population.class){
            if (((Population)o).getPopulation().size() == this.getPopulation().size()){
                return IntStream.range(0,this.getPopulation().size())
                        .allMatch(i-> this.getPopulation().get(i).equals(((Population)o).getPopulation().get(i)));
            }
        }
        //otherwise
        return false;
    }
}
