import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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

        Node testNode = GPUtils.testNode;
        System.out.println(testNode.print());

        population = Population.builder()
                .fitnessFunction(GPUtils.FitnessFunctionSumOfErrors)
                .generationMethod("grow")
                .initialMaxDepth(2)
                .testNode(testNode)
                .maxSize(50)
                .elitismLevel(5)
                .build();
        int count = 0;
        population.generate("full");
        System.out.println("\n\nSTART LOOP, generation = " + count);
        population.evaluate(population.getTestNode());
        population.sort();
        System.out.println(population.printPopulation());

        List<Double> avgSizes = new ArrayList<>();
        List<Integer> maxSizes = new ArrayList<>();
        List<Double> avgDepths = new ArrayList<>();
        List<Integer> maxDepths = new ArrayList<>();

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


            newIndividuals = newIndividuals.stream().limit(50).collect(Collectors.toList());
            population.setIndividuals(newIndividuals);
            System.out.println("(after limit) size : "+newIndividuals.size());

            newIndividuals.stream().forEach(individual -> individual.evaluate(population.getTestNode()));
            newIndividuals.sort(Individual::compareTo);

            System.out.println("\n PART 3 - Mutations ");

            double mutationRate = 0.10;
            population.setIndividuals(population.doMutations(mutationRate));
            System.out.println("(after mutation) size : "+population.size());


            System.out.println("\n PART 4 - Reduce");
            double reduceRate = 0.7;
            population.setIndividuals(population.doReduction(crossingRate));
            population.evaluate(population.getTestNode());
            population.sort();
            System.out.println("size:" + population.size());
            System.out.println("PART 5 - Check termination : generation : "+count);

            population.evaluate(population.getTestNode());
            terminationCondition = population.isTerminationConditionMet() || ++count > MAX_RUN;
            System.out.println("continue ...");
            System.out.println(population.printPopulation(5));

            // Stats
            maxDepths.add(population.getIndividuals().stream().map(individual -> individual.getDepth()).max(Integer::compareTo).get());
            avgDepths.add(population.getIndividuals().stream().mapToInt(individual -> individual.getDepth()).sum()/50.0);
            maxSizes.add(population.getIndividuals().stream().map(individual -> individual.size()).max(Integer::compareTo).get());
            avgSizes.add(population.getIndividuals().stream().mapToInt(individual -> individual.size()).sum()/50.0);

            System.out.println("\n\n");
        }

        // print result

        System.out.println("\n\nFINAL Result : " + population.getFittest(0).print());
        System.out.println(String.format("Fitness : %.2f", population.getFittest(0).getFitness()));
        IntStream.range(0,count).skip(Math.min(45,count - 5)).forEach(i ->{
            System.out.println("gen | MaxS | MaxD | AvgS  | AvgD  |");
            System.out.println(String.format(" %-2d | %-4d | %-4d | %-5.2f | %-5.2f |",
                    i,maxSizes.get(i),maxDepths.get(i),
                    avgSizes.get(i),avgDepths.get(i)));
        });
    }
}
