import lombok.Builder;
import lombok.Data;

import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.util.Arrays.asList;

/**
 * Populations contains a list of individuals, how the initial population is constructed
 * The rates at which population is mutated, breed and crossed with. Also contains the
 * evaluation, selection and fitness function
 */
@Data
@Builder
public class Population {

    private static final double TOL = 0.000001;
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
    @Builder.Default
    private List<Node> terminalNodeList = asList(new TerminalNode(0.0));

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

    @Builder.Default
    private Double mutateRate = 0.25;

    @Builder.Default
    private Double reproductionRate = 0.3;

    @Builder.Default
    private double crossoverRate = 0.5;

    @Builder.Default
    private BiFunction<List<Node>, Double, Node>  mutateFunction = GPUtils.mutateRandomIndex;
    @Builder.Default
    private Function<Population,Individual> selectionMethod = GPUtils.selectWeightedParent;


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
        return terminalNodeList.get(selection);
    }

    List<Individual> generate( int size){
        return this.generate(generationMethod,size);
    }

    void initialise(){
        this.setIndividuals(generate(generationMethod,maxPopulation));
    }


    // Methods

    /**
     * Returns a List of the fittest individual up to index of sorted list of individuals
     */
    List<Individual> getFittestList(int index){
        return individuals.stream().sorted().limit(index).collect(Collectors.toList());
    }

    List<Individual> getReproductionSelection() {
        List<Individual> selected = new ArrayList<>();

        int limit = (int)(size()*reproductionRate);

        selected.addAll(getFittestList(limit));

        if (this.logging){
            System.out.println(String.format("Selected.size() = %2d   : limit = %2d", selected.size(),limit));
        }
        return selected;
    }

    private Individual getSelection() {
        return selectionMethod.apply(this);
    }

    List<Individual> getCrossoverSelection() {
        List<Individual> children = new ArrayList<>();
        individuals.stream().sorted().skip((int)(size()*reproductionRate)).forEach(individual -> {
            List<Individual> parentList = asList(individual, selectionMethod.apply(this));
            children.addAll(Individual.crossoverIndividuals(parentList,crossoverRate));
        });


        if (this.logging){
            System.out.println(String.format("Crossover.size() = %d", children.size()));
        }
        return children;
    }

    private List<Individual> getIndividualCrossover(List<Individual> selected, int index, double rate) {
        Individual parent0 = getFittest(index).get();
        Individual parent1 = getSelection();
        return Individual.crossoverIndividuals(asList(parent0,parent1), rate);
    }

    List<Individual> mutate() {

        List<Individual> selected = new ArrayList<>();
        for (Individual individual: individuals){
            if (Math.random()<mutateRate){
                int r = new Random().nextInt(individual.size());
                if (r == 0){
                    selected.add(Individual.generate(terminalNodeList,functionNodeList,"grow",maxGenerationDepth));
                } else{
                    Node root = individual.getRoot().clone();
                    if (root.size()==0){
                        selected.add(Individual.generate(terminalNodeList,functionNodeList,"grow",maxGenerationDepth));
                    } else{
                        ((FunctionNode)root).replaceSubtreeAt(r,NodeUtils.generateNode(terminalNodeList,functionNodeList,"grow",maxGenerationDepth));
                        selected.add(Individual.builder().root(root).build());
                    }
                }
            }else{
                selected.add(individual);
            }
        }

        if (this.logging){
            System.out.println(String.format("mutate.size() = %d", selected.size()));
        }
        return selected;
    }

    /**
     * generates a root
     * @return
     */
    private Node generateRoot() {
        return NodeUtils.generateNode(terminalNodeList,functionNodeList,generationMethod,getMaxGenerationDepth());
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
        if (size() != 0){
            return false;
        }else {
            return getFittest(0).get().getFitness() < TOL;
        }
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

    String print() {
        var sb = new StringBuilder();
        if (individuals.isEmpty()){
            sb.append("Population is Empty");
        }else{
            individuals.forEach(individual -> sb.append(individual.toClojureString()).append("\n"));
        }
        return sb.toString();
    }

    String printFitness() {
        var sb = new StringBuilder();
        if (individuals.isEmpty()){
            sb.append("Population is Empty");
        }else{
            individuals.forEach(individual -> sb.append(String.format("Fitness %f   for %s \n",individual.getFitness(),individual.toClojureString())));
        }
        return sb.toString();
    }

    // evaluation

    void evaluate(Node expNode){
        individuals.stream().forEach(individual -> {
            List nodes = asList(individual.getRoot(),expNode);
            individual.setFitness(GPUtils.evaluateFitness(nodes, new double[]{-1.0,1.0,0.1}));
        });

    }

    /**
     * Removes and replaces and individual at index
     */
    void replaceIndividualAt(int index, Individual newIndividual) {
        individuals.remove(index);
        individuals.add(index,Individual.builder().root(newIndividual.getRoot()).build());
    }

    /**
     * Returns the maximum size amongst individuals
     * @return maximum individual's depth
     */
    int getIndividualsMaxDepth() {
        return individuals.stream().mapToInt(Individual::maxDepth).max().getAsInt();
    }

    /**
     * Returns the maximum size amongst individuals
     * @return maximum individual's size
     */
    int getIndividualsMaxSize() {
        return individuals.stream().mapToInt(Individual::size).max().getAsInt();
    }

    public double getSumOfFitness() {
        return individuals.stream().mapToDouble(Individual::getFitness).sum();
    }
}
