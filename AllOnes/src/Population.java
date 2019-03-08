import java.util.Arrays;
import java.util.stream.IntStream;

class Population {

    private Individual[] individuals;
    private double fitness;

    Population(int populationSize) {
        individuals = new Individual[populationSize];
    }

    int size() {
        return  individuals.length;
    }

    void initialize(int chromosomeSize) {
        IntStream.range(0,this.size()).forEach(i -> individuals[i] = new Individual(chromosomeSize));
    }

    Individual[] getIndividuals() {
        return this.individuals;
    }

    void setIndividual(int index, Individual individual) {
        this.individuals[index] = individual;
    }

    void evaluateFitness(){
        Arrays.stream(individuals).forEach(individual -> individual.evaluateFitness());
        this.fitness = Arrays.stream(individuals).mapToDouble(Individual::getFitness).sum()/(double)individuals.length;

    }

    double getFitness() {
        return this.fitness;
    }
}
