import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

/**
 * Populations contains a list of individuals, how the initial population is constructed
 * The rates at which population is mutated, breed and crossed with. Also contains the
 * evaluation, selection and fitness function
 */
@Data
@Builder
public class Population {

    /**
     * the maximum number of individuals that a population can contain
     */
    private final int maxPopulation;

    /**
     * maximum depth that a generated tree can be
     */
    private final int maxGenerationDepth;

    @Builder.Default
    private final String generationMethod = "full";

    /**
     * Stores a list of allowable terminal nodes to generate trees from
     */
    private List<Node> terminalNodeList;

    private List<GPFunction> functionNodeList;

    /**
     * Contains a List of the individuals. Each individual contains a Node, fitness
     */
    private List<Individual> individuals;
    /**
     * if true prints out logging information
     */
    @Builder.Default
    private boolean logging = true;


    private List<Individual> generate(String generationMethod, int size){
        var newIndividuals = new ArrayList<Individual>();
        while (size-- > 0){
            newIndividuals.add(Individual.generate(terminalNodeList,functionNodeList,generationMethod,maxGenerationDepth));
        }
        return newIndividuals;
    }

    private Node selectTerminalNode() {

        // standard select equal random bag
        int selection = new Random().nextInt(terminalNodeList.size());
        Node node = terminalNodeList.get(selection);
        return node;
    }

    List<Individual> generate( int size){
        return this.generate(generationMethod,size);
    }

    void initialise(){
        this.setIndividuals(generate(generationMethod,maxPopulation));
    }


    // Methods

    List<Individual> getReproductionSelection() {
        List<Individual> selected = new ArrayList<>();

        if (this.logging){
            System.out.println(String.format("Selected.size() = %d", selected.size()));
        }
        return selected;
    }

    List<Individual> getCrossoverSelection() {
        List<Individual> selected = new ArrayList<>();

        if (this.logging){
            System.out.println(String.format("Crossover.size() = %d", selected.size()));
        }
        return selected;
    }

    List<Individual> mutate() {
        List<Individual> selected = new ArrayList<>();

        if (this.logging){
            System.out.println(String.format("mutate.size() = %d", selected.size()));
        }
        return selected;
    }

    List<Individual> edit() {
        List<Individual> selected = new ArrayList<>();

        if (this.logging){
            System.out.println(String.format("edit.size() = %d", selected.size()));
        }
        return selected;
    }

    /**
     * If the terminal condition has been met (ie a program that finds an acceptable solution) then returns true, false otherwise.
     * @return true if terminal condition is met
     */
    boolean isTerminalConditionMet(){
        return false;
    }

    Optional<Individual> getFittest(int index) {
        if (individuals == null ||individuals.size() == 0){
            return Optional.empty();
        }else{
            return Optional.of(individuals.get(0));
        }
    }

    public int size(){
        return individuals.size();
    }


    Integer getMaxSize() {
        return individuals.stream().mapToInt(Individual::size).max().getAsInt();
    }

    Integer getMaxDepth() {
        return individuals.stream().mapToInt(Individual::maxDepth).max().getAsInt();
    }

    public String print() {
        var sb = new StringBuilder();
        if (individuals.isEmpty()){
            sb.append("Population is Empty");
        }else{
            individuals.forEach(individual -> sb.append(individual.print()).append("\n"));
        }
        return sb.toString();
    }
}
