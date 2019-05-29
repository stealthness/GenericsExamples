import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
public class GP {

    private static final int MAX_RUN = 50;
    private static final int MAX_SIZE = 100;
    Population population;

    public static void main(String[] args) {

        GP gp = new GPBuilder().build();
        gp.run();
    }


    void run(){


        population = Population.builder()
                .build();
        int count = 0;

        boolean terminationCondition = false;

        while (!terminationCondition){

            List<Individual> newIndividuals = new ArrayList<>();

            /************** REPRODUCTION ********************/
            System.out.println("\n PART 1 - Reproduction ");
            newIndividuals.addAll(population.getReproductionSelection());


            /************** CROSSING ********************/
            System.out.println("\n PART 2 - Crossing");
            newIndividuals.addAll(population.getCrossoverSelection());

            /************** MUTATING ********************/
            System.out.println("\n PART 3 - Mutations ");
            newIndividuals.addAll(population.mutate());


            /************** EDITING ********************/
            System.out.println("\n PART 4 - edit");
            newIndividuals.addAll(population.edit());


            /************** TERMINATION ********************/
            System.out.println("\n PART 5 - Check termination : generation : " + count++);
            System.out.println("\n\n");

            terminationCondition = count > MAX_RUN || population.isTerminalConditionMet();
        }

        // print result

    }
}
