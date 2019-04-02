import java.util.Arrays;
import java.util.function.Function;
import java.util.stream.IntStream;

class Population {

    // Fields

    private Individual[] individuals;
    private double fitness = -1.0;
    private int chromosomeSize = -1;

    // Constructors

    Population(int populationSize) {
        individuals = new Individual[populationSize];
    }

    // Methods

    /**
     * Sets all the individuals to random chromosome of size chromosomeSize
     * @param chromosomeSize
     */
    void initialize(int chromosomeSize) {
        this.chromosomeSize = chromosomeSize;
        IntStream.range(0,this.size()).forEach(i -> individuals[i] = new Individual(chromosomeSize));
    }

    /**
     * Evaluates the fitness of the Population
     * fitness = sum of fitness of individuals in individuals divided by individuals size
     */
    void evaluateFitness(){
        evaluateFitness(GAUtils.getMeanGeneFitness);
    }

    void evaluateFitness(Function<Individual,Double> fitnessFunction){
        Arrays.stream(individuals).forEach(individual -> individual.evaluateFitness(fitnessFunction));
        this.fitness = Arrays.stream(individuals).mapToDouble(Individual::getFitness).sum()/(double)individuals.length;
    }

    // Setter and Getters

    int size() {
        return  individuals.length;
    }

    /**
     * @return individuals
     */
    Individual[] getIndividuals() {
        return this.individuals;
    }

    /**
     * Sets the value of an individual to the index of individuals.
     * @param index of the Individual
     * @param individual new value
     */
    void setIndividual(int index, Individual individual) {
        IntStream.range(0,this.chromosomeSize).forEach(gene -> this.setGene(index, gene, individual.getGene(gene)));
    }

    /**
     * @return the fitness of the individuals
     */
    double getFitness() {
        return this.fitness;
    }

    // Override

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        Arrays.stream(this.getIndividuals()).forEach(individual -> sb.append(individual.toString()+System.lineSeparator()));
        return sb.toString();
    }

    void setGene(int individualIndex, int geneIndex, int newValue) {
        this.getIndividuals()[individualIndex].setGene(geneIndex,newValue);
    }

    int getGene(int individualIndex, int geneIndex) {
        return this.getIndividuals()[individualIndex].getGene(geneIndex);
    }

    int getChromosomeSize() {
        return this.chromosomeSize;
    }

    @Override
    public Population clone(){
        var pop = new Population(this.size());
        pop.initialize(this.chromosomeSize);
        IntStream.range(0,this.size()).forEach(i-> pop.setIndividual(i,this.getIndividuals()[i]));
        return pop;
    }
}
