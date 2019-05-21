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

            System.out.println("Evaluated and sorted");

            population.getIndividuals().forEach(individual -> {
                System.out.println("Expression : "+individual.print());
                //System.out.println("Fitness is : "+ individual.getFitness());
            });

            double mutationRate = 0.10;
            population.setIndividuals(population.doMutations(mutationRate));
            population.evaluate();

            System.out.println("Mutations completed");

            double crossingRate = 0.5;
            population.setIndividuals(population.doCrossing(crossingRate));

            System.out.println("Crossing completed");

            population.getIndividuals().forEach(individual -> {
                System.out.println("Expression : "+individual.print());
                //System.out.println("Fitness is : "+ individual.getFitness());
            });

            System.out.println("Check termination");
            terminationCondition = population.isTerminationConditionMet() || ++count > 2;

            System.out.println("\n\n");
            System.out.println("generation : " + count);
        }

        // print result

        System.out.println("Result " + population.getFittest(0));

    }
}
