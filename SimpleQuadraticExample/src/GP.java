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
                .maxSize(4)
                .build();
        population.generate("full");


        boolean terminationCondition = false;
        while (!terminationCondition){



            population.getIndividuals().forEach(individual -> System.out.println(individual.print()));
            population.evaluate();
            population.getIndividuals().forEach(individual -> System.out.println(individual.getFitness()));


            population.sort();

            population.doSelection();

            population.doMutations();

            population.doCrossing();

            terminationCondition = population.isTerminationConditionMet();

        }

    }
}
