package ga;

/**
 * This is our main class used to run the genetic algorithm.
 * 
 * This case is one of the simplest problems we can solve: the objective is to
 * end up with an individual whose chromosome is all ones.
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
public class AllOnesGA {

	public static void main(String[] args) {
		// Create GA object
		GeneticAlgorithm ga = new GeneticAlgorithm.GeneticAlgorithmBuilder()
                .selectionFunction(GAUtils.selectWeightedParent)
                .crossoverFunction(GAUtils.crossoverFunction)
                .chromosomeSize(50)
                .populationSize(100)
                .mutationRate(0.001)
                .crossoverRate(0.95)
                .elitismCount(2)
                .build();

		// Initialize individuals
		Population population = ga.initPopulation();

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
		while (ga.isTerminationConditionMet(population) == false) {
			// Print fittest individual from individuals
			System.out.println("Best solution: " + population.getFittest(0).toString());

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
}
