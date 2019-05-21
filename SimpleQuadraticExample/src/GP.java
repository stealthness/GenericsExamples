import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GP {

    Population population;

    public static void main(String[] args) {

        GP gp = new GPBuilder().build();
        gp.run();
    }


    void run(){
        population = Population.builder()
                .generationMethod("grow")
                .initialMaxDepth(1)
                .maxSize(10)
                .elitismLevel(2)
                .build();
        population.generate("full");


        int count = 0;
        boolean terminationCondition = false;

        while (!terminationCondition){

            System.out.println("start loop, generation = " + count);

            population.evaluate();
            population.sort();
            System.out.println(population.printPopulation());



            double mutationRate = 0.10;
            population.setIndividuals(population.doMutations(mutationRate));
            population.evaluate();

            System.out.println("size:"+population.size());
            System.out.println("Mutations completed");

            double crossingRate = 0.5;
            population.setIndividuals(population.doCrossing(crossingRate));

            System.out.println("size:"+population.size());
            System.out.println("Crossing completed");



            System.out.println("Check termination");

            population.evaluate();
            terminationCondition = population.isTerminationConditionMet() || ++count > 2;
            System.out.println(population.printPopulation());
            System.out.println("\n\n");
        }

        // print result

        System.out.println("\n\nFINAL Result : " + population.getFittest(0).print());

    }
}
