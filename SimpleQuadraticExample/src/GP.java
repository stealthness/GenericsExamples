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
                .maxSize(4)
                .build();
        population.generate("full");


        int count = 0;
        boolean terminationCondition = false;

        while (!terminationCondition){


            population.evaluate();
            population.getIndividuals().forEach(individual -> {
                System.out.println("Expression : "+individual.print());
                System.out.println("Fitness is : "+ individual.getFitness());
            });


            population.sort();

            population.doSelection();

            population.doMutations();

            population.doCrossing();

            terminationCondition = population.isTerminationConditionMet() || ++count > 2;
            System.out.println("generation : " + count);
        }

        // print result

        System.out.println("Result " + population.getFittest(0));

    }
}
