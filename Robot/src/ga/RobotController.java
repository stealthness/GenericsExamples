package ga;

/**
 * This is our main class used to run the genetic algorithm.
 * 
 *
 * 
 * The simplicity of this problem makes the ga.GeneticAlgorithm class'
 * "calcFitness" method very simple. We'll just count the number of ones in the
 * chromosome and use that as the fitness score. Similarly, the
 * "isTerminationConditionMet" method in the ga.GeneticAlgorithm class for this
 * example is very simple: if the fitness score (ie, number of ones) is the same
 * as the length of the chromosome (ie, we're all ones), we're done!
 * 
 * @author bkanber
 *
 */
public class RobotController implements Runnable{

	public static void main(String[] args) {
        Population population = new Population(2,128);
	    final var robot= new Robot(population.getIndividual(0),createTestMaze(),64);
	    robot.run();
	}


    @Override
    public void run() {
        // Create GA object
        final var ga = new GeneticAlgorithm.GeneticAlgorithmBuilder()
                .selectionFunction(GAUtils.selectWeightedParent)
                .crossoverFunction(GAUtils.crossoverFunction)
                .mutationFunction(GAUtils.mutatePopulation)
                .tournamentSize(5)
                .chromosomeSize(128)
                .populationSize(200)
                .mutationRate(0.05)
                .crossoverRate(0.9)
                .elitismCount(2)
                .build();

        // Initialize individuals
        var population = ga.initPopulation();

        // Evaluate individuals
        ga.evalPopulation(population);

        // Keep track of current generation
        int generation = 1;

        /**
         * Start the evolution loop
         *
         * Every genetic algorithm problem has different criteria for finishing.
         * In this case, we know what a perfect solution looks like (we don't
         * always!), so our isTerminationConditionMet method is very
         * straightforward: if there's a member of the individuals whose
         * chromosome is all ones, we're done!
         */
        while (!ga.isTerminationConditionMet(population)) {
            // Print fittest individual from individuals
            System.out.println("Best solution: " + population.getFittest(0).toString());

//            Future<Population> future = ga.evolove(population);
//
//            while (!future.isDone()){
//                System.out.println("Calculating...");
//                try {
//                    Thread.sleep(10);
//                } catch (InterruptedException e) {
//                    // do nothing
//                }
//            }
//
//            try {
//                population = future.get();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            } catch (ExecutionException e) {
//                e.printStackTrace();
//            }
            // Apply crossover
            population = ga.crossoverPopulation(population);

            // Apply mutation
            population = ga.mutatePopulation(population);

            // Evaluate individuals
            ga.evalPopulation(population);

            // Increment the current generation
            generation++;
        }

        /**
         * We're out of the loop now, which means we have a perfect solution on
         * our hands. Let's print it out to confirm that it is actually all
         * ones, as promised.
         */
        System.out.println("Found solution in " + generation + " generations");
        System.out.println("Best solution: " + population.getFittest(0).toString());
    }

    public static Maze createTestMaze() {
	    if (false){
            return new Maze(new int[][]{{0, 0, 0, 0, 1, 0, 1, 3, 2},
                    {1, 0, 1, 1, 1, 0, 1, 3, 1},
                    {1, 0, 0, 1, 3, 3, 3, 3, 1},
                    {3, 3, 3, 1, 3, 1, 1, 0, 1},
                    {3, 1, 3, 3, 3, 1, 1, 0, 0},
                    {3, 3, 1, 1, 1, 1, 0, 1, 1},
                    {1, 3, 0, 1, 3, 3, 3, 3, 3},
                    {0, 3, 1, 1, 3, 1, 0, 1, 3},
                    {1, 3, 3, 3, 3, 1, 1, 1, 4}});	        
        }else{
            return new Maze(new int[][]{
                    {0, 0, 0, 0, 1, 0, 1, 0, 2},
                    {0, 0, 0, 1, 1, 0, 0, 0, 0},
                    {1, 0, 0, 1, 0, 0, 0, 0, 1},
                    {0, 0, 0, 1, 0, 1, 1, 0, 0},
                    {0, 1, 0, 0, 0, 1, 1, 0, 0},
                    {0, 0, 1, 0, 1, 1, 0, 1, 1},
                    {0, 0, 0, 1, 0, 0, 0, 0, 0},
                    {0, 0, 1, 0, 0, 1, 0, 1, 0},
                    {1, 0, 0, 0, 0, 1, 1, 1, 4}});

        }

    }
    
    
}
