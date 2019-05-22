import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Data
@Builder
public class GP {

    private static final int MAX_RUN = 50;
    Population population;

    public static void main(String[] args) {

        GP gp = new GPBuilder().build();
        gp.run();
    }


    void run(){
        population = Population.builder()
                .fitnessFunction(GPUtils.FitnessFunctionSumOfErrors)
                .generationMethod("grow")
                .initialMaxDepth(1)
                .maxSize(50)
                .elitismLevel(5)
                .testNode(GPUtils.defaultTestNode1)
                .build();
        int count = 0;
        population.generate("full");
        System.out.println("\n\nSTART LOOP, generation = " + count);
        population.evaluate();
        population.sort();
        System.out.println(population.printPopulation());

        boolean terminationCondition = false;

        while (!terminationCondition){

            List<Individual> newIndividuals = new ArrayList<>();

            System.out.println("\n PART 1 - Reproduction ");

            int reproductionRate = 20;
            newIndividuals.addAll(population.doReproduction(reproductionRate));
            System.out.println("newIndividuals size :"+newIndividuals.size());

            System.out.println("\n PART 2 - Crossing");
            double crossingRate = 0.5;
            newIndividuals.addAll(population.doCrossing(crossingRate));
            System.out.println("newIndividuals size : "+newIndividuals.size());

            newIndividuals.stream().forEach(Individual::evaluate);
            newIndividuals.sort(Individual::compareTo);
            newIndividuals = newIndividuals.stream().limit(50).collect(Collectors.toList());
            population.setIndividuals(newIndividuals);
            System.out.println("(after limit) size : "+newIndividuals.size());


            System.out.println("\n PART 3 - Mutations ");

            double mutationRate = 0.10;
            population.setIndividuals(population.doMutations(mutationRate));
            System.out.println("(after mutation) size : "+population.size());


            System.out.println("\n PART 4 - Reduce");
            double reduceRate = 0.7;
            population.setIndividuals(population.doReduction(crossingRate));
            population.evaluate();
            population.sort();
            System.out.println("size:" + population.size());

            System.out.println("PART 5 - Check termination : generation : "+count);

            population.evaluate();
            terminationCondition = population.isTerminationConditionMet() || ++count > MAX_RUN;
            System.out.println("continue ...");
            System.out.println(population.printPopulation(10));
            System.out.println("\n\n");
        }

        // print result

        System.out.println("\n\nFINAL Result : " + population.getFittest(0).print());

    }
}
