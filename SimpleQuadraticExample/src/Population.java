import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Data
@Builder
/**
 * Populations contains a list of individuals, how the initial population is constructed
 * The rates at which population is mutated, breed and crossed with. Also contains the
 * evaluation, selection and fitness function
 */
public class Population {

    /**
     * ERROR_TOLERANCE is the value of the fitness value that has reach an acceptable solution
     */
    private static final Double ERROR_TOLERANCE = 0.01;

    /**
     * Contains a List of the individuals. Each individual contains a Node, fitness
     */
    private List<Individual> individuals;

    /**
     * Maximum size of the population
     */
    private int maxSize;

    /**
     * Method used to generate the initial population
     */
    private String generationMethod = "grow";

    /**
     * The initial depth of the generated individuals in the population
     */
    private int initialMaxDepth = 2;

    /**
     * Returns the size of the population, which is the number of individuals in the population
     * @return
     */
    int size() {
        return (individuals == null)?0:individuals.size();
    }

    /**
     * Sets the elitism level, that is the amount of the top individuals that remain unaltered
     */
    int elitismLevel;



    // generate population

    /**
     * Generate the initial population according method selected
     */
    void generate(String type) {
        individuals = new ArrayList<>();
        Individual.setOfTerminals = GPUtils.getTerminalsList("basic");
        Individual.setOfFunctions = GPUtils.getFunctionList("basic");
        if (type.equals("full" )){
            IntStream.range(0,maxSize).forEach(i -> {
                Individual individual = Individual.generate(type,initialMaxDepth,1);
                individuals.add(individual);
            });
        } else if (type.equals("grow")){
            Individual individual = Individual.generate(type,initialMaxDepth,1);
            individuals.add(individual);
        } else {
        }
    }

    void generate(){
        generate(generationMethod);
    }

    // crossover function

    List<Individual>  doCrossing(double crossingRate) {
        List<Individual> newIndividuals = new ArrayList<>();
        individuals.stream().limit(elitismLevel).forEach(individual -> {
            newIndividuals.add(individual);
        });
        individuals.stream().skip(elitismLevel).forEach(individual -> {
            if (Math.random() < crossingRate){
                Individual randomIndividual = GPUtils.selectWeightedParent.apply(this);
                Individual newIndividual = Individual.generate(individual.getRoot());
                int selectedNode1 = new Random().nextInt(individual.size());
                int selectedNode2 = new Random().nextInt(randomIndividual.size());
                Node node = individual.selectSubtree(selectedNode1).clone();
                newIndividual.changeSubtreeAt(selectedNode1,randomIndividual.selectSubtree(selectedNode2));
                randomIndividual.changeSubtreeAt(selectedNode2,node);
                randomIndividual.evaluate();
                newIndividual.evaluate();
                newIndividuals.add(randomIndividual);
                newIndividuals.add(newIndividual);
            }else{
                newIndividuals.add(individual);
            }
        });
        sort();
        return newIndividuals.stream().limit(this.maxSize).collect(Collectors.toList());
    }

    // mutate function

    /**
     * Take a list of individuals and returns a list where some of the individuals have been mutated
     * @param mutationRate
     * @return
     */
    List<Individual> doMutations(double mutationRate) {
        List<Individual> newIndividuals = new ArrayList<>();
        individuals.stream().limit(elitismLevel).forEach(individual -> {
            newIndividuals.add(individual);
        });
        individuals.stream().skip(elitismLevel).forEach(individual -> {
            if (Math.random() < mutationRate){
                int selectedNode = new Random().nextInt(individual.size());
                individual.changeSubtreeAt(selectedNode,Individual.generate("grow",1,1).getRoot());
                individual.evaluate();
                newIndividuals.add(individual);
            }else{
                newIndividuals.add(individual);
            }
        });
        return newIndividuals;
    }

    // breed function





    // evaluate function



    public void evaluate() {
        individuals.stream().forEach(Individual::evaluate);
    }

    // sort function

    void sort(){
        individuals = individuals.stream().sorted().collect(Collectors.toList());
    }

    public boolean isTerminationConditionMet() {

        return individuals.stream().anyMatch(individual -> (individual.getFitness() < ERROR_TOLERANCE));

    }

    public Individual getFittest(int index) {
        sort();
        return individuals.get(index);
    }

    public void addIndividual(Individual individual) {
        if (individuals==null){
            individuals = new ArrayList<>();
        }
        if (individuals.size() < maxSize){
            individuals.add(individual);
        }
    }

    public void doSelection(int i) {
    }

    public String printPopulation(){
        var sb = new StringBuilder();
        sb.append("population size :"+ this.size());
        individuals.forEach(individual -> {
            sb.append(individual.print() + "   fitness : " + individual.getFitness() +"\n");
        });
        return sb.toString();
    }


    public double getSumOfFitness() {
        double sum = 0.0;
        for (Individual individual: individuals){
            sum += individual.getFitness();
        }
        return sum;
    }
}
