import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GP {

    private static final int MAX_RUN = 20;
    Population population;

    public static void main(String[] args) {

        GP gp = new GPBuilder().build();
        gp.run();
    }


    void run(){
        population = Population.builder()
                .fitnessFunction(GPUtils.FitnessFunctionSumOfErrors2)
                .generationMethod("grow")
                .initialMaxDepth(2)
                .maxSize(50)
                .elitismLevel(5)
                .build();
        int count = 0;
        population.generate("full");
        System.out.println("\n\nSTART LOOP, generation = " + count);
        population.evaluate();
        population.sort();
        System.out.println(population.printPopulation());

        boolean terminationCondition = false;

        while (!terminationCondition){

            System.out.println("\n PART 1 - Mutations ");

            double mutationRate = 0.20;
            population.setIndividuals(population.doMutations(mutationRate));
            population.evaluate();
            population.sort();
            System.out.println("size:"+population.size());

            System.out.println("\nPART 2 - Crossing");
            double crossingRate = 0.3;
            population.setIndividuals(population.doCrossing(crossingRate));
            population.evaluate();
            population.sort();
            System.out.println("size:"+population.size());

            System.out.println("\nPART 3 - Reduce");
            double reduceRate = 0.5;
            population.setIndividuals(population.doReduction(crossingRate));
            population.evaluate();
            population.sort();
            System.out.println("size:"+population.size());

            System.out.println("PART 4 - Check termination : generation : "+count);

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
