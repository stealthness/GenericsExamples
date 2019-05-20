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
                .maxSize(4)
                .build();
        population.generate("full");


        int count = 0;
        boolean terminationCondition = false;

        while (!terminationCondition){



            population.getIndividuals().forEach(individual -> System.out.println(individual.print()));
            population.evaluate();
            population.getIndividuals().forEach(individual -> System.out.println(individual.getFitness()));


            population.sort();

            population.doSelection();

            population.doMutations();

            population.doCrossing();

            terminationCondition = population.isTerminationConditionMet() || count++ > 2;

        }

        // print result

        System.out.println("Result " + population.getFittest(0));

    }
}
