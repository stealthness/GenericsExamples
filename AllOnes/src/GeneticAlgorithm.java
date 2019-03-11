import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * Created by Stephen West on 08/03/2019.
 */
public class GeneticAlgorithm {

    private final int popSize;
    private final double crossoverRate;
    private final double mutationRate;
    private final int elitism;

    GeneticAlgorithm(int popSize, double crossoverRate, double mutationRate, int elitism) {
        this.popSize = popSize;
        this.crossoverRate = crossoverRate;
        this.mutationRate = mutationRate;
        this.elitism = elitism;
    }

    /**
     * get the average fitness of the population
     * @param population
     * @return
     */
    double getFitness(Population population){
        population.evaluateFitness();
        return population.getFitness();
    }

    /**
     * Returns the an individuals that is the index position of fitness amongst the population
     * @param index
     * @param population
     * @return
     */
    Individual getFitessIndividual(int index, Population population) {
         return Arrays.stream(population.getIndividuals()).sorted().skip(index).findFirst().orElse(null);
    }

    void evaluateFitness(Population population) {
        population.evaluateFitness();
    }

    /**
     * Create a new Population of initialised individuals and sets their fitness values.
     * @param chromosomeSize, sets the the size of the individuals chromosome.
     * @return population, with all individuals intialised and fitness value set.
     */
    Population initPopulation(int chromosomeSize) {
        Population population = new Population(this.popSize);
        population.initialize(chromosomeSize);
        population.evaluateFitness();
        return population;
    }

    /**
     * Selects an individual at random weighted by their fitness level.
     * @param population to slect an individual from.
     * @return selected individual
     */
    Individual selectParent(Population population) {

        // Spin roulette Wheel
        double populationFitness = population.getFitness();
        double rouletteWheelPosition = Math.random() * populationFitness;

        // Find Parent
        double spinWheel = 0.0;
        for (Individual individual : population.getIndividuals()){
            spinWheel += individual.getFitness();
            if(spinWheel >= rouletteWheelPosition){
                return  individual;
            }
        }
        return population.getIndividuals()[this.popSize - 1];
    }

    /**
     * Returns true is any individual's fitness has a value of 1, false otherwise
     * @param population
     * @return
     */
   boolean solutionFound(Population population) {
       return Arrays.stream(population.getIndividuals())
               .anyMatch(individual -> Arrays.stream(individual.getChromosome())
                       .allMatch(gene ->gene==1));
    }

    Population crossoverPopulation(Population population){
       Population newPopulation = new Population(popSize);

       // loop of population by fitness and apply crossover
        IntStream.range(0,popSize).forEach(i-> {

            // do wee apply to this individual
            if (true){

                // select parent 1
                Individual parent1 = getFitessIndividual(i,population);
                Individual parent2 = selectParent(population);

                System.out.println("p1 " +parent1);
                System.out.println("p2 " +parent2);
                System.out.println("cr " +crossover(parent1,parent2));

                newPopulation.setIndividual(i,crossover(parent1,parent2));
            }

        });

       return newPopulation;
    }

    private Individual crossover(Individual parent1, Individual parent2) {
       int chromosomeLength = parent1.size();
       Individual offspring = new Individual(chromosomeLength);
       IntStream.range(0,chromosomeLength).forEach(i -> {
           int newGene = (Math.random() > 0.5) ? parent1.getGene(i) : parent2.getGene(i);
           offspring.setGene(i, newGene);
       });
       return offspring;
    }
}
