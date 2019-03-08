import java.util.Arrays;
import java.util.stream.IntStream;

class Population {

    private Individual[] individuals;

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



}
