import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Builder
/**
 * Populations contains a list of individuals, how the initial population is constructed
 * The rates at which population is mutated, breed and crossed with. Also contains the
 * evaluation, selection and fitness function
 */
public class Population {

    /**
     * Contains a List of the individuals. Each individual contains a Node, fitness
     */
    private List<Individual> individuals;
    /**
     * if true prints out logging information
     */
    @Builder.Default
    private boolean logging = true;


    List<Individual> generate(String generationMethod, int size){
        var newIndividuals = new ArrayList<Individual>();
        while (size-- > 0){
            newIndividuals.add(Individual.builder()
                    .root(new TerminalNode(0.0))
                    .build());
        }
        return newIndividuals;
    }

    public void intialise(){
        this.setIndividuals(generate("full",4));
    }


    // Methods

    public List<Individual> getReproductionSelection() {
        List<Individual> selected = new ArrayList<>();

        if (this.logging){
            System.out.println(String.format("Selected.size() = %d", selected.size()));
        }
        return selected;
    }

    public List<Individual> getCrossoverSelection() {
        List<Individual> selected = new ArrayList<>();

        if (this.logging){
            System.out.println(String.format("Crossover.size() = %d", selected.size()));
        }
        return selected;
    }

    public List<Individual> mutate() {
        List<Individual> selected = new ArrayList<>();

        if (this.logging){
            System.out.println(String.format("mutate.size() = %d", selected.size()));
        }
        return selected;
    }

    public List<Individual> edit() {
        List<Individual> selected = new ArrayList<>();

        if (this.logging){
            System.out.println(String.format("edit.size() = %d", selected.size()));
        }
        return selected;
    }

    /**
     * If the terminal condition has been met (ie a program that finds an acceptable solution) then returns true, false otherwise.
     * @return
     */
    public boolean isTerminalConditionMet(){
        return false;
    }

    public Optional<Individual> getFittest(int index) {
        if (individuals == null ||individuals.size() == 0){
            return Optional.empty();
        }else{
            return Optional.of(individuals.get(0));
        }
    }

    public int size(){
        return individuals.size();
    }


    public Integer getMaxSize() {
        System.out.println(individuals.stream().mapToInt(Individual::size).max());
        return individuals.stream().mapToInt(Individual::size).max().getAsInt();
    }

    public Integer getMaxDepth() {
        return individuals.stream().mapToInt(Individual::maxDepth).max().getAsInt();
    }
}
