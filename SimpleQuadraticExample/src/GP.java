import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Data
@Builder
public class GP {

    private static final int MAX_RUN = 50;
    private static final int MAX_SIZE = 100;
    private static final String FULL = "full";
    Population population;

    public static void main(String[] args) {

        GP gp = new GPBuilder().build();
        gp.run();
    }


    void run(){

        Optional<Individual> solution = findSolution();
        if (solution.isEmpty()){
            System.out.println("no Solution found");
        }else{
            System.out.println(String.format("Solution found : %s",solution.toString()));
        }

    }

    private Optional<Individual> findSolution(){

        population = Population.builder()
                .maxPopulation(4)
                .maxGenerationDepth(1)
                .generationMethod(FULL)
                .build();

        population.initialise();

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

            // print result

        }
        return population.getFittest(0);
    }
}
