import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GP {

    private static final int MAX_RUN = 1000;
    Population population;

    public static void main(String[] args) {

        GP gp = new GPBuilder().build();
        gp.run();
    }


    void run(){
        population = Population.builder()
                .generationMethod("grow")
                .initialMaxDepth(2)
                .maxSize(100)
                .elitismLevel(25)
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

            double mutationRate = 0.40;
            population.setIndividuals(population.doMutations(mutationRate));
            population.evaluate();
            System.out.println("size:"+population.size());

            System.out.println("\nPART 2 - Crossing");
            double crossingRate = 0.5;
            population.setIndividuals(population.doCrossing(crossingRate));
            System.out.println("size:"+population.size());

            System.out.println("PART 3 - Check termination : generation : "+count);

            population.evaluate();
            terminationCondition = population.isTerminationConditionMet() || ++count > MAX_RUN;
            System.out.println("continue ...");
            System.out.println(population.printPopulation());
            System.out.println("\n\n");
        }

        // print result

        System.out.println("\n\nFINAL Result : " + population.getFittest(0).print());

    }
}
