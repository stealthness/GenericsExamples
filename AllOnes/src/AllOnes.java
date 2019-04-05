import java.util.Arrays;

public class AllOnes {

    private static final int POP_SIZE = 100;
    private static final int CHROMO_SIZE = 50;
    private static final double CROSSOVER_RATE = 0.95;
    private static final double MUTATION_RATE = 0.001;
    private static final int ELITISM = 1;
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
        population.evaluateFitness();
        printFirstPopulationUpTo(population,4);
        printLastPopulationUpTo(population,4);

        // loop

        int generation = 0;
        while (!ga.solutionFound(population) && ++generation < MAX_GENERATION){

            // apply crossovers
            newPopulation = ga.crossoverPopulation(population);

            // apply mutation
            newPopulation = ga.mutatePopulation(newPopulation);
            // update

            population = newPopulation;

            population.evaluateFitness();
//            System.out.println("generation:"+generation);
//            printFirstPopulationUpTo(population,4);
//            printLastPopulationUpTo(population,4);
//            System.out.println("---------------");
        }
        printFirstPopulationUpTo(population,4);
        printLastPopulationUpTo(population,4);
        System.out.println("end");

    }

    private void printFirstPopulationUpTo(Population population,int n){
        StringBuilder sb = new StringBuilder();
        Arrays.stream(population.getIndividuals()).limit(n).forEach(individual -> sb.append(individual.toString()+System.lineSeparator()));
        System.out.println("start population:");
        System.out.println(sb.toString());
        System.out.println(".....");
    }

    private void printLastPopulationUpTo(Population population,int n){
        StringBuilder sb = new StringBuilder();
        Arrays.stream(population.getIndividuals()).skip(population.size()-n).forEach(individual -> sb.append(individual.toString()+System.lineSeparator()));
        System.out.println("end population:");
        System.out.println(".....");
        System.out.println(sb.toString());
    }
}
