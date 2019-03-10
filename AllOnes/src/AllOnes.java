public class AllOnes {

    private static final int POP_SIZE = 50;
    private static final int CHROMO_SIZE = 10;
    private static final double CROSSOVER_RATE = 0.95;
    private static final double MUTATION_RATE = 0.01;
    private static final int ELITISM = 0;

    public static void main(String[] args) {
        AllOnes allOnes = new AllOnes();

        allOnes.start(POP_SIZE,CHROMO_SIZE,CROSSOVER_RATE,MUTATION_RATE,ELITISM);
    }

    private void start(int popSize, int chromoSize, double crossoverRate, double mutationRate, int elitism) {

        GeneticAlgorithm ga = new GeneticAlgorithm(popSize,crossoverRate,mutationRate,elitism);
        Population population = new Population(popSize);
        Population  newPopulation = new Population(chromoSize);
        population.initialize(chromoSize);

        // loop

        while (ga.solutionFound(population)){

            // apply crossovers

            // apply mutation

            // update

        }


    }
}
