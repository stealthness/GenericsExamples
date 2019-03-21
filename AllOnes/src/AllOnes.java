public class AllOnes {

    private static final int POP_SIZE = 50;
    private static final int CHROMO_SIZE = 10;
    private static final double CROSSOVER_RATE = 0.95;
    private static final double MUTATION_RATE = 0.2;
    private static final int ELITISM = 0;
    private static final int MAX_GENERATION = 1000;

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

        int generation = 0;
        while (!ga.solutionFound(population) && ++generation < MAX_GENERATION){

            // apply crossovers
            newPopulation = ga.crossoverPopulation(population);

            // apply mutation
            newPopulation = ga.mutatePopulation(newPopulation);

            // update

            population = newPopulation;
            System.out.println(ga.getFitness(population));
            System.out.println(ga.getFitessIndividual(0,population));
        }
        System.out.println(population);
        System.out.println("generation:"+generation);
        System.out.println("end");
        

    }
}
