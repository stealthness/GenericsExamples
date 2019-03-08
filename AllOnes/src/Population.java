import java.util.Arrays;
import java.util.stream.IntStream;

class Population {

    // Fields

    private Individual[] individuals;
    private double fitness;

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
        IntStream.range(0,this.size()).forEach(i -> individuals[i] = new Individual(chromosomeSize));
    }

    /**
     * Evaluates the fitness of the Population
     * fitness = sum of fitness of individuals in population divided by population size
     */
    void evaluateFitness(){
        Arrays.stream(individuals).forEach(Individual::evaluateFitness);
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
     * @param index
     * @param individual
     */
    void setIndividual(int index, Individual individual) {
        this.individuals[index] = individual;
    }

    /**
     * @return the fitness of the population
     */
    double getFitness() {
        return this.fitness;
    }

    // Override

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        return sb.toString();
    }
}
